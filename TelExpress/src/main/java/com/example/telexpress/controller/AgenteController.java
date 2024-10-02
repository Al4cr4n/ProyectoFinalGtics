package com.example.telexpress.controller;




import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.OrdenesRepository;
import com.example.telexpress.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agente")
public class AgenteController {

    final OrdenesRepository ordenesRepository;
    final UsuarioRepository usuarioRepository;

    @Autowired
    public AgenteController(OrdenesRepository ordenesRepository,UsuarioRepository usuarioRepository) {
        this.ordenesRepository = ordenesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/ordenes")
    public String mostrarOrdenesAgente(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Ordenes> ordenes;

        // Si hay un término de búsqueda, buscar por cliente o estado de la orden
        if (search != null && !search.isEmpty()) {
            // Realiza la búsqueda en el repositorio por nombre de cliente o estado
            ordenes = ordenesRepository.findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCaseOrEstadoOrdenesContainingIgnoreCase(search, search, search);
        } else {
            // Si no hay búsqueda, obtener todas las órdenes
            ordenes = ordenesRepository.findAll();
        }

        // Pasar las órdenes al modelo para la vista
        model.addAttribute("ordenes", ordenes);

        // Pasar el término de búsqueda al modelo para que se mantenga en el campo de búsqueda
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_agente.html"
        return "Agente/ordenes_agente";
    }



    @GetMapping("/ordenes_en_progreso")
    public String mostrarOrdenesEnProgreso(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        List<Ordenes> ordenesEnProgreso;
        List<String> estadosPermitidos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA");

        // Si hay un término de búsqueda, primero filtramos por estado y luego por nombre/apellido
        if (search != null && !search.isEmpty()) {
            // Realizamos la búsqueda en base a nombre/apellido y estados permitidos
            ordenesEnProgreso = ordenesRepository.findByEstadoOrdenesInAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    estadosPermitidos, search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesEnProgreso.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con los estados 'EN PROCESO', 'ARRIBO AL PAÍS', 'EN ADUANAS', 'EN RUTA' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_en_progreso";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes con los estados permitidos
            ordenesEnProgreso = ordenesRepository.findByEstadoOrdenesIn(estadosPermitidos);
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesEnProgreso);
        model.addAttribute("search", search);

        return "Agente/ordenes_en_progreso";
    }





    @GetMapping("/ordenes_pendientes")
    public String mostrarOrdenesPendientes(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        List<Ordenes> ordenesEnValidacion;

        // Si hay un término de búsqueda, buscar por nombre o apellido solo si el estado es "En Validación"
        if (search != null && !search.isEmpty()) {
            // Búsqueda de órdenes con estado "En Validación"
            ordenesEnValidacion = ordenesRepository.findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    "EN VALIDACIÓN", search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesEnValidacion.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con el estado 'En Validación' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_pendientes";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes en estado "En Validación"
            ordenesEnValidacion = ordenesRepository.findAll().stream()
                    .filter(orden -> "EN VALIDACIÓN".equals(orden.getEstadoOrdenes()))
                    .collect(Collectors.toList());
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesEnValidacion);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_pendientes.html"
        return "Agente/ordenes_pendientes";
    }



    @GetMapping("/ordenes_sin_asignar")
    public String mostrarOrdenesSinAsignar(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        List<Ordenes> ordenesSinAsignar;

        // Si hay un término de búsqueda, buscar por nombre o apellido
        if (search != null && !search.isEmpty()) {
            // Búsqueda de órdenes con estado "CREADO"
            ordenesSinAsignar = ordenesRepository.findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    "CREADO", search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesSinAsignar.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con el estado 'Creado' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_sin_asignar";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes en estado "CREADO"
            ordenesSinAsignar = ordenesRepository.findAll().stream()
                    .filter(orden -> "CREADO".equals(orden.getEstadoOrdenes()))
                    .collect(Collectors.toList());
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesSinAsignar);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_sin_asignar.html"
        return "Agente/ordenes_sin_asignar";
    }


    @GetMapping("/ordenes_resueltas")
    public String mostrarOrdenesResueltas(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        List<Ordenes> ordenesResueltas;

        // Si hay un término de búsqueda, buscar por nombre o apellido
        if (search != null && !search.isEmpty()) {
            // Búsqueda de órdenes con estado "RECIBIDO"
            ordenesResueltas = ordenesRepository.findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    "RECIBIDO", search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesResueltas.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con el estado 'Recibido' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_resueltas";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes en estado "RECIBIDO"
            ordenesResueltas = ordenesRepository.findAll().stream()
                    .filter(orden -> "RECIBIDO".equals(orden.getEstadoOrdenes()))
                    .collect(Collectors.toList());
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesResueltas);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_resueltas.html"
        return "Agente/ordenes_resueltas";
    }



    // Método para mostrar la lista de usuarios en la vista con búsqueda por nombre o apellido
    @GetMapping("/usuarios_agente")
    public String usuariosAgente(@RequestParam(value = "search", required = false) String search, Model model) {
        // Crear una lista de estados permitidos
        List<String> estados = Arrays.asList("Activo", "Inactivo");

        List<Usuario> usuarios;

        // Si hay un término de búsqueda, buscar por nombre o apellido
        if (search != null && !search.isEmpty()) {
            usuarios = usuarioRepository.findByEstadoUsuarioIgnoreCaseInAndNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(estados, search, search);
        } else {
            // Si no hay búsqueda, obtener los usuarios cuyos estados sean "activo" o "inactivo"
            usuarios = usuarioRepository.findByEstadoUsuarioIgnoreCaseIn(estados);
        }

        // Pasar la lista de usuarios al modelo para la vista
        model.addAttribute("usuarios", usuarios);

        // Pasar el valor de búsqueda actual al modelo para mantenerlo en el campo de búsqueda
        model.addAttribute("search", search);

        // Retornar la vista "usuarios_agente.html"
        return "Agente/usuarios_agente";
    }

    // Método para listar los usuarios baneados o buscar por nombre y apellido
    @GetMapping("/usuarios_baneados")
    public String getUsuariosBaneados(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Usuario> usuariosBaneados;

        // Si hay un criterio de búsqueda, filtrar por nombre o apellido y motivo no nulo
        if (search != null && !search.isEmpty()) {
            usuariosBaneados = usuarioRepository.findByEstadoUsuarioAndNombreContainingOrApellidoContainingAndMotivoIsNotNull("Baneado", search, search);
        } else {
            // De lo contrario, mostrar solo los baneados con motivo no nulo
            usuariosBaneados = usuarioRepository.findByEstadoUsuarioAndMotivoIsNotNull("Baneado");
        }

        model.addAttribute("usuariosBaneados", usuariosBaneados);
        model.addAttribute("search", search); // Para mostrar el criterio de búsqueda en el input
        return "Agente/usuarios_baneados";
    }

    @PostMapping("/banear_usuario")
    public String banearUsuario(@RequestParam("usuarioId") Integer id,
                                @RequestParam("motivo") String motivo,
                                RedirectAttributes attr) {
        try {
            // Buscar el usuario en la base de datos
            Long idLong = Long.valueOf(id);  // Convierte el Integer a Long
            Usuario usuario = usuarioRepository.findById(idLong)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));


            // Cambiar el estado del usuario a "baneado"
            usuario.setEstadoUsuario("Baneado");

            // Guardar el motivo seleccionado
            usuario.setMotivo(motivo);

            // Guardar los cambios en la base de datos
            usuarioRepository.save(usuario);

            // Agregar mensaje de éxito
            attr.addFlashAttribute("successMessage", "Usuario baneado exitosamente con el motivo: " + motivo);
        } catch (Exception e) {
            // Agregar mensaje de error
            attr.addFlashAttribute("errorMessage", "No se pudo banear al usuario.");
        }

        // Redirigir a la lista de usuarios
        return "redirect:/agente/usuarios_agente";
    }


    // Método para listar los usuarios baneados
    /*@GetMapping("/usuarios_baneados")
    public String getUsuariosBaneados(Model model) {
        // Obtener la lista de usuarios donde estadoUsuario es 'Baneado'
        List<Usuario> usuariosBaneados = usuarioRepository.findByEstadoUsuario("Baneado");
        model.addAttribute("usuariosBaneados", usuariosBaneados);
        return "Agente/usuarios_baneados";
    }*/



}

