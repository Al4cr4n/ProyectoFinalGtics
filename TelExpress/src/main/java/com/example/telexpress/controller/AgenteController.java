package com.example.telexpress.controller;

import com.example.telexpress.entity.*;
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
@RequestMapping("/agente")
public class AgenteController {

    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;
    final ContrasenaAgenteRespository contrasenaAgenteRespository;
    final UsuarioPerfilRepository usuarioPerfilRepository;

    public AgenteController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                            ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                            ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository, ContrasenaAgenteRespository contrasenaAgenteRespository, UsuarioPerfilRepository usuarioPerfilRepository) {

        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
        this.contrasenaAgenteRespository=contrasenaAgenteRespository;
        this.usuarioPerfilRepository = usuarioPerfilRepository;

    }





    @GetMapping("/inicio_agente")
    public String inicioAgente() {


        return "Agente/inicio_agente";
    }

    @GetMapping("/cambio_contra_agente")
    public String cambioContraAgente( Model model) {
        int id =3;
        String passw = contrasenaAgenteRespository.findcontrasena(id);
        model.addAttribute("passw", passw);
        return "Agente/cambio_contra_agente";
    }
    @PostMapping("/cambio_contra_agente")
    public String ActualizarContraAgente(
            @RequestParam("password") String currentPassword,  // Contraseña actual
            @RequestParam("new-password-again") String newPassword, // Nueva contraseña
            @RequestParam("new-password") String confirmNewPassword, // Confirmación de la nueva contraseña
            Model model) {

        int id = 3; // ID del agente

        // Obtener la contraseña almacenada en la base de datos
        String storedPassword = contrasenaAgenteRespository.findcontrasena(id);

        // Verificar que la contraseña actual sea correcta
        if (!currentPassword.equals(storedPassword)) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "Agente/cambio_contra_agente"; // Retornar a la vista con mensaje de error
        }

        // Verificar que la nueva contraseña y su confirmación coincidan
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            return "Agente/cambio_contra_agente"; // Retornar a la vista con mensaje de error
        }

        // Si la nueva contraseña es igual a la contraseña actual, prevenir el cambio
        if (currentPassword.equals(newPassword)) {
            model.addAttribute("error", "La nueva contraseña no puede ser igual a la contraseña actual.");
            return "Agente/cambio_contra_agente"; // Retornar a la vista con mensaje de error
        }

        // Actualizar la contraseña en la base de datos
        contrasenaAgenteRespository.updatecontrasena(id, newPassword);
        System.out.println(newPassword);
        // Redireccionar al perfil del agente con mensaje de éxito
        model.addAttribute("success", "Contraseña cambiada exitosamente.");
        return "Agente/inicio_agente"; // Redirigir al perfil
    }


    @GetMapping("/chat_agente")
    public String chatAgente() {


        return "Agente/chat_agente";
    }

    @GetMapping("/ordenes_agente")
    public String ordenesAgente() {


        return "Agente/ordenes_agente";
    }

    @GetMapping("/ordenes_en_progreso")
    public String ordenesEnProgresoAgente() {


        return "Agente/ordenes_en_progreso";
    }

    @GetMapping("/ordenes_pendientes")
    public String ordenesPendientesAgente() {


        return "Agente/ordenes_pendientes";
    }

    @GetMapping("/ordenes_resueltas")
    public String ordenesResueltasAgente() {


        return "Agente/ordenes_resueltas";
    }

    @GetMapping("/ordenes_sin_asignar")
    public String ordenesSinAsignarAgente() {


        return "Agente/ordenes_sin_asignar";
    }

    @GetMapping("/perfil_agente")
    public String perfilAgente(Model model) {
        int id =3;
        String nombre;
        nombre = usuarioPerfilRepository.findnombre(id);
        model.addAttribute("nombre", nombre);

        String apellido = usuarioPerfilRepository.findapellido(id);
        model.addAttribute("apellido", apellido);

        String telefono = usuarioPerfilRepository.findtelefono(id);
        model.addAttribute("telefono", telefono);

        String correo = usuarioPerfilRepository.findcorreo(id);
        model.addAttribute("correo", correo);

        String codigoDespachador = usuarioPerfilRepository.findcodigoDespachador(id);
        model.addAttribute("codigoDespachador", codigoDespachador);



        return "Agente/perfil_agente";
    }

    @PostMapping("/perfil_agente")
    public String ActualizarPerfil( @RequestParam("id") int id){

        usuarioPerfilRepository.updatenombre(id);
        usuarioPerfilRepository.updateapellido(id);
        return "Agente/perfil_agente";
    }

    @GetMapping("/usuarios_agente")
    public String usuariosAgente() {


        return "Agente/usuarios_agente";
    }

    @GetMapping("/usuarios_baneados")
    public String usuariosBaneados() {


        return "Agente/usuarios_baneados";
    }

    
}
