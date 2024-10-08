package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoUsuario;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.entity.Proveedor;
import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Rol;
import com.example.telexpress.repository.*;
import com.example.telexpress.entity.Distrito;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Optional;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;
    final DistritoRepository distritoRepository;


    public SuperAdminController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository,
                                DistritoRepository distritoRepository
                                ) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
        this.distritoRepository=distritoRepository;

    }



    /*@GetMapping("")
    public String index(){

        return "SuperAdmin/inicio_superadmin";
    }*/

    /*METODO DE TODAS LAS LISTAS DE LA VISTA PRINCIPAL*/

    @GetMapping("/inicio_superadmin")
    public String inicioSuperadmin(Model model){
        List<Usuario> usuarios = adminRepository.buscarUsuarioPorRol(4);
        System.out.println("Usuarios: " + usuarios);
        model.addAttribute("listaUsuario", usuarios);
        model.addAttribute("listaAgente",adminRepository.buscarUsuarioPorRol(3));
        model.addAttribute("listaCoordi",adminRepository.buscarUsuarioPorRol(2));
        /*model.addAttribute("listaAgente",adminRepository.buscarAgentePorRol(3));
        model.addAttribute("listaCoordinador",adminRepository.buscarCoordiPorRol(2));*/

        return "SuperAdmin/inicio_superadmin";
    }
    /*busquedas en pagina inicial*/

    @GetMapping("/buscador/buscarusuarios")
    public String busquedauserInicio(Usuario usuario, @RequestParam("searchTerm") String searchTerm, Model model){
        int rolId = 4;  // El rol de "usuarios" es 4
        List<Usuario> listauser = adminRepository.searchByNameOrDniAndRol(searchTerm, rolId);
        //List<Usuario> listauser = adminRepository.searchByNameOrDni(searchTerm);
        model.addAttribute("listaUsuario", listauser);
        model.addAttribute("listaAgente",adminRepository.buscarUsuarioPorRol(3));
        model.addAttribute("listaCoordi",adminRepository.buscarUsuarioPorRol(2));
        return "SuperAdmin/inicio_superadmin";
    }
    /* AGENTES  */
    @GetMapping("/buscador/buscaragentes")
    public String busquedaAgentes(@RequestParam("searchTermAgente") String searchTermAgente, Model model){
        int rolId = 3;  // El rol de "usuarios" es 4
        List<Usuario> listaAgentes = adminRepository.searchByNameOrDniAndRol(searchTermAgente, rolId);
        model.addAttribute("listaUsuario", adminRepository.buscarUsuarioPorRol(4));
        model.addAttribute("listaAgente", listaAgentes);
        model.addAttribute("listaCoordi", adminRepository.buscarUsuarioPorRol(2));
        return "SuperAdmin/inicio_superadmin";
    }
    /* COORDINADOR ZONAL */
    @GetMapping("/buscador/buscarcoordis")
    public String busquedaZonales(@RequestParam("searchTermZonal") String searchTermZonal, Model model){
        int rolId = 2;  // El rol de "usuarios" es 4
        List<Usuario> listazonales = adminRepository.searchByNameOrDniAndRol(searchTermZonal, rolId);
        model.addAttribute("listaUsuario", adminRepository.buscarUsuarioPorRol(4));
        model.addAttribute("listaAgente", adminRepository.buscarUsuarioPorRol(3));
        model.addAttribute("listaCoordi", listazonales);
        return "SuperAdmin/inicio_superadmin";
    }

    /* ************************* **/
    /* ELIMINAR EN LA PRIMERA TABLA DE INICIO */
    @GetMapping("/eliminarusuario_sa")
    public String borrarUsuarioIncioSA(Model model,
                                       @RequestParam("id") int id,
                                       RedirectAttributes attr) {

        try {
            Optional<Usuario> optUser = adminRepository.findById(id);

            if (optUser.isPresent()) {
                Usuario usuario = optUser.get();

                // Verificar si el usuario está relacionado con órdenes
                List<Ordenes> ordenes = ordenesRepository.findByUsuarioId(id);

                if (ordenes.isEmpty()) {
                    // Si no hay relaciones, eliminar el usuario
                    adminRepository.deleteById(id);
                    attr.addFlashAttribute("success", "Usuario eliminado exitosamente.");
                } else {
                    // Si hay órdenes relacionadas, eliminar las órdenes antes de eliminar el usuario
                    //ordenesRepository.deleteAll(ordenes);
                    //adminRepository.deleteById(id);
                    attr.addFlashAttribute("error", "Error al eliminar. El usuario cuenta con ordenes pendientes");
                }
            } else {
                attr.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception ex) {
            attr.addFlashAttribute("error", "No se puede eliminar al usuario debido a que tiene relaciones.");
            ex.printStackTrace();
        }
        return "redirect:/superadmin/inicio_superadmin";
    }

    /* ELIMINAR EN LA segunda TABLA DE INICIO */
    @GetMapping("/borraragente_sa")
    public String borrarAgenteInicioSA(Model model,
                                       @RequestParam("id") int id,
                                       RedirectAttributes attraa) {

        try{
            Optional<Usuario> optUser = adminRepository.findById(id);

            if (optUser.isPresent()) {
                Usuario usuario = optUser.get();

                // Verificar si el usuario está relacionado con órdenes
                List<Ordenes> ordenes = ordenesRepository.findByUsuarioId(id);

                if (ordenes.isEmpty()) {
                    // Si no hay relaciones, eliminar el usuario
                    adminRepository.deleteById(id);
                    attraa.addFlashAttribute("success", "Usuario eliminado exitosamente.");
                } else {
                    // Si hay órdenes relacionadas, eliminar las órdenes antes de eliminar el usuario
                    //ordenesRepository.deleteAll(ordenes);
                    //adminRepository.deleteById(id);
                    attraa.addFlashAttribute("error", "Error al eliminar. El usuario cuenta con ordenes pendientes");
                }
            } else {
                attraa.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception ex) {
            attraa.addFlashAttribute("error", "No se puede eliminar al usuario debido a que tiene relaciones.");
            ex.printStackTrace();
        }
        return "redirect:/superadmin/inicio_superadmin";
    }


    /*USUARIO EDITAR, GUARDAR Y BORRAR*/

    @GetMapping("/editar/{id}")
    public String editarUsuario(Model model, @PathVariable("id") Integer id){

        Optional<Usuario> optUsuario = adminRepository.findById(id);
        if (optUsuario.isPresent()){
            Usuario usuario = optUsuario.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("distritos", distritoRepository.findAll());

            return "SuperAdmin/editar_usuario";
        }else{
            return "redirect:/superadmin/gestion_usuarios";
        }
    }

    @GetMapping("/crear_usuario")
    public String guardarUsuario(Model model, RedirectAttributes attr) {
        List<Distrito> distritos = distritoRepository.findAll();
        model.addAttribute("distritos",distritos);
        model.addAttribute("usuario",new Usuario());

        return "SuperAdmin/editar_usuario";
    }
    @PostMapping("/guardar_usuario")
    public String guardarUsuario(Usuario usuario, RedirectAttributes attr,@RequestParam("distrito.id") Integer iddist ) {
        // Verificar si el DNI, correo o número telefónico ya existen
        Optional<Usuario> usuarioConMismoDni = adminRepository.findByDni(usuario.getDni());
        Optional<Usuario> usuarioConMismoCorreo = adminRepository.findByCorreo(usuario.getCorreo());
        Optional<Usuario> usuarioConMismoTelefono = adminRepository.findByTelefono(usuario.getTelefono());

        // Verificar si el DNI ya está en uso por otro usuario
        if (usuarioConMismoDni.isPresent() && !usuarioConMismoDni.get().getId().equals(usuario.getId())) {
            attr.addFlashAttribute("mensaje", "El DNI ya está registrado en otro usuario.");
            attr.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }

        // Verificar si el correo ya está en uso por otro usuario
        if (usuarioConMismoCorreo.isPresent() && !usuarioConMismoCorreo.get().getId().equals(usuario.getId())) {
            attr.addFlashAttribute("mensaje", "El correo ya está registrado en otro usuario.");
            attr.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }

        // Verificar si el número telefónico ya está en uso por otro usuario
        if (usuarioConMismoTelefono.isPresent() && !usuarioConMismoTelefono.get().getId().equals(usuario.getId())) {
            attr.addFlashAttribute("mensaje", "El número telefónico ya está registrado en otro usuario.");
            attr.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }


            // Asignar el rol de usuario (idroles = 4)
            Rol rolusuario = new Rol();
            rolusuario.setId(4); // se ingresa el id del rol usuario

            usuario.setRol(rolusuario); // se le asigna a este nuevo usuario el rol
            Distrito distrito = distritoRepository.findById(iddist).orElse(null);
            if(distrito!= null){
                usuario.setDistrito(distrito);
            }

            // Asignar la zona basada en el distrito seleccionado
            Zona zona = distrito.getZona(); // Obtener la zona desde el distrito
            usuario.setZona(zona); // Asignar la zona al usuario

            adminRepository.save(usuario);
            attr.addFlashAttribute("success", "Usuario guardado correctamente.");


        return "redirect:/superadmin/gestion_usuarios";
    }




    @GetMapping("/eliminar/{id}")
    public String borrarUsuario(Model model,
                                @PathVariable("id") int id,
                                RedirectAttributes attr) {

        try {
            Optional<Usuario> optUser = adminRepository.findById(id);

            if (optUser.isPresent()) {
                Usuario usuario = optUser.get();

                // Verificar si el usuario está relacionado con órdenes
                List<Ordenes> ordenes = ordenesRepository.findByUsuarioId(id);

                if (ordenes.isEmpty()) {
                    // Si no hay relaciones, eliminar el usuario
                    adminRepository.deleteById(id);
                    attr.addFlashAttribute("success", "Usuario eliminado exitosamente.");
                } else {
                    // Si hay órdenes relacionadas, eliminar las órdenes antes de eliminar el usuario
                    //ordenesRepository.deleteAll(ordenes);
                    //adminRepository.deleteById(id);
                    attr.addFlashAttribute("error", "Error al eliminar. El usuario cuenta con ordenes pendientes");
                }
            } else {
                attr.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception ex) {
            attr.addFlashAttribute("error", "No se puede eliminar al usuario debido a que tiene relaciones.");
            ex.printStackTrace();
        }
        return "redirect:/superadmin/gestion_usuarios";
    }

    @GetMapping("/nuevo_usuario")
    public String nuevoUsuario(Model model){
        model.addAttribute("listaZona", zonaRepository.findAll());
        return "Superadmin/crear_usuario";
    }

    @GetMapping("/inventario_registrar_producto")
    public String inventarioRegistrarSuperadmin() {


        return "SuperAdmin/inventario_registrar_producto";
    }

    @GetMapping("/inventario_editar_producto")
    public String inventarioEditarSuperadmin() {


        return "SuperAdmin/inventario_editar_producto";
    }



    @GetMapping("/gestion_coordinadores")
    public String gestionCoordinadoresSuperadmin(Model model) {
        List<Usuario> listazonal = adminRepository.buscarUsuarioPorRol(2);
        for(Usuario cz : listazonal){
            System.out.println("Zonal ID: " + cz.getId());
        }
        model.addAttribute("coordinadores",listazonal);
        return "SuperAdmin/gestion_coordinadores";
    }
/*para gestionar coordinador zonal*/
    @GetMapping("/coordinador_zonal_formu/{id}")
    public String CoordinadorZonalFormularioSuperadmin(Model model, @PathVariable("id") Integer id) {
        Optional<Usuario> coordinador = adminRepository.findById(id);
        if (coordinador.isPresent()) {
            Usuario user = coordinador.get();

            if (user.getFechanacimiento() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = user.getFechanacimiento().format(formatter);
                model.addAttribute("formattedDate", formattedDate);  // Agregar fecha formateada al modelo
            }
            model.addAttribute("coordinadorzonal", user);
            List<Zona> zonas = zonaRepository.findAll();
            model.addAttribute("zonas",zonas);

            return "SuperAdmin/coordinador_zonal_formulario";
        } else {
            return "redirect:/superadmin/gestion_coordinadores";
        }
    }
/*para gestionar coordinador zonal*/
    @GetMapping("/new")
    public String crearnuevocoordi(Model model){
        List<Zona> zonas = zonaRepository.findAll();
        model.addAttribute("zonas", zonas);
        model.addAttribute("coordinadorzonal", new Usuario()); //creacion
        return "SuperAdmin/coordinador_zonal_formulario";
    }

    /* para actualizar datos de coodinador zonal*/
    @PostMapping("/update")
    public String actualizardatoscoordi(Usuario usuario, @RequestParam("zona.idzona") Integer zonaid, RedirectAttributes rttbtsp) {

        // Verificar si el DNI, correo o número telefónico ya existen
        Optional<Usuario> usuarioConMismoDni = adminRepository.findByDni(usuario.getDni());
        Optional<Usuario> usuarioConMismoCorreo = adminRepository.findByCorreo(usuario.getCorreo());
        Optional<Usuario> usuarioConMismoTelefono = adminRepository.findByTelefono(usuario.getTelefono());

        // Verificar si el DNI ya está en uso por otro usuario
        if (usuarioConMismoDni.isPresent() && !usuarioConMismoDni.get().getId().equals(usuario.getId())) {
            rttbtsp.addFlashAttribute("mensaje", "El DNI ya está registrado en otro usuario.");
            rttbtsp.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }

        // Verificar si el correo ya está en uso por otro usuario
        if (usuarioConMismoCorreo.isPresent() && !usuarioConMismoCorreo.get().getId().equals(usuario.getId())) {
            rttbtsp.addFlashAttribute("mensaje", "El correo ya está registrado en otro usuario.");
            rttbtsp.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }

        // Verificar si el número telefónico ya está en uso por otro usuario
        if (usuarioConMismoTelefono.isPresent() && !usuarioConMismoTelefono.get().getId().equals(usuario.getId())) {
            rttbtsp.addFlashAttribute("mensaje", "El número telefónico ya está registrado en otro usuario.");
            rttbtsp.addFlashAttribute("tipoMensaje", "danger");
            return "redirect:/superadmin/gestion_coordinadores";
        }

        // Asignar el rol de Coordinador (idroles = 2)
        Rol rolCoordinador = new Rol();
        rolCoordinador.setId(2); // Asegúrate de que el método setId exista en la clase Rol

        usuario.setRol(rolCoordinador); // Asignar el rol al usuario

        Zona zona = zonaRepository.findById(zonaid).orElse(null);
        if (zona != null) {
            usuario.setZona(zona); // Asignar la zona seleccionada al usuario
        }
        adminRepository.save(usuario);

        // Mensaje de éxito
        rttbtsp.addFlashAttribute("mensaje", "Los datos del coordinador fueron actualizados con éxito.");
        rttbtsp.addFlashAttribute("tipoMensaje", "success");
        return "redirect:/superadmin/gestion_coordinadores";
    }

    /*para gestionar coordinador zonal*/
   /* @GetMapping("/deletecoordi")
    public String eliminarcoordi(Model model, @RequestParam("id") Integer id){
        Optional <Usuario> coordi = adminRepository.findById(id);
        if (coordi.isPresent()){
            adminRepository.deleteById(id);
        }else {
            System.out.println("No se encontró el coordinador con ID: " + id);
        }
        return "redirect:/superadmin/gestion_coordinadores";
    }*/
   /* @GetMapping("/deletecoordi")
    public String eliminarCoordi(@RequestParam("id") Integer id, Model model) {
        Optional<Usuario> coordi = adminRepository.findById(id);
        if (coordi.isPresent()) {
            try {
                adminRepository.deleteById(id);  // Este método ya eliminará también las órdenes si configuras cascada
                System.out.println("Coordinador y sus órdenes eliminados correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar el coordinador con ID: " + id + ". " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el coordinador con ID: " + id);
        }
        return "redirect:/superadmin/gestion_coordinadores";
    }*/
    @GetMapping("/deletecoordi")
    public String eliminarCoordi(Model model,@RequestParam("id") Integer id, RedirectAttributes attrbt) {
        try {
            Optional<Usuario> optUser = adminRepository.findById(id);

            if (optUser.isPresent()) {
                Usuario usuario = optUser.get();

                // Verificar si el usuario está relacionado con órdenes
                List<Ordenes> ordenes = ordenesRepository.findByUsuarioId(id);

                if (ordenes.isEmpty()) {
                    // Si no hay relaciones, eliminar el usuario
                    adminRepository.deleteById(id);
                    attrbt.addFlashAttribute("success", "Coordinador zonal eliminado exitosamente.");
                } else {
                    // Si hay órdenes relacionadas, eliminar las órdenes antes de eliminar el usuario
                    ordenesRepository.deleteAll(ordenes);
                    adminRepository.deleteById(id);
                    attrbt.addFlashAttribute("success", "Usuario y sus órdenes eliminados exitosamente.");
                }
            } else {
                attrbt.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception ex) {
            attrbt.addFlashAttribute("error", "No se puede eliminar al coordinador debido tiene procesos no finalizados.");
            ex.printStackTrace();
        }
        return "redirect:/superadmin/gestion_coordinadores";
    }




    @GetMapping("/buscadorcoordinador")
    public String buscador_coordinador_zonal(Usuario usuario, @RequestParam("searchTerm") String  searchTerm, Model model){
        int rolId = 2;  // El rol de "usuarios" es 4
        List<Usuario> listazonal = adminRepository.searchByNameOrDniAndRol(searchTerm, rolId);
        //List<Usuario> listazonal = adminRepository.searchByNameOrDni(searchTerm);
        model.addAttribute("coordinadores", listazonal);
        return "SuperAdmin/gestion_coordinadores";
    }
    @GetMapping("/gestion_usuarios")
    public String gestionUsuariosSuperadmin(Model model) {
        List<Usuario> listaUsuario= adminRepository.buscarUsuarioPorRol(4);
        model.addAttribute("listaUsuario",listaUsuario);
        return "SuperAdmin/gestion_usuarios";
    }



    @GetMapping("/modificar_usuario")
    public String modificarUsuarioSuperadmin() {


        return "SuperAdmin/modificar_usuario";
    }

    @GetMapping("/gestion_agentes")
    public String listar_agentes(Model model) {
        List<Usuario> lista_agentes = adminRepository.buscarAgentePorRol();
        model.addAttribute("lista_agentes",lista_agentes);
        return "SuperAdmin/gestion_agentes";
    }

    @GetMapping("/editar_agentes")
    public String editar_agentes(Model model, @RequestParam("id") Integer id) {
        Optional<Usuario> optionalUsuario = adminRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario agente = optionalUsuario.get();
            model.addAttribute("agente", agente);
            return "SuperAdmin/editar_agente";
        } else {
            return "SuperAdmin/gestion_agentes";
        }
    }

    /* para actualizar datos de agente*/
    @PostMapping("/actualizar_agentes")
    public String actualizar_agentes(Usuario agenteActualizado, RedirectAttributes attr) {
        if (agenteActualizado.getId() != null) { // Si es una edición
            Optional<Usuario> optAgenteExistente = adminRepository.findById(agenteActualizado.getId());

            if (optAgenteExistente.isPresent()) {
                Usuario agenteExistente = optAgenteExistente.get();

                // Actualizar solo los campos modificados desde el formulario
                agenteExistente.setNombre(agenteActualizado.getNombre());
                agenteExistente.setApellido(agenteActualizado.getApellido());
                agenteExistente.setTelefono(agenteActualizado.getTelefono());

                // Actualizar el nombre de la tienda del proveedor, si existe un proveedor asociado
                if (agenteActualizado.getProveedor() != null && agenteActualizado.getProveedor().getNombreTienda() != null) {
                    Proveedor proveedorExistente = agenteExistente.getProveedor();

                    if (proveedorExistente != null) {
                        proveedorExistente.setNombreTienda(agenteActualizado.getProveedor().getNombreTienda());
                        proveedorRepository.save(proveedorExistente); // Guardar cambios en el proveedor
                    }
                }

                // Si la zona ha sido modificada, actualizarla
                if (agenteActualizado.getZona() != null) {
                    agenteExistente.setZona(agenteActualizado.getZona());
                }

                // Guardar los cambios del agente
                adminRepository.save(agenteExistente);
            }
        } else { // Si es un nuevo registro
            adminRepository.save(agenteActualizado);
        }

        return "redirect:/superadmin/gestion_agentes";
    }

    /*para borrar agente*/
    @GetMapping("/borrar_agentes")
    public String borrar_agentes(Model model, @RequestParam("id") int id, RedirectAttributes attr) {
        System.out.println("Eliminando agente con ID: " + id);
        try {
            // Buscar al agente por ID
            Optional<Usuario> optAgente = adminRepository.findById(id);

            if (optAgente.isPresent()) {
                Usuario agente = optAgente.get();

                // Buscar todas las órdenes asociadas al agente
                List<Ordenes> ordenesAgente = ordenesRepository.findByUsuarioId(agente.getId());

                if (ordenesAgente.isEmpty()) {
                    // Si no hay órdenes, puedes eliminar al agente
                    adminRepository.deleteById(id);
                    attr.addFlashAttribute("success", "Agente eliminado exitosamente.");
                } else {
                    // Si hay órdenes, mostrar un mensaje de error sin eliminar
                    attr.addFlashAttribute("error", "No se puede eliminar el agente porque tiene órdenes pendientes.");
                }
            } else {
                attr.addFlashAttribute("error", "Agente no encontrado.");
            }
        } catch (EntityNotFoundException ex) {
            // Excepción si no se encuentra al agente
            attr.addFlashAttribute("error", "No se pudo encontrar el agente con el ID especificado.");
        } catch (Exception ex) {
            // Manejo de otras excepciones inesperadas
            attr.addFlashAttribute("error", "No se puede eliminar el agente debido a un error inesperado.");
            ex.printStackTrace();  // Útil para depuración, elimínalo en producción
        }
        return "redirect:/superadmin/gestion_agentes";
    }

    @GetMapping("/agente/buscar")
    public String busquedaAgentes2(@RequestParam("searchTermAgente2") String searchTermAgente2, Model model){
        int rolId = 3;  // El rol de "usuarios" es 4
        List<Usuario> lista_agentes = adminRepository.searchByNameOrDniAndRol(searchTermAgente2, rolId);
        model.addAttribute("listaUsuario", adminRepository.buscarUsuarioPorRol(4));
        System.out.println("Resultados encontrados: " + lista_agentes.size());
        model.addAttribute("lista_agentes", lista_agentes);
        model.addAttribute("listaCoordi", adminRepository.buscarUsuarioPorRol(2));
        return "SuperAdmin/gestion_agentes";
    }

    @GetMapping("/agente/filtrar")
    public String filtrarAgentesPorEstado(@RequestParam(value = "estado", required = false) String estado, Model model) {
        List<Usuario> agentesFiltrados;

        if (estado == null || estado.isEmpty()) {
            // Si no se selecciona ningún estado, obtener todos los agentes con rol "Agente" (id = 3)
            agentesFiltrados = usuarioRepository.findByRol_Id(3);
        } else {
            // Si se selecciona un estado, aplicar el filtro por estado
            agentesFiltrados = usuarioRepository.findByRol_IdAndCodigoDespachador_Estado(3, estado);
        }

        model.addAttribute("lista_agentes", agentesFiltrados);
        model.addAttribute("estadoSeleccionado", estado);  // Esto ayuda a mantener el valor seleccionado en el HTML
        return "Superadmin/gestion_agentes";
    }

    @GetMapping("/rol_agente_solicitudes")
    public String rolAgenteSolicitudesSuperadmin() {


        return "SuperAdmin/rol_agente_solicitudes";
    }

    @GetMapping("/solicitud_agente")
    public String solicitudAgenteSuperadmin() {


        return "SuperAdmin/solicitud_agente";
    }

    @GetMapping({ "/dashboard_superadmin"})
    public String listasDashboard(Model model) {

        List<Producto> listaTop = productoRepository.findAll();
        listaTop.sort(Comparator.comparing(Producto::getCantidadComprada).reversed());

        model.addAttribute("listaTop", listaTop);

        if (listaTop.size() > 10) {
            listaTop = listaTop.subList(0, 10);
        }



        long totalUsuarios = usuarioRepository.count();
        long usuariosActivos = usuarioRepository.countByEstadoUsuario("Activo");
        long usuariosInactivos = usuarioRepository.countByEstadoUsuario("Inactivo");
        long usuariosBaneados = usuarioRepository.countByEstadoUsuario("Baneado");
        long usuariosAgentes = usuarioRepository.countByRol_Id(3L);
        long proveedoresBaneados = proveedorRepository.countByEstadoProveedor("baneado");
        long ordenesCreado = ordenesRepository.countByEstadoOrdenes("CREADO");
        long ordenesEnValidacion = ordenesRepository.countByEstadoOrdenes("EN VALIDACIÓN");
        long ordenesEnProceso = ordenesRepository.countByEstadoOrdenes("EN PROCESO");
        long ordenesArriboAlPais = ordenesRepository.countByEstadoOrdenes("ARRIBO AL PAÍS");
        long ordenesEnAduanas = ordenesRepository.countByEstadoOrdenes("EN ADUANAS");
        long ordenesEnRuta = ordenesRepository.countByEstadoOrdenes("EN RUTA");
        long ordenesRecibido = ordenesRepository.countByEstadoOrdenes("RECIBIDO");
        long ordenesEnero = ordenesRepository.countByMesCreacion("Enero");
        long ordenesFebrero = ordenesRepository.countByMesCreacion("Febrero");
        long ordenesMarzo = ordenesRepository.countByMesCreacion("Marzo");
        long ordenesAbril = ordenesRepository.countByMesCreacion("Abril");
        long ordenesMayo = ordenesRepository.countByMesCreacion("Mayo");
        long ordenesJunio = ordenesRepository.countByMesCreacion("Junio");
        long ordenesJulio = ordenesRepository.countByMesCreacion("Julio");
        long ordenesAgosto = ordenesRepository.countByMesCreacion("Agosto");
        long ordenesSeptiembre = ordenesRepository.countByMesCreacion("Septiembre");
        long ordenesOctubre = ordenesRepository.countByMesCreacion("Octubre");
        long ordenesNoviembre = ordenesRepository.countByMesCreacion("Noviembre");
        long ordenesDiciembre = ordenesRepository.countByMesCreacion("Diciembre");


        model.addAttribute("ordenesEnero", ordenesEnero);
        model.addAttribute("ordenesFebrero", ordenesFebrero);
        model.addAttribute("ordenesMarzo", ordenesMarzo);
        model.addAttribute("ordenesAbril", ordenesAbril);
        model.addAttribute("ordenesMayo", ordenesMayo);
        model.addAttribute("ordenesJunio", ordenesJunio);
        model.addAttribute("ordenesJulio", ordenesJulio);
        model.addAttribute("ordenesAgosto", ordenesAgosto);
        model.addAttribute("ordenesSeptiembre", ordenesSeptiembre);
        model.addAttribute("ordenesOctubre", ordenesOctubre);
        model.addAttribute("ordenesNoviembre", ordenesNoviembre);
        model.addAttribute("ordenesDiciembre", ordenesDiciembre);


        model.addAttribute("ordenesCreado", ordenesCreado);
        model.addAttribute("ordenesEnValidacion", ordenesEnValidacion);
        model.addAttribute("ordenesEnProceso", ordenesEnProceso);
        model.addAttribute("ordenesArriboAlPais", ordenesArriboAlPais);
        model.addAttribute("ordenesEnAduanas", ordenesEnAduanas);
        model.addAttribute("ordenesEnRuta", ordenesEnRuta);
        model.addAttribute("ordenesRecibido", ordenesRecibido);
        model.addAttribute("proveedoresBaneados", proveedoresBaneados);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("usuariosActivos", usuariosActivos);
        model.addAttribute("usuariosInactivos", usuariosInactivos);
        model.addAttribute("usuariosBaneados", usuariosBaneados);
        model.addAttribute("usuariosAgentes", usuariosAgentes);
        return "SuperAdmin/dashboard_superadmin";
    }


    //Proveedores

    @GetMapping({"/proveedor/lista", "/gestion_proveedores"})
    public String listarProveedores(Model model) {
        List<Proveedor> listaProveedores = proveedorRepository.findAll();
        //model.addAttribute("lista", proveedorRepository.findAll());
        model.addAttribute("listaProveedores", listaProveedores);
        model.addAttribute("listaZona", zonaRepository.findAll());

        return "SuperAdmin/gestion_proveedores";
    }

    @GetMapping("/proveedor/nuevo")
    public String nuevoProveedor(Model model){
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("listaZona", zonaRepository.findAll());

        return "SuperAdmin/proveedor_registrar";
    }

    @GetMapping("/proveedor/editar")
    public String editarProveedor(Model model, @RequestParam("id") int id) {

        Optional<Proveedor> optProovedor = proveedorRepository.findById(id);

        if (optProovedor.isPresent()) {
            Proveedor proveedor = optProovedor.get();

            model.addAttribute("proveedor", proveedor);
            //model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
            return "SuperAdmin/proveedor_editar";
        } else {
            return "redirect:/superadmin/proveedor/lista";
        }
    }
    @PostMapping("/proveedor/guardar")
    public String guardarProveedor(Proveedor proveedor, RedirectAttributes attr) {
        proveedorRepository.save(proveedor);
        return "redirect:/superadmin/proveedor/lista";
    }
    @GetMapping("/proveedor/borrar")
    public String borrarProveedor(Model model,
                                  @RequestParam("id") int id,
                                  RedirectAttributes attr) {

        Optional<Proveedor> optProduct = proveedorRepository.findById(id);


        if (optProduct.isPresent()) {
            proveedorRepository.deleteById(id);
        }
        return "redirect:/superadmin/proveedor/lista";

    }

    @GetMapping("/proveedor/buscar")
    public String buscarProveedor(@RequestParam("searchField") String searchField,
                                 Model model) {

        List<Proveedor> listaProveedor = proveedorRepository.findByNombreProveedorContaining(searchField);
        System.out.println("Resultados encontrados: " + listaProveedor.size());
        model.addAttribute("listaProveedores", listaProveedor);
        return "Superadmin/gestion_proveedores";
    }


    @GetMapping("/proveedor/filtrar")
    public String filtrarPorZona(@RequestParam(value = "zona", required = false) Integer idzona, Model model) {
        List<Proveedor> listaProveedores;

        if (idzona == null || idzona == 0) {
            // Si no se selecciona ninguna zona, retorna todos los proveedores
            listaProveedores = proveedorRepository.findAll();
        } else {
            // Si se selecciona una zona, realiza la búsqueda filtrando por el ID de la zona
            listaProveedores = proveedorRepository.findByZona_Idzona(idzona);
        }

        model.addAttribute("listaProveedores", listaProveedores);
        model.addAttribute("listaZona", zonaRepository.findAll());  // Cargar zonas nuevamente para el select
        model.addAttribute("zonaSeleccionada", idzona);  // Esto asegura que el valor seleccionado se manteng
        return "Superadmin/gestion_proveedores";
    }
}
