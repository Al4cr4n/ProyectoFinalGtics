package com.example.telexpress.controller;




import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.*;
import com.example.telexpress.service.ChatRoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/agente")
public class AgenteController {

    final OrdenesRepository ordenesRepository;
    final UsuarioRepository usuarioRepository;
    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final ProveedorRepository proveedorRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChatRoomService chatRoomService;

    public AgenteController(OrdenesRepository ordenesRepository, UsuarioRepository usuarioRepository, AdminRepository adminRepository,
                            ZonaRepository zonaRepository, ProductoRepository productoRepository, ProveedorRepository proveedorRepository,
                            ContrasenaAgenteRespository contrasenaAgenteRespository, UsuarioPerfilRepository usuarioPerfilRepository, CoordinadorRepository coordinadorRepository, PasswordEncoder passwordEncoder, ChatRoomService chatRoomService) {
        this.ordenesRepository = ordenesRepository;
        this.usuarioRepository = usuarioRepository;
        this.adminRepository = adminRepository;
        this.zonaRepository = zonaRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.contrasenaAgenteRespository = contrasenaAgenteRespository;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.passwordEncoder = passwordEncoder;
        this.chatRoomService = chatRoomService;
    }

    final ContrasenaAgenteRespository contrasenaAgenteRespository;
    final UsuarioPerfilRepository usuarioPerfilRepository;


    @GetMapping("/inicio")
    public String paginicio(Model model) {
        model.addAttribute("paginaActual", "inicio");
        return "Agente/inicio_agente";
    }

    @GetMapping("/ordenes")
    public String mostrarOrdenesAgente(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("paginaActual", "ordenes");
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO", "EN PROCESO");
        List<Ordenes> ordenes;

        // Si hay un término de búsqueda, primero filtramos por estado y luego por nombre/apellido
        if (search != null && !search.isEmpty()) {
            // Realizamos la búsqueda en base a nombre/apellido y estados permitidos
            ordenes = ordenesRepository.findByEstadoOrdenesInAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    estadosTodos, search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenes.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes con los estados permitidos para la zona del usuario actual
            ordenes = ordenesRepository.findByUsuario_ZonaAndEstadoOrdenesIn(zona, estadosTodos);
        }
        // Agregar mensaje si no hay órdenes
        if (ordenes.isEmpty()) {
            model.addAttribute("infoMessage", "No hay órdenes en tu zona.");
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenes);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_agente.html"
        return "Agente/ordenes_agente";
    }




    @GetMapping("/ordenes_en_progreso")
    public String mostrarOrdenesEnProgreso(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("paginaActual", "ordenes_en_progreso");

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();
        List<Ordenes> ordenesEnProgreso;
        List<String> estadosPermitidos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA");



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
            // Si no hay búsqueda, mostrar todas las órdenes con los estados permitidos filtrando por zona
            ordenesEnProgreso = ordenesRepository.findByUsuario_ZonaAndEstadoOrdenesIn(zona, estadosPermitidos);

            // Agregar mensaje si no hay órdenes en progreso
            if (ordenesEnProgreso.isEmpty()) {
                model.addAttribute("infoMessage", "No hay órdenes En Progreso en tu zona.");
            }
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesEnProgreso);
        model.addAttribute("search", search);

