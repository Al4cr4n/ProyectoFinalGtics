package com.example.telexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperAdminController {



    @GetMapping("")
    public String index(){

        return "SuperAdmin/inicio_superadmin";
    }

    @GetMapping("inicio_superadmin")
    public String inicioSuperadmin(){

        return "SuperAdmin/inicio_superadmin";
    }

    @GetMapping("/dashboard_superadmin")
    public String dashboardSuperadmin() {


        return "SuperAdmin/dashboard_superadmin";
    }

    @GetMapping("/inventario_superadmin")
    public String inventarioSuperadmin() {


        return "SuperAdmin/inventario_superadmin";
    }

    @GetMapping("/inventario_registrar_producto")
    public String inventarioRegistrarSuperadmin() {


        return "SuperAdmin/inventario_registrar_producto";
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



}
