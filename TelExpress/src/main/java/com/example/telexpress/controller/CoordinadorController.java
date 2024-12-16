package com.example.telexpress.controller;
import com.example.telexpress.config.EmailService;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController {
    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final EmailService emailService;
    final DistritoRepository distritoRepository;
    private final ContrasenaAgenteRespository contrasenaAgenteRespository;
    private final PasswordEncoder passwordEncoder;


    public CoordinadorController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                 ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                 ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository,
                                 CoordinadorRepository coordinadorRepository, EmailService emailService,
                                 DistritoRepository distritoRepository, ContrasenaAgenteRespository contrasenaAgenteRespository, PasswordEncoder passwordEncoder) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.emailService=emailService;
        this.distritoRepository=distritoRepository;
        this.contrasenaAgenteRespository = contrasenaAgenteRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/inicio_coordinador_zonal",""})
    public String inicioCoordinadorZonal(Model model) {
        model.addAttribute("paginaActual", "inicio");

        return "CoordinadorZonal/inicio_coordinador_zonal";
    }

    @GetMapping("/crearagente_zonal")
    public String crearAgenteCoordinadorZonal(Model model, Principal principal) {
        model.addAttribute("paginaActual", "agentes");
        // Obtener el usuario logueado
        Usuario usuarioLogueado = usuarioRepository.findByCorreo(principal.getName());
        if (usuarioLogueado == null) {
            throw new RuntimeException("Usuario logueado no encontrado");
        }

        // Obtener la zona del usuario logueado
        Zona zonaUsuario = usuarioLogueado.getZona();
        if (zonaUsuario == null) {
            throw new RuntimeException("El usuario logueado no tiene una zona asignada");
        }

        // Obtener los distritos pertenecientes a la zona del usuario logueado
        List<Distrito> distritos = distritoRepository.findByZona_Idzona(zonaUsuario.getIdzona());

        // Verificar si la lista de distritos está vacía o es nula
        if (distritos == null || distritos.isEmpty()) {
            // Manejo del caso donde no hay distritos encontrados
            throw new RuntimeException("No se encontraron distritos para la zona del usuario.");
        }

        // Agregar los distritos al modelo
        model.addAttribute("distritos", distritos);

        // Inicializa un objeto Usuario para el formulario del nuevo agente
        Usuario nuevoAgente = new Usuario();
        nuevoAgente.setRol(new Rol()); // Asigna un rol vacío que puedes configurar después

        // Inicializa el objeto Distrito dentro del nuevo agente
        nuevoAgente.setDistrito(new Distrito());  // Asegúrate de inicializar el distrito

        // Agregar el nuevo agente al modelo
        model.addAttribute("agente", nuevoAgente);

        // Pasar el objeto zona para saber a qué zona pertenece el nuevo agente
        model.addAttribute("zona", zonaUsuario);

        return "CoordinadorZonal/crearagente_zonal"; // Muestra la vista del formulario
    }




    @PostMapping("/guardaragente_zonal")
    public String guardarAgente(@ModelAttribute("agente") Usuario agenteNuevo, RedirectAttributes attr, Principal principal) {

        // Verificar si el agente es nuevo
        if (agenteNuevo.getId() == null) {

            // Obtener el usuario logueado para asignarle la zona al nuevo agente
            Usuario usuarioLogueado = usuarioRepository.findByCorreo(principal.getName());
            if (usuarioLogueado == null) {
                throw new RuntimeException("Usuario logueado no encontrado.");
            }

            // Obtener la zona del usuario logueado
            Zona zonaUsuario = usuarioLogueado.getZona();
            if (zonaUsuario == null) {
                throw new RuntimeException("El usuario logueado no tiene una zona asignada.");
            }

            // Asignar la misma zona del usuario logueado al nuevo agente
            agenteNuevo.setZona(zonaUsuario);

            // Asignar el rol de agente (ID de rol = 3)
            Rol rolAgente = new Rol();
            rolAgente.setId(4); // Asigna el ID 3 al rol de agente

            agenteNuevo.setRol(rolAgente);
            agenteNuevo.setSolicitud(1);//Postula para la validacion

            // Si el agente tiene un proveedor asociado, se guarda el proveedor
            if (agenteNuevo.getProveedor() != null && agenteNuevo.getProveedor().getNombreTienda() != null) {
                proveedorRepository.save(agenteNuevo.getProveedor());
            }

            // Guardar el nuevo agente en la base de datos
            adminRepository.save(agenteNuevo);
            attr.addFlashAttribute("successMessage", "Agente creado exitosamente.");
        }

        // Redirigir a la lista de agentes actualizada después de guardar
        return "redirect:/coordinador/listaagente_zonal";
    }


    @GetMapping({"/dashboard_zonal"})
    public String dashboardCoordinadorZonal(Model model) {
        model.addAttribute("paginaActual", "dashboard");

        // Obtener el correo del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario actual usando el correo
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);

        // Obtener la zona asociada al usuario actual
        Zona zona = usuarioActual.getZona();

        // Asegúrate de que la zona no sea nula
        if (zona == null) {
            // Manejar el caso cuando el usuario no tiene zona asignada
            model.addAttribute("error", "El usuario no tiene una zona asignada.");
            return "error"; // O la vista que desees mostrar
        }

        // Obtener la lista de productos para la zona del usuario actual
        List<Producto> listaTop = productoRepository.findProductosByZona(zona.getIdzona());
        listaTop.sort(Comparator.comparing(Producto::getCantidadComprada).reversed());
        model.addAttribute("listaTop", listaTop);

        // Contar usuarios activos e inactivos utilizando la zona
        long usuariosActivos = usuarioRepository.countByZonaAndEstadoUsuario(zona, "Activo");
        long usuariosInactivos = usuarioRepository.countByZonaAndEstadoUsuario(zona, "Inactivo");

        // Contar el número total de usuarios en la zona
        long totalUsuarios = usuarioRepository.countByZona(zona);

        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("usuariosActivos", usuariosActivos);
        model.addAttribute("usuariosInactivos", usuariosInactivos);

        // Limitar la lista de productos a los 10 más vendidos
        if (listaTop.size() > 10) {
            listaTop = listaTop.subList(0, 10);
        }

        List<String> colores = Arrays.asList(
                "#f1948a",
                "#6c757d",
                "#67c9c2",
                "#f5a9d0",
                "#ffc107",
                "#17a2b8",
                "#343a40",
                "#C39BD3",
                "#7FB3D5",
                "#76D7C4"
        );
        model.addAttribute("colores", colores);

        return "CoordinadorZonal/dashboard_zonal";
    }



    @GetMapping("/dashboard2_zonal")
    public String dashboard2CoordinadorZonal(Model model) {
        model.addAttribute("paginaActual", "dashboard");

        // Obtener el correo del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario actual usando el correo
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);

        // Obtener la zona asociada al usuario actual
        Zona zona = usuarioActual.getZona();

        // Asegúrate de que la zona no sea nula
        if (zona == null) {
            // Manejar el caso cuando el usuario no tiene zona asignada
            model.addAttribute("error", "El usuario no tiene una zona asignada.");
            return "error"; // O la vista que desees mostrar
        }

        // Obtener y ordenar los usuarios por cantidad de compras para la zona del usuario actual
        List<Usuario> listaTopUsuarios = usuarioRepository.findAllByZonaOrderByCantidadCompras(zona.getIdzona());

        // Limitar la lista a los 10 usuarios con más importaciones
        if (listaTopUsuarios.size() > 10) {
            listaTopUsuarios = listaTopUsuarios.subList(0, 10);
        }

        model.addAttribute("listaTopUsuarios", listaTopUsuarios);

        // Definir colores para la barra de progreso
        List<String> colores = Arrays.asList(
                "#f1948a",
                "#6c757d",
                "#67c9c2",
                "#f5a9d0",
                "#ffc107",
                "#17a2b8",
                "#343a40",
                "#C39BD3",
                "#7FB3D5",
                "#76D7C4"
        );
        model.addAttribute("colores", colores);

        return "CoordinadorZonal/dashboard2_zonal";
    }

    @GetMapping("/importacion_zonal")
    public String mostrarCalendario(Model model) {
        model.addAttribute("paginaActual", "importaciones");

        // Get products with arrival dates
        List<Producto> productosConFecha = productoRepository.findAll()
                .stream()
                .filter(producto -> producto.getFecharribo() != null)
                .collect(Collectors.toList());

        // Prepare events for FullCalendar
        List<Map<String, Object>> events = productosConFecha.stream()
                .map(producto -> {
                    Map<String, Object> event = new HashMap<>();
                    event.put("title", producto.getNombreProducto());
                    event.put("start", new SimpleDateFormat("yyyy-MM-dd").format(producto.getFecharribo()));
                    String categoriaNormalizada = Normalizer
                            .normalize(producto.getCategorias().getNombre(), Normalizer.Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "") // Eliminar acentos
                            .toLowerCase(); // Convertir a minúsculas
                    event.put("type", categoriaNormalizada);

                    // Asignar color según la categoría
                    String color;
                    switch (categoriaNormalizada) {
                        case "electronica":
                            color = "#007bff"; // Azul
                            break;
                        case "muebles":
                            color = "#28a745"; // Verde
                            break;
                        case "accesorios":
                            color = "#ffc107"; // Amarillo
                            break;
                        case "fotografia":
                            color = "#6c757d"; // Gris
                            break;
                        case "oficina":
                            color = "#dc3545"; // Rojo
                            break;
                        case "limpieza":
                            color = "#fd7e14"; // Naranja
                            break;
                        default:
                            color = "#6c757d"; // Gris por defecto
                    }
                    event.put("backgroundColor", color); // Color del fondo
                    event.put("borderColor", color); // Color del borde (opcional)

                    return event;
                })
                .collect(Collectors.toList());

        model.addAttribute("events", events);
        model.addAttribute("productos", productosConFecha);


        return "CoordinadorZonal/importacion_zonal";
    }





    /*@GetMapping({"/importacion_zonal"})
    public String importacionCoordinadorZonal(Model model) {
        model.addAttribute("paginaActual", "importaciones");

        return "CoordinadorZonal/importacion_zonal";
    }*/

    @GetMapping({"/listaagente_zonal"})
    public String listaAgenteCoordinadorZonal(Model model, Principal principal) {
        model.addAttribute("paginaActual", "agentes");

        // Obtener el usuario logueado
        Usuario usuarioLogueado = usuarioRepository.findByCorreo(principal.getName());
        if (usuarioLogueado == null) {
            throw new RuntimeException("Usuario logueado no encontrado");
        }

        // Obtener la zona del usuario logueado
        Zona zonaUsuario = usuarioLogueado.getZona();
        if (zonaUsuario == null) {
            throw new RuntimeException("El usuario logueado no tiene una zona asignada");
        }

        // Obtener los agentes de compra que pertenecen a la misma zona del usuario logueado
        List<Usuario> listaAgentesZonal = coordinadorRepository.buscarAgentePorIdSuperior(usuarioLogueado.getId());

        // Pasar la lista de agentes al modelo
        model.addAttribute("lista_agentes_zonal", listaAgentesZonal);

        return "CoordinadorZonal/listaagente_zonal"; // Retorna la vista con la lista de agentes
    }

    @GetMapping({"/perfilagente_zonal"})
    public String perfilAgenteCoordinadorZonal(Model model, @RequestParam("id") Integer id) {

        Optional<Usuario> optionalUsuario = adminRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario agente = optionalUsuario.get();
            model.addAttribute("agente", agente);
            return "CoordinadorZonal/perfilagente_zonal";
        } else {
            return "CoordinadorZonal/listaagente_zonal";
        }
    }

    @GetMapping("/productos_zonal")
    public String productosCoordinadorZonal(Model model) {
        model.addAttribute("paginaActual", "productos");
        // Obtener el correo del usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario actual usando el correo
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);

        // Obtener la zona asociada al usuario actual
        Zona zona = usuarioActual.getZona();

        List<Producto> producto = productoRepository.findProductosByZona(zona.getIdzona());
        producto.sort(Comparator.comparing(Producto::getCantidadDisponible));
        model.addAttribute("producto", producto);
        return "CoordinadorZonal/productos_zonal";
    }

    @PostMapping("/solicitarReponer")
    public String solicitarReposicion(@RequestParam("nombreProducto") String nombreProducto,
                                      @RequestParam("categoria") String categoria,
                                      @RequestParam("email") String email) {
        // Llamar al servicio de correo
        emailService.enviarEmailSolicitud(email, nombreProducto, categoria);

        // Redirigir a la vista de productos con un mensaje de éxito
        return "redirect:/productos_zonal?solicitudExitosa";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, @RequestParam("id") int id) {

        Optional<Usuario> optUsuario = coordinadorRepository.findById(id);

        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            model.addAttribute("coordinador", usuario);
            return "CoordinadorZonal/perfil_coordinador";
        } else {
            return "redirect:/coordinador";
        }
    }

    @PostMapping("/actualizarFechaArribo")
    @Transactional
    public String actualizarFechaArribo(@RequestParam("productoId") int productoId,
                                        @RequestParam("fechaArribo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaArribo,
                                        RedirectAttributes redirectAttributes) {
        try {
            int filasActualizadas = productoRepository.actualizarFechaArribo(productoId, fechaArribo);
            if (filasActualizadas > 0) {
                redirectAttributes.addFlashAttribute("successMessage", "Fecha de arribo actualizada con éxito");
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "No se encontró el producto para actualizar");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar la fecha de arribo: " + e.getMessage());
        }
        return "redirect:/coordinador/productos_zonal";
    }

    @GetMapping("/cambio_contra")
    public String cambioContraAgente(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("id", id);
        // Obtener la contraseña almacenada en la base de datos
        return "CoordinadorZonal/cambio_contra"; // Retornar la vista con el formulario
    }

    @PostMapping("/cambia_contra")
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
            return "redirect:/coordinador/inicio_coordinador_zonal"; // Redirigir a tu html
        }

        // Verificar que la nueva contraseña y su confirmación coincidan
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            System.out.println("2" + hashnewPassword);
            return "redirect:/coordinador/inicio_coordinador_zonal"; // Retornar a la vista con mensaje de error
        }

        // Si la nueva contraseña es igual a la contraseña actual, prevenir el cambio
        if (currentPassword.equals(newPassword)) {
            model.addAttribute("error", "La nueva contraseña no puede ser igual a la contraseña actual.");
            System.out.println("3" + hashnewPassword);
            return "redirect:/coordinador/inicio_coordinador_zonal"; // Retornar a la vista con mensaje de error
        }

        // Actualizar la contraseña en la base de datos
        contrasenaAgenteRespository.updatecontrasena(id, hashnewPassword);
        System.out.println("aaaa" +  newPassword);
        // Redireccionar al perfil del agente con mensaje de éxito
        model.addAttribute("success", "Contraseña cambiada exitosamente.");
        return "redirect:/coordinador/inicio_coordinador_zonal"; // Redirigir al perfil
    }


}
