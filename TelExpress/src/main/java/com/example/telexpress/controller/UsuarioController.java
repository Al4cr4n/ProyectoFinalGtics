package com.example.telexpress.controller;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.AdminRepository;
import com.example.telexpress.repository.UsuarioRepository;
import com.example.telexpress.repository.ZonaRepository;
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
public class UsuarioController {


    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

}