        return "Agente/ordenes_en_progreso";
    }







    @GetMapping("/ordenes_pendientes")
    public String mostrarOrdenesPendientes(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("paginaActual", "ordenes_pendientes");

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();
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
            // Si no hay búsqueda, mostrar todas las órdenes en estado "En Validación" filtrando por zona
            ordenesEnValidacion = ordenesRepository.findByUsuario_ZonaAndEstadoOrdenes(zona, "EN VALIDACIÓN");

            // Agregar mensaje si no hay órdenes en validación
            if (ordenesEnValidacion.isEmpty()) {
                model.addAttribute("infoMessage", "No hay órdenes Pendientes en tu zona.");
            }
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesEnValidacion);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_pendientes.html"
        return "Agente/ordenes_pendientes";
    }




    @GetMapping("/ordenes_sin_asignar")
    public String mostrarOrdenesSinAsignar(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("paginaActual", "ordenes_sin_asignar");

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();
        List<Ordenes> ordenesSinAsignar;

        // Si hay un término de búsqueda, buscar por nombre o apellido
        if (search != null && !search.isEmpty()) {
            // Búsqueda de órdenes con estado "CREADO" filtrando por zona
            ordenesSinAsignar = ordenesRepository.findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    "CREADO", search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesSinAsignar.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con el estado 'Creado' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_sin_asignar";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes en estado "CREADO" filtrando por zona
            ordenesSinAsignar = ordenesRepository.findByUsuario_ZonaAndEstadoOrdenes(zona, "CREADO");
        }

        // Agregar mensaje si no hay órdenes sin asignar
        if (ordenesSinAsignar.isEmpty()) {
            model.addAttribute("infoMessage", "No hay órdenes Sin Asignar en tu zona.");
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesSinAsignar);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_sin_asignar.html"
        return "Agente/ordenes_sin_asignar";
    }



    @GetMapping("/ordenes_resueltas")
    public String mostrarOrdenesResueltas(@RequestParam(value = "search", required = false) String search, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("paginaActual", "ordenes_resueltas");

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();
        List<Ordenes> ordenesResueltas;

        // Si hay un término de búsqueda, buscar por nombre o apellido
        if (search != null && !search.isEmpty()) {
            // Búsqueda de órdenes con estado "RECIBIDO" filtrando por zona
            ordenesResueltas = ordenesRepository.findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
                    "RECIBIDO", search, search);

            // Si no se encuentran resultados, mostrar un mensaje de error
            if (ordenesResueltas.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron órdenes con el estado 'Recibido' para el nombre o apellido proporcionado.");
                return "redirect:/agente/ordenes_resueltas";
            }
        } else {
            // Si no hay búsqueda, mostrar todas las órdenes en estado "RECIBIDO" filtrando por zona
            ordenesResueltas = ordenesRepository.findByUsuario_ZonaAndEstadoOrdenes(zona, "RECIBIDO");
        }

        // Agregar mensaje si no hay órdenes resueltas
        if (ordenesResueltas.isEmpty()) {
            model.addAttribute("infoMessage", "No hay órdenes Resueltas en tu zona.");
        }

        // Pasar las órdenes filtradas al modelo para la vista
        model.addAttribute("ordenes", ordenesResueltas);
        model.addAttribute("search", search);

        // Retornar la vista "ordenes_resueltas.html"
        return "Agente/ordenes_resueltas";
    }



    @GetMapping("/orden/editar")
    public String editarOrden(Model model, @RequestParam("idOrdenes") Integer idOrdenes) {
        model.addAttribute("activePage", "proveedores");

        Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(idOrdenes);
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO","EN PROCESO");

        if (optionalOrdenes.isPresent()) {
            Ordenes ordenes = optionalOrdenes.get();

            model.addAttribute("ordenes", ordenes);
            model.addAttribute("estadosPermitidos", estadosTodos); // Agrega los estados permitidos al modelo

            return "Agente/orden_editar";
        } else {
            return "redirect:/agente/ordenes";
        }
    }

    @PostMapping("/orden/guardar")
    public String guardarOrden(@Valid Ordenes ordenes, BindingResult result, RedirectAttributes attr, Model model) {
        model.addAttribute("activePage", "ordenes");
        Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(ordenes.getIdOrdenes());
        if (result.hasErrors()) {
            model.addAttribute("error", "Error al guardar la orden. Verifique los campos e intente nuevamente.");
            return "Agente/orden_editar";
        }
        Ordenes ordenFromDb = optionalOrdenes.get();
        ordenFromDb.setEstadoOrdenes(ordenes.getEstadoOrdenes());
        ordenFromDb.setFechaArribo(ordenes.getFechaArribo());
        ordenesRepository.save(ordenFromDb);

        attr.addFlashAttribute("success", "Orden guardada exitosamente.");
        return "redirect:/agente/ordenes";
    }



    // Método para mostrar la lista de usuarios en la vista con búsqueda por nombre o apellido
    @GetMapping("/usuarios_agente")
    public String usuariosAgente(@RequestParam(value = "search", required = false) String search,
                                 Model model, HttpSession session) {
        model.addAttribute("paginaActual", "usuarios_agente");

        // Obtener el agente autenticado desde la sesión
        Usuario agente = (Usuario) session.getAttribute("usuario");

        // Verificar que el agente esté presente en la sesión
        if (agente == null) {
            model.addAttribute("mensajeError", "Agente no encontrado en la sesión");
            return "error";  // Redirigir a una página de error si no se encuentra el agente
        }

        // Crear una lista de estados permitidos
        List<String> estados = Arrays.asList("Activo", "Inactivo");

        List<Usuario> usuarios;

        // Si hay un término de búsqueda, buscar por nombre o apellido y filtrar por idSuperior
        if (search != null && !search.isEmpty()) {
            usuarios = usuarioRepository.findByIdSuperior_IdAndEstadoUsuarioIgnoreCaseInAndNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
                    agente.getId(), estados, search, search);
        } else {
            // Si no hay búsqueda, obtener los usuarios con idSuperior igual al id del agente
            usuarios = usuarioRepository.findByIdSuperior_IdAndEstadoUsuarioIgnoreCaseIn(agente.getId(), estados);
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
        model.addAttribute("paginaActual", "usuarios_baneados");

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

    @GetMapping("/cambio_contra_agente")
    public String cambioContraAgente(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("id", id);
        // Obtener la contraseña almacenada en la base de datos
        return "Agente/cambio_contra_agente"; // Retornar la vista con el formulario
    }

    @PostMapping("/cambia_contra_agente")
    public String ActualizarContraAgente(
            @ModelAttribute("id") Integer id,
            @RequestParam("currentPassword") String currentPassword,  // Contraseña actual
            @RequestParam("newPassword") String newPassword, // Nueva contraseña
            @RequestParam("confirmPassword") String confirmNewPassword, // Confirmación de la nueva contraseña
            Model model) {


        System.out.println("ID recibido: " + id);
        // Obtener la contraseña almacenada en la base de datos
        String storedPassword = contrasenaAgenteRespository.findcontrasena(id);
        String hashcurrentPassword = passwordEncoder.encode(currentPassword);
        String hashnewPassword = passwordEncoder.encode(newPassword);
        String hashconfirmNewPassword = passwordEncoder.encode(confirmNewPassword);


        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(currentPassword, storedPassword)) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            System.out.println("1" + hashnewPassword);
            model.addAttribute("id", id);
            return "redirect:/agente/cambio_contra_agente"; // Redirigir a tu html
        }

        // Verificar que la nueva contraseña y su confirmación coincidan
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            System.out.println("2" + hashnewPassword);
            return "redirect:/agente/inicio"; // Retornar a la vista con mensaje de error
        }

        // Si la nueva contraseña es igual a la contraseña actual, prevenir el cambio
        if (currentPassword.equals(newPassword)) {
            model.addAttribute("error", "La nueva contraseña no puede ser igual a la contraseña actual.");
            System.out.println("3" + hashnewPassword);
            return "redirect:/agente/inicio"; // Retornar a la vista con mensaje de error
        }

        // Actualizar la contraseña en la base de datos
        contrasenaAgenteRespository.updatecontrasena(id, hashnewPassword);
        System.out.println("aaaa" +  newPassword);
        // Redireccionar al perfil del agente con mensaje de éxito
        model.addAttribute("success", "Contraseña cambiada exitosamente.");
        return "redirect:/agente/inicio"; // Redirigir al perfil
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, @RequestParam("id") Integer id) {

        Optional<Usuario> optUsuario = coordinadorRepository.findById(id);

        if (optUsuario.isPresent()) {
            Usuario agente = optUsuario.get();
            model.addAttribute("agente", agente);
            return "Agente/perfil_agente";
        } else {
            return "redirect:/agente";
        }
    }


    @GetMapping("/chat")
    public ModelAndView getMonitorPage(Model model, HttpSession session) {
        model.addAttribute("paginaActual", "chat_agente");
        Usuario agente = (Usuario) session.getAttribute("usuario");
        Integer idAgente = agente.getId() ;
        //Usuario name =  ;
        ArrayList<Integer> listUsuario= new ArrayList<>();
        listUsuario.add(18);
        listUsuario.add(4);
        listUsuario.add(5);
        listUsuario.add(6);

        ModelAndView modelAndView = new ModelAndView("Agente/chat_agente"); // Crea un ModelAndView con la vista correcta
        Set<String> activeRooms = chatRoomService.getActiveRooms(); // Obtiene las salas activas
        modelAndView.addObject("activeRooms", activeRooms);
        modelAndView.addObject("listUsuario", listUsuario);
        //modelAndView.addObject("name", name);
        modelAndView.addObject("idAgente", idAgente);// Agrega las salas activas al modelo
        return modelAndView; // Devuelve el ModelAndView
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

