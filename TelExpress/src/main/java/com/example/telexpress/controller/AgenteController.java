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
@RequestMapping("/agente")
public class AgenteController {

    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;

    public AgenteController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;
    }





    @GetMapping("/inicio_agente")
    public String inicioAgente() {


        return "Agente/inicio_agente";
    }

    @GetMapping("/cambio_contra_agente")
    public String cambioContraAgente() {


        return "Agente/cambio_contra_agente";
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
    public String perfilAgente() {


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
