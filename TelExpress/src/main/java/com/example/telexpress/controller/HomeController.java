package com.example.telexpress.controller;

import com.example.telexpress.entity.*;
import com.example.telexpress.config.EmailService;
import com.example.telexpress.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;
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
        this.adminRepository = adminRepository;
        this.zonaRepository = zonaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRepository = proveedorRepository;
        this.ordenesRepository = ordenesRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.emailService = emailService;
        this.distritoRepository = distritoRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "Sistema/Login";
    }
    @GetMapping("/")
    public String redirigiALogin(){
        return "redirect:/login";
    }

    @GetMapping("cambio_contraseña")
    public String cambioContrasena() {
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

    @PostMapping("/registro_usuario")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        // Asignar el rol de usuario (ID de rol = 1)
        Rol rolUsuario = new Rol();
        rolUsuario.setId(4); // Asigna el ID 1 al rol de usuario
        usuario.setRol(rolUsuario);


        // Asignar la zona correspondiente según el distrito
        Distrito distrito = distritoRepository.findById(usuario.getDistrito().getId())
                .orElseThrow(() -> new RuntimeException("Distrito no encontrado"));

        String nombreDistrito = distrito.getNombredistrito();
        Zona zonaAsignada = determinarZonaPorDistrito(nombreDistrito);

        if (zonaAsignada == null) {
            throw new RuntimeException("No se pudo asignar una zona para el distrito seleccionado.");
        }

        usuario.setZona(zonaAsignada);

        // Hashear la contraseña antes de guardarla
        String hashedPassword = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(hashedPassword);


        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        // Añadir un mensaje de éxito en los atributos de redirección
        redirectAttributes.addFlashAttribute("successMessage", "Usuario registrado exitosamente.");

        // Redirigir al formulario de login o alguna otra página
        return "redirect:/login";
    }

    // Método para asignar la zona basada en el distrito
    private Zona determinarZonaPorDistrito(String nombreDistrito) {
        Zona zona = new Zona();

        // Asignación manual de zonas según el distrito
        if (Arrays.asList("Ancon", "Santa Rosa", "Carabayllo", "Puente Piedra", "Comas", "Los Olivos", "San Martín de Porres", "Independencia").contains(nombreDistrito)) {
            zona.setNombre("Norte");
        } else if (Arrays.asList("San Juan de Miraflores", "Villa María del Triunfo", "Villa el Salvador", "Pachacamac", "Lurín", "Punta Hermosa", "Punta Negra", "San Bartolo", "Santa María del Mar", "Pucusana").contains(nombreDistrito)) {
            zona.setNombre("Sur");
        } else if (Arrays.asList("San Juan de Lurigancho", "Lurigancho/Chosica", "Ate", "El Agustino", "Santa Anita", "La Molina", "Cieneguilla").contains(nombreDistrito)) {
            zona.setNombre("Este");
        } else if (Arrays.asList("Rimac", "Cercado de Lima", "Breña", "Pueblo Libre", "Magdalena", "Jesus María", "La Victoria", "Lince", "San Isidro", "San Miguel", "Surquillo", "San Borja", "Santiago de Surco", "Barranco", "Chorrillos", "San Luis", "Miraflores").contains(nombreDistrito)) {
            zona.setNombre("Oeste");
        } else {
            return null; // Si no coincide con ninguna zona, se retorna null
        }

        // Buscamos la zona en la base de datos y la retornamos
        return zonaRepository.findByNombre(zona.getNombre())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada en la base de datos"));
    }
}
