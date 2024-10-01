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
@RequestMapping("/coordinador")
public class CoordinadorController {
    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;


    public CoordinadorController(AdminRepository adminRepository, ZonaRepository zonaRepository,
                                ProductoRepository productoRepository, UsuarioRepository usuarioRepository,
                                ProveedorRepository proveedorRepository, OrdenesRepository ordenesRepository
    ) {
        this.adminRepository=adminRepository; this.zonaRepository=zonaRepository;
        this.productoRepository=productoRepository; this.usuarioRepository=usuarioRepository;
        this.proveedorRepository=proveedorRepository; this.ordenesRepository=ordenesRepository;

    }

    @GetMapping({"/inicio_coordinador_zonal",""})
    public String inicioCoordinadorZonal() {


        return "CoordinadorZonal/inicio_coordinador_zonal";
    }

    @GetMapping("/crearagente_zonal")
    public String crearAgenteCoordinadorZonal() {


        return "CoordinadorZonal/crearagente_zonal";
    }


    @GetMapping({"/dashboard_zonal"})
    public String dashboardCoordinadorZonal() {


        return "CoordinadorZonal/dashboard_zonal";
    }

    @GetMapping({"/dashboard2_zonal"})
    public String dashboard2CoordinadorZonal() {


        return "CoordinadorZonal/dashboard2_zonal";
    }


    @GetMapping({"/importacion_zonal"})
    public String importacionCoordinadorZonal() {


        return "CoordinadorZonal/importacion_zonal";
    }

    @GetMapping({"/listaagente_zonal"})
    public String listaAgenteCoordinadorZonal() {


        return "CoordinadorZonal/listaagente_zonal";
    }

    @GetMapping({"/perfilagente_zonal"})
    public String perfilAgenteCoordinadorZonal() {


        return "CoordinadorZonal/perfilagente_zonal";
    }

    @GetMapping({"/productos_zonal"})
    public String productosCoordinadorZonal() {


        return "CoordinadorZonal/productos_zonal";
    }



}
