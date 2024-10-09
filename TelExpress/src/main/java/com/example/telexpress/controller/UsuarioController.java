package com.example.telexpress.controller;
import com.example.telexpress.dto.ProductoDTO;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.telexpress.repository.UsuarioRepository;
import com.example.telexpress.repository.ZonaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ReseniaRepository reseniaRepository;
    private final OrdenesRepository ordenesRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, ProductoRepository productoRepository,
                             ReseniaRepository reseniaRepository, OrdenesRepository ordenesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.reseniaRepository = reseniaRepository;
        this.ordenesRepository = ordenesRepository;
    }

    @GetMapping({"","/inicio"})
    public String index(){
        return "Usuariofinal/inicio_usuariofinal";
    }

    @GetMapping("/inicio_usuariofinal")
    public String inicio_usuariofinal(){
        return "Usuariofinal/inicio_usuariofinal";
    }

    @GetMapping("/chat")
    public String chat(){
        return "Usuariofinal/chat";
    }

    @GetMapping("/detalle_producto")
    public String detalle_producto(Model model, @RequestParam("id")  Integer id){
        // Obtener el producto por ID
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Obtener el promedio de puntuación de todas las reseñas del producto
        Double promedioPuntuacion = reseniaRepository.obtenerPromedioPuntuacion(id);
        // Si no hay reseñas, establecer el promedio como 0
        if (promedioPuntuacion == null) {
            promedioPuntuacion = 0.0;
        }
        // Contar las reseñas relacionadas con el producto específico
        Long cantidad_resenias = reseniaRepository.countByProductoIdProducto(id);
        // Añadir el producto al modelo
        model.addAttribute("producto", producto);
        model.addAttribute("promedioPuntuacion", promedioPuntuacion);
        model.addAttribute("cantidadResenias", cantidad_resenias);

        return "Usuariofinal/detalle_producto";
    }

    @GetMapping("/agregarCarrito")
    @ResponseBody
    public String agregarProductoAlCarrito(@RequestParam("productoId") Integer productoId, @RequestParam("usuarioId") Integer usuarioId) {
        // Obtener el usuario
        Usuario usuario = usuarioRepository.findById(Long.valueOf(usuarioId)).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Buscar la orden pendiente del usuario
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenesUser(usuario, "Pendiente");
        Ordenes ordenPendiente;

        if (ordenPendienteOpt.isPresent()) {
            // Si existe una orden pendiente, la utilizamos
            ordenPendiente = ordenPendienteOpt.get();
        } else {
            // Si no existe una orden pendiente, creamos una nueva
            ordenPendiente = new Ordenes();
            ordenPendiente.setUsuario(usuario);
            ordenPendiente.setEstadoOrdenesUser("Pendiente");
            ordenesRepository.save(ordenPendiente);
        }

        // Obtener el producto
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Agregar el producto a la orden
        ordenPendiente.agregarProducto(producto);
        ordenesRepository.save(ordenPendiente);

       // return "redirect:/usuario/ordenes_pendientes";
        return "Producto agregado al carrito";
    }

    @GetMapping("/productos_carrito")
    @ResponseBody
    public List<ProductoDTO> verOrdenesPendientes(@RequestParam("usuarioId") Integer usuarioId, Model model) {
        // Obtener el usuario
        Usuario usuario = usuarioRepository.findById(Long.valueOf(usuarioId))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Obtener la orden pendiente del usuario
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenesUser(usuario, "Pendiente");

        if (ordenPendienteOpt.isPresent()) {
            // Pasar los productos al modelo para mostrarlos en el modal
            Ordenes ordenPendiente = ordenPendienteOpt.get();
            // Convertir los productos a ProductoDTO
            List<ProductoDTO> productosDTO = ordenPendiente.getProductos().stream()
                    .map(producto -> new ProductoDTO(
                            producto.getIdProducto(),
                            producto.getNombreProducto(),
                            producto.getPrecio(),
                            producto.getCantidadDisponible()
                    ))
                    .collect(Collectors.toList());

            return productosDTO; // Retorna la lista de DTOs
        } else {
            //model.addAttribute("productos", new ArrayList<>()); // No hay productos en el carrito
            return new ArrayList<>(); // Si no hay productos
        }
        //return "Usuariofinal/ordenes_pendientes";
    }

    @PostMapping("/agregarCarrito")
    @ResponseBody
    public String agregarProductoAlCarrito(@RequestBody Map<String, Integer> data) {
        Integer productoId = data.get("productoId");
        Integer usuarioId = data.get("usuarioId");

        // Obtener el usuario
        Usuario usuario = usuarioRepository.findById(Long.valueOf(usuarioId))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Buscar la orden pendiente del usuario
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenesUser(usuario, "Pendiente");
        Ordenes ordenPendiente;

        if (ordenPendienteOpt.isPresent()) {
            // Si existe una orden pendiente, la utilizamos
            ordenPendiente = ordenPendienteOpt.get();
        } else {
            // Si no existe una orden pendiente, creamos una nueva
            ordenPendiente = new Ordenes();
            ordenPendiente.setUsuario(usuario);
            ordenPendiente.setEstadoOrdenesUser("Pendiente");
            ordenesRepository.save(ordenPendiente);
        }

        // Obtener el producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Agregar el producto a la orden
        ordenPendiente.agregarProducto(producto);
        ordenesRepository.save(ordenPendiente);

        return "Producto agregado al carrito";
    }


    /*@Transactional
    @PostMapping("/agregar_al_carrito")
    public String agregarAlCarrito(@RequestParam("idProducto") Integer idProducto, @RequestParam("cantidad") Integer cantidad, Model model) {
        // Obtener el usuario actual (simulación, debes implementar autenticación)
        Usuario usuarioActual = usuarioRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificar si hay una orden pendiente
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioIdusuarioAndEstadoOrdenesUser(usuarioActual.getId(), "pendiente");

        Ordenes ordenPendiente;
        if (ordenPendienteOpt.isPresent()) {
            ordenPendiente = ordenPendienteOpt.get();
        } else {
            // Si no hay una orden pendiente, crear una nueva
            ordenPendiente = new Ordenes();
            ordenPendiente.setUsuario(usuarioActual);
            ordenPendiente.setEstadoOrdenesUser("pendiente");
            ordenesRepository.save(ordenPendiente);
        }

        // Agregar el producto a la orden
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        ordenPendiente.agregarProducto(producto, cantidad);
        ordenesRepository.save(ordenPendiente);

        return "redirect:/usuario/ordenes_pendientes";
    }*/

    /*@GetMapping("/ordenes_pendientes")
    public String verOrdenesPendientes(Model model) {
        // Obtener el usuario actual
        Usuario usuarioActual = usuarioRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Obtener la orden pendiente del usuario
        Optional<Ordenes> ordenPendiente = ordenesRepository.findByUsuarioIdusuarioAndEstadoOrdenesUser(usuarioActual.getIdusuario(), "pendiente");

        if (ordenPendiente.isPresent()) {
            model.addAttribute("productosOrden", ordenPendiente.get().getProductos());
        } else {
            model.addAttribute("productosOrden", List.of()); // Vacío si no hay productos
        }

        return "Usuariofinal/ordenes_pendientes";
    }*/

    @GetMapping("/editar_orden")
    public String editar_orden(){
        return "Usuariofinal/editar_orden";
    }

    @GetMapping("/enviar_resena")
    public String enviar_resena(){
        return "Usuariofinal/enviar_resena";
    }

    @GetMapping("/FAQS")
    public String FAQS(){
        return "Usuariofinal/FAQS";
    }

    @GetMapping("/Foro")
    public String Foro(){
        return "Usuariofinal/Foro";
    }

    @GetMapping("/lista_pedidos")
    public String lista_pedidos(@RequestParam(value = "search", required = false) String search, Model model){


        List<Ordenes> ordenes = ordenesRepository.findByUsuarioId(4);

        // Si hay un término de búsqueda, buscar por cliente o estado de la orden
        if (search != null && !search.isEmpty()) {
            // Realiza la búsqueda en el repositorio por nombre de cliente o estado
            ordenes = ordenesRepository.findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCaseOrEstadoOrdenesContainingIgnoreCase(search, search, search);
        } else {
            // Si no hay búsqueda, obtener todas las órdenes
            ordenes = ordenesRepository.findAll();
        }

        // Pasar las órdenes al modelo para la vista
        model.addAttribute("ordenes", ordenes);

        // Pasar el término de búsqueda al modelo para que se mantenga en el campo de búsqueda
        model.addAttribute("search", search);
        return "Usuariofinal/lista_pedidos";
    }

    @GetMapping("/lista_productos")
    public String lista_productos(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "9") int size,
                                  @RequestParam(value = "filtroStock", required = false) String filtroStock){
        // Definir el paginador
        Pageable pageable = PageRequest.of(page, size);
        // Obtener los productos paginados desde el servicio
        Page<Producto> pagproductos = productoRepository.findAll(pageable);

        // Filtrar productos según el valor del filtro de stock
        if ("agotado".equals(filtroStock)) {
            // Si se selecciona "agotado", mostrar solo productos sin stock
            pagproductos = productoRepository.findByCantidadDisponible(0, pageable);
        } else {
            // Si no se selecciona "agotado", mostrar productos con stock
            pagproductos = productoRepository.findByCantidadDisponibleGreaterThan(0, pageable);
        }

        // Pasar los productos y la información de paginación al modelo
        model.addAttribute("productos", pagproductos.getContent());
        model.addAttribute("totalPages", pagproductos.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filtroStock", filtroStock);

        return "Usuariofinal/lista_productos";
    }

    @GetMapping("/pagar")
    public String pagar(){
        return "Usuariofinal/pagar";
    }

    @GetMapping("/pago")
    public String pago(){
        return "Usuariofinal/pago";
    }

    @GetMapping("/resenia")
    public String resenia(){
        return "Usuariofinal/resenia";
    }

    @GetMapping("/respuesta_resenia")
    public String respuesta_resenia(){
        return "Usuariofinal/respuesta_resenia";
    }

    @GetMapping("/unete")
    public String unete(){
        return "Usuariofinal/unete";
    }

    @GetMapping("/editar_perfil")
    public String editar_perfil(){
        return "Usuariofinal/editar_perfil";
    }

    @GetMapping("/cambiar_contrasena")
    public String cambiar_contrasena(){
        return "Usuariofinal/cambiar_contrasena";
    }
}



