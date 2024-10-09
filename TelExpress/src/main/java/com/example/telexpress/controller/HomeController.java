package com.example.telexpress.controller;
import com.example.telexpress.entity.*;
import com.example.telexpress.config.EmailService;
import com.example.telexpress.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.List;

@Controller
public class HomeController {
    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final EmailService emailService;
    final DistritoRepository distritoRepository;


    public HomeController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                 ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                 ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository,
                                 CoordinadorRepository coordinadorRepository, EmailService emailService,
                                 DistritoRepository distritoRepository) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.emailService=emailService;
        this.distritoRepository=distritoRepository;
    }
    @GetMapping("/login")
    public String login(){

        return "Sistema/Login";
    }

    @GetMapping("cambio_contraseña")
    public String cambioContrasena(){


        return "Sistema/cambio_contraseña";
    }

    @GetMapping("/registro_usuario")
    public String registroUsuario(Model model) {
        // Crear un nuevo objeto Usuario y añadirlo al modelo
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);

        // Crear lista de distritos (puede venir de una base de datos o servicio)
        List<Distrito> lista_distritos = distritoRepository.findAll();
        model.addAttribute("lista_distritos", lista_distritos);

        return "Sistema/registro_usuario";
    }

}
