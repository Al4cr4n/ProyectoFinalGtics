package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.AdminRepository;
import com.example.telexpress.repository.ZonaRepository;
import com.example.telexpress.repository.UsuarioRepository;
import com.example.telexpress.repository.ProductoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    public SuperAdminController(AdminRepository adminRepository, ZonaRepository zonaRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
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
            return "redirect:/superadmin";
        }
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Usuario usuario, RedirectAttributes attr) {
        adminRepository.save(usuario);
        return "redirect:/superadmin";
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
    @GetMapping("/coordinador_zonal_formulario")
    public String CoordinadorZonalFormularioSuperadmin(Model model, @RequestParam("id") Integer id) {
        Optional<Usuario> coordinador = adminRepository.findById(id);
        if (coordinador.isPresent()) {
            Usuario user = coordinador.get();
            model.addAttribute("coordinadorzonal", user);
            List<Zona> zonas = zonaRepository.findAll();
            model.addAttribute("zonas",zonas);

            return "SuperAdmin/coordinador_zonal_formulario";
        } else {
            return "redirect:SuperAdmin/gestion_coordinadores";
        }

    }
/*para gestionar coordinador zonal*/
    @GetMapping("/new")
    public String crearnuevocoordi(Model model){
        List<Zona> zonas = zonaRepository.findAll();
        model.addAttribute("zonas", zonas);
        model.addAttribute("coordinadorzonal", new Usuario());
        return "SuperAdmin/coordinador_zonal_formulario";
    }

    /* para actualizar datos de coodinador zonal*/
    @PostMapping("/update")
    public String actualizardatoscoordi(Usuario usuario, @RequestParam("zonaid") Integer zonaid) {

        Zona zona = zonaRepository.findById(zonaid).orElse(null);
        if (zona != null) {
            usuario.setZona(zona); // Asignar la zona seleccionada al usuario
        }
        adminRepository.save(usuario);
        return "redirect:SuperAdmin/gestion_coordinadores";
    }

    /*para gestionar coordinador zonal*/
    @GetMapping("/deletecoordi")
    public String eliminarcoordi(Model model, @RequestParam("id") Integer id){
        Optional <Usuario> coordi = adminRepository.findById(id);
        if (coordi.isPresent()){
            adminRepository.deleteById(id);
        }
        return "redirect:SuperAdmin/gestion_coordinadores";
    }

    @GetMapping("/gestion_usuarios")
    public String gestionUsuariosSuperadmin() {


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
    public String editar_agentes(Model model) {

        return "SuperAdmin/editar_agente";
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

        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("usuariosActivos", usuariosActivos);
        model.addAttribute("usuariosInactivos", usuariosInactivos);
        model.addAttribute("usuariosBaneados", usuariosBaneados);
        return "SuperAdmin/dashboard_superadmin";
    }




}
