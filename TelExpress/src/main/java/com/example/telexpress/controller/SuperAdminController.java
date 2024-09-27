package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoUsuario;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.entity.Proveedor;
import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Rol;
import com.example.telexpress.repository.*;

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

    public SuperAdminController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
    }



    @GetMapping("")
    public String index(){

        return "SuperAdmin/inicio_superadmin";
    }

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

    /*USUARIO EDITAR, GUARDAR Y BORRAR*/

    @GetMapping("/editar")
    public String editarUsuario(Model model, @RequestParam("id") int id){

        Optional<Usuario> optUsuario = adminRepository.findById(id);
        if (optUsuario.isPresent()){
            Usuario usuario = optUsuario.get();
            model.addAttribute("usuarios", usuario);

            return "SuperAdmin/editar_usuario";
        }else{
            return "redirect:/superadmin/gestion_usuarios";
        }
    }

    @PostMapping("/guardar_usuario")
    public String guardarUsuario(Usuario usuario, RedirectAttributes attr) {
        adminRepository.save(usuario);
        return "redirect:/superadmin/gestion_usuarios";
    }



    @GetMapping("/eliminar")
    public String borrarUsuario(Model model,
                                @RequestParam("id") int id,
                                RedirectAttributes attr) {

        Optional<Usuario> optUsuario = adminRepository.findById(id);

        if (optUsuario.isPresent()) {
            adminRepository.deleteById(id);
        }
        return "redirect:/superadmin";

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
    public String actualizardatoscoordi(Usuario usuario, @RequestParam("zona.idzona") Integer zonaid) {

        // Asignar el rol de Coordinador (idroles = 2)
        Rol rolCoordinador = new Rol();
        rolCoordinador.setId(2); // Asegúrate de que el método setId exista en la clase Rol

        usuario.setRol(rolCoordinador); // Asignar el rol al usuario

        Zona zona = zonaRepository.findById(zonaid).orElse(null);
        if (zona != null) {
            usuario.setZona(zona); // Asignar la zona seleccionada al usuario
        }
        adminRepository.save(usuario);
        return "redirect:/superadmin/gestion_coordinadores";
    }

    /*para gestionar coordinador zonal*/
    @GetMapping("/deletecoordi")
    public String eliminarcoordi(Model model, @RequestParam("id") Integer id){
        Optional <Usuario> coordi = adminRepository.findById(id);
        if (coordi.isPresent()){
            adminRepository.deleteById(id);
        }else {
            System.out.println("No se encontró el coordinador con ID: " + id);
        }
        return "redirect:/superadmin/gestion_coordinadores";
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
    public String actualizar_agentes(Usuario agente, RedirectAttributes attr) {
        adminRepository.save(agente);
        return "redirect:/superadmin/gestion_agentes";
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
        // Devuelve la vista correspondiente


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
