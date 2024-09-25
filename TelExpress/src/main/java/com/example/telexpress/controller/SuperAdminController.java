package com.example.telexpress.controller;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.AdminRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    final AdminRepository adminRepository;
    public AdminRepository(AdminRepository adminRepository){
        this.adminRepository=adminRepository;
    }



    @GetMapping("")
    public String index(){

        return "SuperAdmin/inicio_superadmin";
    }

    /*METODO DE TODAS LAS LISTAS DE LA VISTA PRINCIPAL*/

    @GetMapping("inicio_superadmin")
    public String inicioSuperadmin(Model model){
        model.addAttribute("listaUsuario",adminRepository.buscarUsuarioPorRol());
        model.addAttribute("listaAgente",adminRepository.buscarAgentePorRol());
        model.addAttribute("listaCoordinador",adminRepository.buscarCoordiPorRol());
        return "SuperAdmin/inicio_superadmin";
    }

    /*USUARIO EDITAR Y BORRAR*/

    @GetMapping("/editar")
    public String editarUsuario(Model model, @RequestParam("id") int id){
        Optional<Usuario> optUsuario = adminRepository.findById(id);
        if (optUsuario.isPresent()){
            Usuario usuario = optUsuario.get();
            model.addAttribute("usuario", usuario);

            return "SuperAdmin/editarUsuario";
        }else{
            return "redirect:/superadmin";
        }
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






    @GetMapping("/dashboard_superadmin")
    public String dashboardSuperadmin() {


        return "SuperAdmin/dashboard_superadmin";
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
    public String gestionCoordinadoresSuperadmin() {


        return "SuperAdmin/gestion_coordinadores";
    }

    @GetMapping("/coordinador_zonal_formulario")
    public String CoordinadorZonalFormularioSuperadmin() {


        return "SuperAdmin/coordinador_zonal_formulario";
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
    public String gestionAgentesSuperadmin() {


        return "SuperAdmin/gestion_agentes";
    }
    @GetMapping("/rol_agente_solicitudes")
    public String rolAgenteSolicitudesSuperadmin() {


        return "SuperAdmin/rol_agente_solicitudes";
    }

    @GetMapping("/solicitud_agente")
    public String solicitudAgenteSuperadmin() {


        return "SuperAdmin/solicitud_agente";
    }



}
