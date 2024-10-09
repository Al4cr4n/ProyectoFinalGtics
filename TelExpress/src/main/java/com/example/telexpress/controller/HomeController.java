package com.example.telexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){

        return "Sistema/Login";
    }

    @GetMapping("cambio_contraseña")
    public String cambioContrasena(){


        return "Sistema/cambio_contraseña";
    }

    @GetMapping("/registro_usuario")
    public String registroUsuario() {
        return "Sistema/registro_usuario";
    }
}
