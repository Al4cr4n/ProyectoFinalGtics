package com.example.telexpress.controller;
import com.example.telexpress.config.EmailService;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController {
    final AdminRepository adminRepository;
    final ZonaRepository zonaRepository;
    final ProductoRepository productoRepository;
    final UsuarioRepository usuarioRepository;
    final ProveedorRepository proveedorRepository;
    final OrdenesRepository ordenesRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final EmailService emailService;
    final DistritoRepository distritoRepository;


    public CoordinadorController(AdminRepository adminRepository, ZonaRepository zonaRepository,
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

    @GetMapping({"/inicio_coordinador_zonal",""})
    public String inicioCoordinadorZonal() {


        return "CoordinadorZonal/inicio_coordinador_zonal";
    }

    @GetMapping("/crearagente_zonal")
    public String crearAgenteCoordinadorZonal(Model model) {
        // Buscar la entidad Zona Norte (idzona = 1)
        Zona zonaNorte = zonaRepository.findById(1).orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        // Obtener los distritos pertenecientes a la Zona Norte
        List<Distrito> distritosNorte = distritoRepository.findByZona(zonaNorte);
        model.addAttribute("distritosNorte", distritosNorte);

        model.addAttribute("agente", new Usuario()); // Inicializa un objeto Usuario para el formulario
        return "CoordinadorZonal/crearagente_zonal"; // Muestra la vista del formulario
    }

    @PostMapping("/crearagente_zonal")
    public String guardarAgente(@ModelAttribute("agente") Usuario agenteNuevo, RedirectAttributes attr) {

        if (agenteNuevo.getId() == null) { // Verificar si es un nuevo agente
            // Si el agente tiene un proveedor asociado, se guarda el proveedor
            if (agenteNuevo.getProveedor() != null && agenteNuevo.getProveedor().getNombreTienda() != null) {
                proveedorRepository.save(agenteNuevo.getProveedor());
            }

            // Asignar la zona "Norte" por defecto, ya que no la modificas en el formulario
            Zona zonaNorte = new Zona();
            zonaNorte.setIdzona(1); // Aquí debes usar el ID correspondiente para la zona Norte en la base de datos
            agenteNuevo.setZona(zonaNorte);

            // Guardar el nuevo agente
            adminRepository.save(agenteNuevo);
            attr.addFlashAttribute("successMessage", "Agente creado exitosamente.");
        }

        return "redirect:/coordinador/listaagente_zonal"; // Redireccionar a la lista de agentes después de guardar
    }


    @GetMapping({"/dashboard_zonal"})
    public String dashboardCoordinadorZonal(Model model) {

        List<Producto> listaTop = productoRepository.findAll();
        listaTop.sort(Comparator.comparing(Producto::getCantidadComprada).reversed());
        model.addAttribute("listaTop", listaTop);


        if (listaTop.size() > 10) {
            listaTop = listaTop.subList(0, 10);
        }

        List<String> colores = Arrays.asList(
                "#f1948a",
                "#6c757d",
                "#67c9c2",
                "#f5a9d0",
                "#ffc107",
                "#17a2b8",
                "#343a40",
                "#C39BD3",
                "#7FB3D5",
                "#76D7C4"
        );
        model.addAttribute("colores", colores);




        return "CoordinadorZonal/dashboard_zonal";
    }

    @GetMapping({"/dashboard2_zonal"})
    public String dashboard2CoordinadorZonal(Model model) {
        /*List<Usuario> listaTopUsuarios = usuarioRepository.findByZona("zona");
        listaTopUsuarios.sort(Comparator.comparing(Usuario::getCantidadcompras).reversed());
        System.out.println(listaTopUsuarios);


        model.addAttribute("listaTopUsuarios",listaTopUsuarios);

        if (listaTopUsuarios.size() > 10) {
            listaTopUsuarios = listaTopUsuarios.subList(0, 10);
        }

        List<String> colores = Arrays.asList(
                "#f1948a",
                "#6c757d",
                "#67c9c2",
                "#f5a9d0",
                "#ffc107",
                "#17a2b8",
                "#343a40",
                "#C39BD3",
                "#7FB3D5",
                "#76D7C4"
        );
        model.addAttribute("colores", colores);

        System.out.println();*/

        return "CoordinadorZonal/dashboard2_zonal";
    }



    @GetMapping({"/importacion_zonal"})
    public String importacionCoordinadorZonal() {


        return "CoordinadorZonal/importacion_zonal";
    }

    @GetMapping({"/listaagente_zonal"})
    public String listaAgenteCoordinadorZonal(Model model) {
        List<Usuario> lista_agentes_zonal = coordinadorRepository.buscarAgentePorRolYZona("Norte"); //solo para agente norte, pero puede ser por request param para otras zonas
        model.addAttribute("lista_agentes_zonal",lista_agentes_zonal);

        return "CoordinadorZonal/listaagente_zonal";
    }

    @GetMapping({"/perfilagente_zonal"})
    public String perfilAgenteCoordinadorZonal(Model model, @RequestParam("id") Integer id) {
        Optional<Usuario> optionalUsuario = adminRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario agente = optionalUsuario.get();
            model.addAttribute("agente", agente);
            return "CoordinadorZonal/perfilagente_zonal";
        } else {
            return "CoordinadorZonal/listaagente_zonal";
        }
    }

    @GetMapping("/productos_zonal")
    public String productosCoordinadorZonal(Model model) {
        List<Producto> producto = productoRepository.findAll();
        producto.sort(Comparator.comparing(Producto::getCantidadTotal).reversed());
        model.addAttribute("producto", producto);
        return "CoordinadorZonal/productos_zonal";
    }

    @PostMapping("/solicitarReponer")
    public String solicitarReposicion(@RequestParam("nombreProducto") String nombreProducto,
                                      @RequestParam("categoria") String categoria,
                                      @RequestParam("email") String email) {
        // Llamar al servicio de correo
        emailService.enviarEmailSolicitud(email, nombreProducto, categoria);

        // Redirigir a la vista de productos con un mensaje de éxito
        return "redirect:/productos_zonal?solicitudExitosa";
    }




}
