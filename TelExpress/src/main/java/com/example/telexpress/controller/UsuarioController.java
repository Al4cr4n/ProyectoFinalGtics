package com.example.telexpress.controller;
import com.example.telexpress.config.EmailService;
import com.example.telexpress.dao.ServicioCompraDAO;
import com.example.telexpress.dto.DatosCompra;
import com.example.telexpress.dto.PostDTO;
import com.example.telexpress.dto.ProductoDTO;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import com.example.telexpress.service.ChatRoomService;
import com.example.telexpress.service.PdfService;
import com.example.telexpress.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.telexpress.repository.DistritoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.telexpress.repository.UsuarioRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ReseniaRepository reseniaRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final ProductoOrdenesRepository productoOrdenesRepository;
    private final ContrasenaAgenteRespository contrasenaAgenteRespository;
    private final PasswordEncoder passwordEncoder;
    private final ChatRoomService chatRoomService;
    private final PagosRepository pagosRepository;
    private final PostService postService;
    private final ServicioCompraDAO servicioCompraDAO;
    private final View error;
    private final CommentRepository commentRepository;


    public UsuarioController(UsuarioRepository usuarioRepository, PagosRepository pagosRepository, ProductoRepository productoRepository, ReseniaRepository reseniaRepository,
                             OrdenesRepository ordenesRepository, DistritoRepository distritoRepository, CoordinadorRepository coordinadorRepository,
                             ProductoOrdenesRepository productoOrdenesRepository, ContrasenaAgenteRespository contrasenaAgenteRespository, PasswordEncoder passwordEncoder, ChatRoomService chatRoomService,
                             PostService postService, ServicioCompraDAO servicioCompraDAO, View error, CommentRepository commentRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.reseniaRepository = reseniaRepository;
        this.ordenesRepository = ordenesRepository;
        this.distritoRepository = distritoRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.productoOrdenesRepository = productoOrdenesRepository;
        this.contrasenaAgenteRespository = contrasenaAgenteRespository;
        this.passwordEncoder = passwordEncoder;
        this.chatRoomService = chatRoomService;
        this.pagosRepository = pagosRepository;
        this.postService = postService;
        this.servicioCompraDAO = servicioCompraDAO;
        this.error = error;
        this.commentRepository = commentRepository;
    }

    private final OrdenesRepository ordenesRepository;
    private final DistritoRepository distritoRepository;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CategoriasRepository categoriasRepository;
/*
    @GetMapping({"","/inicio"})
    public String index(Model model){
        model.addAttribute("activePage", "inicio");

        return "Usuariofinal/inicio_usuariofinal";
    } */

    @GetMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("id") Long id, HttpSession session) {
        // Usamos el repositorio directamente para buscar al usuario por ID
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        // Configuramos la sesión del usuario
        session.setAttribute("usuario", usuario);

        // Redirigimos a la página de inicio del usuario final
        return "redirect:/usuario/inicio_usuariofinal";
    }
    @GetMapping("/inicio_usuariofinal")
    public String inicio_usuariofinal(Model model){

        model.addAttribute("activePage", "inicio");
        return "Usuariofinal/inicio_usuariofinal";
    }


    @GetMapping("/detalle_producto")
    public String detalle_producto(Model model, @RequestParam("id")  Integer id){
        model.addAttribute("activePage", "detalle_producto");

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
        //Usuario usuario = usuarioRepository.findById(Long.valueOf(usuarioId)).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        // Buscar la orden pendiente del usuario
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuario, "Pendiente");
        Ordenes ordenPendiente;

        if (ordenPendienteOpt.isPresent()) {
            // Si existe una orden pendiente, la utilizamos
            ordenPendiente = ordenPendienteOpt.get();
        } else {
            // Si no existe una orden pendiente, creamos una nueva
            ordenPendiente = new Ordenes();
            ordenPendiente.setUsuario(usuario);
            ordenPendiente.setEstadoOrdenes("Pendiente");
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
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuario, "Pendiente");

        if (ordenPendienteOpt.isPresent()) {
            // Pasar los productos al modelo para mostrarlos en el modal
            //Ordenes ordenPendiente = ordenPendienteOpt.get();
            // Obtener los productos de la tabla intermedia con su cantidad
            List<ProductoOrdenes> productosHasOrdenes = productoOrdenesRepository.findByOrdenesIdOrdenes(ordenPendienteOpt.get().getIdOrdenes());

            // Convertir los productos a ProductoDTO
            List<ProductoDTO> productosDTO = productosHasOrdenes.stream()
                    .map(p -> new ProductoDTO(
                            p.getProducto().getIdProducto(),
                            p.getProducto().getNombreProducto(),
                            p.getProducto().getPrecio(),
                            p.getProducto().getCantidadDisponible(),
                            p.getCantidadxproducto() // cantidad de este producto en la orden
                    ))
                    .collect(Collectors.toList());
            System.out.println("Producto ID: " + productosDTO);

            return productosDTO; // Retorna la lista de DTOs
        } else {
            //model.addAttribute("productos", new ArrayList<>()); // No hay productos en el carrito
            return new ArrayList<>(); // Si no hay productos
        }
        //return "Usuariofinal/ordenes_pendientes";
    }

    @PostMapping("/agregarCarrito")
    @ResponseBody
    public Map<String, Object> agregarAlCarrito(@RequestBody Map<String, Object> datos, Model model ) {
        // Obtener el usuario actual (simulación, debes implementar autenticación)
        //Usuario usuarioActual = usuarioRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        //.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Integer productoId = Integer.parseInt((String)  datos.get("productoId"));
        Integer cantidad = Integer.parseInt((String)  datos.get("cantidadxproducto"));
        Integer usuarioId = Integer.parseInt((String)  datos.get("usuarioId"));

        //verificación de si el producto y la cantidad son válidos
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        //crear o encontrar una orden pendiente para el usuario
        Ordenes ordenPendiente = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuarioActual, "Pendiente")
                .orElseGet(() -> {
                   Ordenes ordenPendientenew = new Ordenes();
                   ordenPendientenew.setEstadoOrdenes("Pendiente");
                   ordenPendientenew.setUsuario(usuarioActual);
                    //nuevaOrden.setFechaCreacion(new Date());
                    //nuevaOrden.setMesCreacion(new SimpleDateFormat("MM").format(new Date()));
                    return ordenesRepository.save(ordenPendientenew);
                });
        // Asegurarse de que el ID de la orden está generado antes de proceder
       /* if (ordenPendiente.getIdOrdenes() == null) {
            ordenPendiente = ordenesRepository.save(ordenPendiente); // Esto asegura que la orden tiene un ID generado
        }*/

        // Crear la clave compuesta
        ProductoOrdenesId productoOrdenesId = new ProductoOrdenesId(producto.getIdProducto(), ordenPendiente.getIdOrdenes());

        // Crear o actualizar el producto en la orden
        ProductoOrdenes productosHasOrdenes = productoOrdenesRepository.findById(productoOrdenesId)
                .orElseGet(() -> {
                    ProductoOrdenes nuevoProductoOrdenes = new ProductoOrdenes();
                    nuevoProductoOrdenes.setId(productoOrdenesId);
                    nuevoProductoOrdenes.setProducto(producto);
                    nuevoProductoOrdenes.setOrdenes(ordenPendiente);
                    nuevoProductoOrdenes.setCantidadxproducto(cantidad);
                    return nuevoProductoOrdenes;
                });

        //si el producto ya existe en la orden, actualiza la cantidad
        if (productosHasOrdenes.getId() != null){
            productosHasOrdenes.setCantidadxproducto(cantidad);
        }
        //ahora se procede a guardar los cambios del producto en la orden
        productoOrdenesRepository.save(productosHasOrdenes);
        //ahora se obtiene la lista de todos los productos de la ordenpendiente(carrito)
        List<ProductoOrdenes> productosEnOrden = productoOrdenesRepository.findByIdOrdenesId(ordenPendiente.getIdOrdenes());
        //se crea la lista de productos que se enviará al frontend
        List<Map<String, Object>> listaProductos = new ArrayList<>();
        double precioTotalOrden = 0.0;

        for (ProductoOrdenes po : productosEnOrden){
            Map<String, Object> productoInfo = new HashMap<>();
            productoInfo.put("nombreProducto", po.getProducto().getNombreProducto());
            productoInfo.put("codigoProducto", po.getProducto().getIdProducto());
            productoInfo.put("cantidad", po.getCantidadxproducto());
            productoInfo.put("precio", po.getProducto().getPrecio());
            productoInfo.put("precioTotal", po.getProducto().getPrecio() * po.getCantidadxproducto());

            precioTotalOrden += po.getProducto().getPrecio() * po.getCantidadxproducto();

            listaProductos.add(productoInfo);
        }

        //devolvemos los datos necesarios para el modal
        Map<String, Object> respuesta =new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("productos", listaProductos);
        respuesta.put("subtotal", precioTotalOrden);
        respuesta.put("ordenId",ordenPendiente.getIdOrdenes());
        return respuesta;
    }

    @PostMapping("/eliminarDelCarrito")
    @ResponseBody
    public Map<String, Object> eliminarDelCarrito(@RequestBody List<Integer> productosIds,
                                                  Model model) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        //ya se realizó la actualización, ahora se procede a obtener la orden con estado pendiente del usuario
        Ordenes ordenPendiente = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuarioActual,"Pendiente")
                .orElseThrow(() -> new IllegalArgumentException("Orden pendiente no encontrada"));
        //ahora se procede a eliminar el producto de la orden
        for(Integer productoId : productosIds){
            ProductoOrdenesId productoOrdenesId = new ProductoOrdenesId(productoId, ordenPendiente.getIdOrdenes());
            productoOrdenesRepository.deleteById(productoOrdenesId);
        }
        //ahora se procede a actualizar la lista de productos de la orden Pendiente(carrito)
        List<ProductoOrdenes> productosEnLaOrden = productoOrdenesRepository.findByIdOrdenesId(ordenPendiente.getIdOrdenes());
        List<Map<String, Object>> listaDeProductos = new ArrayList<>();
        double precioTotalOrden = 0.0;
        for (ProductoOrdenes po : productosEnLaOrden){
            Map<String, Object> productoInfo = new HashMap<>();
            productoInfo.put("nombreProducto", po.getProducto().getNombreProducto());
            productoInfo.put("codigoProducto", po.getProducto().getIdProducto());
            productoInfo.put("cantidad", po.getCantidadxproducto());
            productoInfo.put("precio", po.getProducto().getPrecio());
            productoInfo.put("precioTotal", po.getProducto().getPrecio() * po.getCantidadxproducto());
            precioTotalOrden += po.getProducto().getPrecio() * po.getCantidadxproducto();

            listaDeProductos.add(productoInfo);
        }
        //lo que se devuelve a la vista
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("productos", listaDeProductos);
        respuesta.put("subtotal", precioTotalOrden);
        return respuesta;
    }

    @GetMapping("/editar_orden")
    public String editar_orden(){
        return "Usuariofinal/editar_orden";
    }

    @GetMapping("/enviar_resena")
    public String enviar_resena(){
        return "Usuariofinal/enviar_resena";
    }

    @GetMapping("/FAQS")
    public String FAQS(Model model){
        model.addAttribute("activePage", "faqs");


        return "Usuariofinal/FAQS";
    }

    @GetMapping("/Foro")
    public String Foro(Model model,  HttpSession session) {

        List<Post> posts =postService.getAllPosts();
        List<PostDTO> postDTOList= new ArrayList<>();
        posts.forEach(

                post -> {
                    PostDTO postDTO = new PostDTO();
                    List<Comment> comments =  commentRepository.findByPostId(post.getId().intValue());
                    postDTO.setPost(post);
                    postDTO.setList(comments);
                    postDTOList.add(postDTO);
                }

        );
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuario.getId();
        model.addAttribute("id", idUsuario);
        model.addAttribute("activePage", "foro");
        model.addAttribute("posts", postDTOList);
        return "Usuariofinal/Foro";
    }

    @GetMapping("/lista_pedidos")
    public String lista_pedidos(@RequestParam(value = "estado", required = false) List<String> estados,
                                @RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("activePage", "lista_pedidos");
        //Obtener el usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByCorreo(correo);
        // Actualizar el agente encargado solo para las órdenes en los estados 'CREADO', 'EN VALIDACION', 'EN PROCESO'
        ordenesRepository.updateAgenteEncargadoByUsuario(usuarioAutenticado.getId());


        List<Ordenes> ordenes;
        //List<Usuario> usuarios = usuarioRepository.findAll();
        List<String> estadosTodos = Arrays.asList("EN PROCESO", "ARRIBO AL PAÍS", "EN ADUANAS", "EN RUTA", "RECIBIDO", "EN VALIDACIÓN", "CREADO");
        ordenes = ordenesRepository.findOrdenesByUsuarioAAndEstadoOrdenes(usuarioAutenticado.getId(), estadosTodos);

        // Verificar y eliminar elementos nulos si existen
        if (ordenes != null) {
            ordenes.removeIf(Objects::isNull);
        }

        if (estados != null && !estados.isEmpty()) {
            // Obtener las órdenes por estado
            ordenes = ordenesRepository.findByEstadoOrdenesIn(estados);
        } else {
            // Si no hay estados seleccionados, obtener todas las órdenes
            ordenes = ordenesRepository.findOrdenesByUsuarioAAndEstadoOrdenes(usuarioAutenticado.getId(), estadosTodos);

        }

        // Obtener el agente asignado a cada usuario a través del idsuperior
        Map<Integer, String> agentesMap = new HashMap<>();
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Ordenes orden : ordenes) {
            System.out.println("Orden ID: " + (orden != null ? orden.getIdOrdenes() : "null"));
            if (orden.getAgentexorden() != null) {
                // Obtener el agente directamente del idSuperior
                //Usuario agente = usuario.getIdSuperior();
                Usuario agente = usuarioRepository.findById(Long.valueOf(orden.getAgentexorden()))
                        .orElse(null);
                if (agente != null) {
                    agentesMap.put(orden.getIdOrdenes(), agente.getNombre());
                } else {
                    agentesMap.put(orden.getIdOrdenes(), "Sin Asignar");
                }
            } else {
                // Si no tiene un agente asignado, muestra "Sin Asignar"
                agentesMap.put(orden.getIdOrdenes(), "Sin Asignar");
            }
        }

        // Pasar las órdenes, usuarios y agentes al modelo para la vista
        model.addAttribute("ordenes", ordenes);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("agentesMap", agentesMap);

        return "Usuariofinal/lista_pedidos";
    }

    @GetMapping("/pedidos/detallesRecibido")
    public String verPedidoRecibido(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("activePage", "lista_pedidos");

        Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(id);

        if (optionalOrdenes.isPresent()) {
            Ordenes ordenes = optionalOrdenes.get();
            // Obtener los productos asociados a esta orden usando la tabla intermedia
            List<ProductoOrdenes> productosOrdenes = productoOrdenesRepository.findByIdOrdenesId(id);
            // Cargar los productos y sus detalles
            final double[] subtotal = {0.0};
            List<Producto> productos = new ArrayList<>();

            for (ProductoOrdenes po : productosOrdenes) {
                Optional<Producto> producto = productoRepository.findById(po.getProducto().getIdProducto());
                producto.ifPresent(p -> {
                    p.setCantidadComprada(po.getCantidadxproducto());
                    productos.add(p);
                    subtotal[0] += p.getPrecio() * po.getCantidadxproducto(); /// Agregar la cantidad de la tabla intermedia

                });
            }
            double deliveryCost = 15.00; // Suponiendo un costo de envío fijo
            double totalGeneral = subtotal[0] + deliveryCost;

            model.addAttribute("orden", ordenes);
            // Añadir la lista de productos al modelo
            model.addAttribute("productos", productos);
            model.addAttribute("subtotal", subtotal[0]);
            model.addAttribute("deliveryCost", deliveryCost);
            model.addAttribute("totalGeneral", totalGeneral);

            return "Usuariofinal/ver_detalle_orden";
        } else {
            return "redirect:/usuario/lista_pedidos";
        }
    }



    @GetMapping("/pedidos/editar")
    public String editarPedidos(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("activePage", "lista_pedidos");

        Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(id);

        if (optionalOrdenes.isPresent()) {
            Ordenes ordenes = optionalOrdenes.get();
            // Obtener los productos asociados a esta orden usando la tabla intermedia
            List<ProductoOrdenes> productosOrdenes = productoOrdenesRepository.findByIdOrdenesId(id);
            // Cargar los productos y sus detalles
            final double[] subtotal = {0.0};
            List<Producto> productos = new ArrayList<>();

            for (ProductoOrdenes po : productosOrdenes) {
                Optional<Producto> producto = productoRepository.findById(po.getProducto().getIdProducto());
                producto.ifPresent(p -> {
                    p.setCantidadComprada(po.getCantidadxproducto());
                    productos.add(p);
                    subtotal[0] += p.getPrecio() * po.getCantidadxproducto(); /// Agregar la cantidad de la tabla intermedia

                });
            }
            double deliveryCost = 15.00; // Suponiendo un costo de envío fijo
            double totalGeneral = subtotal[0] + deliveryCost;

            model.addAttribute("orden", ordenes);
            // Añadir la lista de productos al modelo
            model.addAttribute("productos", productos);
            model.addAttribute("subtotal", subtotal[0]);
            model.addAttribute("deliveryCost", deliveryCost);
            model.addAttribute("totalGeneral", totalGeneral);

            return "Usuariofinal/editar_orden";
        } else {
            return "redirect:/usuario/lista_pedidos";
        }
    }
    @PostMapping("/pedidos/guardar")
    public String guardarPedidos(@RequestParam("id") Integer idOrden, RedirectAttributes attr, HttpServletRequest request, Model model) {
        Optional<Ordenes> optionalOrden = ordenesRepository.findById(idOrden);

        if (optionalOrden.isPresent()) {
            Ordenes orden = optionalOrden.get();

            // Obtiene la lista de productos asociados a la orden desde la tabla intermedia
            List<ProductoOrdenes> productosOrdenes = productoOrdenesRepository.findByIdOrdenesId(idOrden);
            double subtotalAnterior = productosOrdenes.stream()
                    .mapToDouble(po -> po.getProducto().getPrecio() * po.getCantidadxproducto())
                    .sum();
            int cantidadProductosAnterior = productosOrdenes.stream()
                    .mapToInt(ProductoOrdenes::getCantidadxproducto).sum();

            double subtotalNuevo = 0.00;
            int cantidadProductosNuevo = 0;
            // Recorre los productos de la orden
        for (ProductoOrdenes po : productosOrdenes) {
            // Para cada producto, obtiene el valor de la cantidad enviada por el formulario
            String cantidadParam = request.getParameter("cantidadProducto_" + po.getProducto().getIdProducto());

            if (cantidadParam != null) {
                // Actualiza la cantidad en la tabla intermedia
                Integer nuevaCantidad = Integer.parseInt(cantidadParam);
                po.setCantidadxproducto(nuevaCantidad);
                // Guarda la actualización en la base de datos
                productoOrdenesRepository.save(po);

                subtotalNuevo += po.getProducto().getPrecio() * nuevaCantidad;
                cantidadProductosNuevo += nuevaCantidad;
            }
        }
            double deliveryCost = 15.00;
            double totalNuevo = subtotalNuevo + deliveryCost;
            // Comparar cantidad de productos
            if (cantidadProductosNuevo > cantidadProductosAnterior) {
                attr.addFlashAttribute("mensaje", "Se agregaron más productos. Verifique el monto adicional.");
            } else if (cantidadProductosNuevo < cantidadProductosAnterior) {
                attr.addFlashAttribute("mensaje", "Se eliminaron productos. El monto será ajustado.");
            }
            // Comparar montos
            if (totalNuevo > subtotalAnterior + deliveryCost) {
                // Generar un nuevo pago pendiente si el monto aumentó
                Pagos nuevoPago = new Pagos();
                nuevoPago.setOrdenes(orden);
                nuevoPago.setMonto(totalNuevo - (subtotalAnterior + deliveryCost));
                //nuevoPago.setFechaPago(new Date());
                nuevoPago.setEstadoPago("Incompleto");
                nuevoPago.setMetodoPago(null); // Método nulo hasta pagar
                pagosRepository.save(nuevoPago);

                attr.addFlashAttribute("mensajeMonto", "Se ha generado un nuevo pago pendiente por la diferencia de monto.");
            } else if (totalNuevo < subtotalAnterior + deliveryCost) {
                attr.addFlashAttribute("mensajeMonto", "El monto es menor. Pronto recibirá el reembolso correspondiente.");
            }

            return "redirect:/usuario/pedidos/editar?id=" + idOrden;
        } else {
            //ordenesRepository.save(ordenes);
            return "redirect:/usuario/lista_pedidos";
        }
    }
    @GetMapping("/pedidos/borrar")
    public String borrarPedidos(Model model,
                                @RequestParam("id") int id,
                                RedirectAttributes attr) {
        model.addAttribute("activePage", "lista_pedidos");

        Optional<Ordenes> optProduct = ordenesRepository.findById(id);


        if (optProduct.isPresent()) {
            //si la orden está en 'CREADO' o 'EN VALIDACIÓN'
            if (optProduct.get().getEstadoOrdenes().equals("CREADO") || optProduct.get().getEstadoOrdenes().equals("EN VALIDACIÓN")) {
                ordenesRepository.delete(optProduct.get());
                attr.addFlashAttribute("success", "Orden eliminada exitosamente.");
            } else {
                // Si el estado no permite eliminar
                attr.addFlashAttribute("error", "No se puede eliminar la orden en el estado actual.");
            }
        }
        return "redirect:/usuario/lista_pedidos";

    }

    @GetMapping("/lista_productos")
    public String lista_productos(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", defaultValue = "9") Integer size,
                                  @RequestParam(value = "filtroStock", required = false) String filtroStock,
                                  @RequestParam(value = "filtroCategoria", required = false) String filtroCategoria,
                                  @RequestParam(value="search", required = false) String search){
        model.addAttribute("activePage", "lista_productos");

        // Definir el paginador
        Pageable pageable = PageRequest.of(page, size);
        // Obtener los productos paginados desde el servicio
        Page<Producto> pagproductos = productoRepository.findAll(pageable);
        if(search!= null && !search.isEmpty()){
          pagproductos = productoRepository.findByNombreProductoContainingOrDescripcionContaining(search, search, pageable);
        }
        // Filtrar por categoría
        else if (filtroCategoria != null && !filtroCategoria.isEmpty()) {
            pagproductos = productoRepository.findByCategorias_Nombre(filtroCategoria, pageable);
        }
        // Filtrar productos según el valor del filtro de stock
        else if ("agotado".equals(filtroStock)) {
            // Si se selecciona "agotado", mostrar solo productos sin stock
            pagproductos = productoRepository.findByCantidadDisponible(0, pageable);
        } else {
            // Si no se selecciona "agotado", mostrar productos con stock
            pagproductos = productoRepository.findByCantidadDisponibleGreaterThan(0, pageable);
        }
        List<Categorias> categoriasList = categoriasRepository.findAll();
        if (pagproductos.isEmpty()) {
            model.addAttribute("totalPages", 0);
        } else {
            model.addAttribute("totalPages", pagproductos.getTotalPages());
        }


        model.addAttribute("categorias", categoriasList);

        // Pasar los productos y la información de paginación al modelo
        model.addAttribute("productos", pagproductos.getContent());
        //model.addAttribute("totalPages", pagproductos.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filtroStock", filtroStock);
        model.addAttribute("filtroCategoria", filtroCategoria);

        return "Usuariofinal/lista_productos";
    }
    @PostMapping("/agregar/orden/carrito")
    @ResponseBody
    public Map<String, Object> agregarProductoCarrito(@RequestBody Map<String,Object >map){

        // Obtener el usuario autenticado
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        // Obtener el ID del producto
        Integer productoId = Integer.parseInt((String) map.get("productoId"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        // Encontrar o crear una orden pendiente
        Ordenes ordenPendiente = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuarioActual, "Pendiente")
                .orElseGet(() -> {
                    Ordenes nuevaOrden = new Ordenes();
                    nuevaOrden.setEstadoOrdenes("Pendiente");
                    nuevaOrden.setUsuario(usuarioActual);
                    return ordenesRepository.save(nuevaOrden);
                });
        // Crear la clave compuesta
        ProductoOrdenesId productoOrdenesId = new ProductoOrdenesId(producto.getIdProducto(), ordenPendiente.getIdOrdenes());
        // Crear o actualizar el producto en la orden
        ProductoOrdenes productosHasOrdenes = productoOrdenesRepository.findById(productoOrdenesId)
                .orElseGet(() -> {
                    ProductoOrdenes nuevoProductoOrdenes = new ProductoOrdenes();
                    nuevoProductoOrdenes.setId(productoOrdenesId);
                    nuevoProductoOrdenes.setProducto(producto);
                    nuevoProductoOrdenes.setOrdenes(ordenPendiente);
                    nuevoProductoOrdenes.setCantidadxproducto(1); // Cantidad por defecto
                    return nuevoProductoOrdenes;
                });
        //si el producto ya existe en la orden, actualiza la cantidad
        if (productosHasOrdenes.getId() != null){
            productosHasOrdenes.setCantidadxproducto(productosHasOrdenes.getCantidadxproducto() + 1);
        }
        // Guardar el producto en la orden
        productoOrdenesRepository.save(productosHasOrdenes);
        // Respuesta JSON
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("message", "Producto agregado al carrito con éxito");

        return respuesta;
    }

    @GetMapping("/ver_carrito")
    public String verCarritohtml(Model model) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuario, "Pendiente");

        if (ordenPendienteOpt.isPresent()) {
            model.addAttribute("ordenId", ordenPendienteOpt.get().getIdOrdenes());
        } else {
            model.addAttribute("ordenId", null);
        }
        /*String correo=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuariodesesion =usuarioRepository.findByCorreo(correo);
        //se obtiene los productos de la orden "pendiente"
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuariodesesion, "Pendiente");
        if (ordenPendienteOpt.isPresent()) {
            List<ProductoOrdenes> productosdeOrden = productoOrdenesRepository.findByIdOrdenesId(ordenPendienteOpt.get().getIdOrdenes());
            List<ProductoDTO> productospendientesDTO = productosdeOrden.stream()
                    .map(p -> new ProductoDTO(
                            p.getProducto().getIdProducto(),
                            p.getProducto().getNombreProducto(),
                            p.getProducto().getPrecio(),
                            p.getProducto().getCantidadDisponible(),
                            p.getCantidadxproducto()
                    ))
                    .collect(Collectors.toList());
            model.addAttribute("productos", productospendientesDTO);
        } else {
            model.addAttribute("productos", new ArrayList<>()); // No hay productos
        }*/


        Producto producto1 = productoRepository.findProductoConIdMayor();
        Producto producto2 = productoRepository.findProductoConSegundoIdMayor();
        Producto producto3 = productoRepository.findProductoConTercerIdMayor();

        model.addAttribute("producto1", producto1);
        model.addAttribute("producto2", producto2);
        model.addAttribute("producto3", producto3);


        return "Usuariofinal/ver_carrito"; // Ruta a la plantilla HTML
    }


    @GetMapping("/productos-carrito-compras")
    @ResponseBody
    @Transactional
    public Map<String, Object> verCarrito() {

        String correo=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuariodesesion =usuarioRepository.findByCorreo(correo);
        //se obtiene los productos de la orden "pendiente"
        Optional<Ordenes> ordenPendienteOpt = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuariodesesion, "Pendiente");
        Map<String, Object> respuesta = new HashMap<>();

        if (ordenPendienteOpt.isPresent()) {
            List<ProductoOrdenes> productosDeOrden = productoOrdenesRepository.findByIdOrdenesId(ordenPendienteOpt.get().getIdOrdenes());

            double subtotal = productosDeOrden.stream()
                    .mapToDouble(p -> p.getProducto().getPrecio() * p.getCantidadxproducto())
                    .sum();
            int totalArticulos = productosDeOrden.stream()
                    .mapToInt(ProductoOrdenes::getCantidadxproducto)
                    .sum();
            List<Map<String, Object>> productos =  productosDeOrden.stream()
                    .map(p -> {
                        Map<String, Object> productoMap = new HashMap<>();
                        productoMap.put("idProducto", p.getProducto().getIdProducto());
                        productoMap.put("nombreProducto", p.getProducto().getNombreProducto());
                        productoMap.put("precio", p.getProducto().getPrecio());
                        productoMap.put("cantidadxproducto", p.getCantidadxproducto());

                        // Calcular el estado del stock en tiempo real
                        String estadoStock;
                        if (p.getProducto().getCantidadDisponible() == 0) {
                            estadoStock = "Sin Stock";
                        } else if (p.getProducto().getCantidadDisponible() < 10) {
                            estadoStock = "Por Agotarse";
                        } else {
                            estadoStock = "Stock";
                        }
                        productoMap.put("estadoStock", estadoStock);
                        productoMap.put("cantidadDisponible", p.getProducto().getCantidadDisponible());
                        return productoMap;
                    })
                    .collect(Collectors.toList());
            double costoEnvio = productosDeOrden.isEmpty() ? 0.00 : 15.00;
            respuesta.put("productos", productos);
            respuesta.put("subtotal", subtotal);
            respuesta.put("deliveryCost", costoEnvio);
            respuesta.put("total", subtotal + costoEnvio);
            respuesta.put("totalArticulos", totalArticulos);
        } else {
            respuesta.put("productos", Collections.emptyList());
            respuesta.put("totalArticulos", 0);
            respuesta.put("deliveryCost", 0.00);
            respuesta.put("total", 0.00);
        }
        return respuesta;
    }

    @PostMapping("/actualizar-cantidad")
    @ResponseBody
    public ResponseEntity<String> actualizarCantidad(
            @RequestParam("idProducto") Integer idProducto,
            @RequestParam("nuevaCantidad") Integer nuevaCantidad) {

        Optional<ProductoOrdenes> productoOrdenOpt = productoOrdenesRepository.findByProductoIdProductoAndOrdenesEstadoOrdenes(
                idProducto, "Pendiente");

        if (productoOrdenOpt.isPresent()) {
            ProductoOrdenes productoOrden = productoOrdenOpt.get();
            productoOrden.setCantidadxproducto(nuevaCantidad); // Actualizar la cantidad
            productoOrdenesRepository.save(productoOrden); // Guardar cambios

            return ResponseEntity.ok("Cantidad actualizada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado en la orden");
        }
    }
    @PostMapping("/eliminar-productos-carrito")
    @ResponseBody
    public ResponseEntity<String> eliminarProductosCarrito(@RequestBody Map<String, List<Integer>> request){
        try {
            List<Integer> ids = request.get("ids");
            // Validación: Verificar que la lista no esté vacía
            if (ids == null || ids.isEmpty()) {
                System.out.println("la lista de products esta totalmente vacia");
                return ResponseEntity.badRequest().body("La lista de productos a eliminar está vacía.");
            }

            // Obtener el usuario actual
            String correo = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
            //elimando los productos de la orden pendiente
            Ordenes ordenPendiente = ordenesRepository.findByUsuarioAndEstadoOrdenes(usuarioActual, "Pendiente")
                    .orElseThrow(() -> new IllegalArgumentException("No hay orden pendiente para el usuario"));
            ids.forEach(id -> {
                ProductoOrdenesId productoOrdenesId = new ProductoOrdenesId(id, ordenPendiente.getIdOrdenes());
                productoOrdenesRepository.deleteById(productoOrdenesId);
            });
            return ResponseEntity.ok("Productos eliminados correctamente.");
        }
        catch (Exception e) {
            e.printStackTrace(); // Log para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar productos.");
        }
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent() && producto.get().getImage() != null) {
            byte[] image = producto.get().getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG based on your image type
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*redireccion a la pagina para pagar la orden*/
    @GetMapping("/orden/pagar")
    public String verDetalleOrdenaPagar(Model model , @RequestParam Integer ordenId, RedirectAttributes attr) {
        model.addAttribute("activePage", "orden_pagar");
        //Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(id);
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        // Recuperar la zona del usuario
        Zona zonaUsuario = usuarioActual.getZona();


        Ordenes orden =ordenesRepository.findById(ordenId).orElseThrow(()-> new IllegalArgumentException("Orden no encontrada"));
        //Ordenes orden = ordenesRepository.findByIdOrdenes(ordenId).orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        List<ProductoOrdenes> productosEnOrden = productoOrdenesRepository.findByIdOrdenesId(ordenId);
        /*Double precioSubtotalenOrden = productosEnOrden.stream()
                .mapToDouble(po -> po.getProducto().getPrecio()*po.getCantidadxproducto())
                .sum();*/
        // Cargar los productos y sus detalles
        final double[] subtotal = {0.0};
        List<Producto> productos = new ArrayList<>();
        for (ProductoOrdenes po : productosEnOrden) {
            Optional<Producto> producto = productoRepository.findById(po.getProducto().getIdProducto());
            producto.ifPresent(p -> {
                p.setCantidadComprada(po.getCantidadxproducto());
                productos.add(p);
                subtotal[0] += p.getPrecio() * po.getCantidadxproducto(); // Agregar la cantidad de la tabla intermedia

            });
        }
        double deliveryCost = 15.00; // Suponiendo un costo de envío fijo
        double totalGeneral = subtotal[0] + deliveryCost;

        // Añadir la lista de productos al modelo
        model.addAttribute("orden", orden);
        model.addAttribute("productosDeOrden", productos);
        model.addAttribute("precioSubtotal", subtotal[0]);
        model.addAttribute("deliveryCost", deliveryCost);
        model.addAttribute("totalGeneral", totalGeneral);

        model.addAttribute("zona", zonaUsuario.getNombre());
        model.addAttribute("direccion", usuarioActual.getDireccion());
        model.addAttribute("correo", usuarioActual.getCorreo());
        return "Usuariofinal/pagar";
    }

    @PostMapping("/solicitudpago/tarjeta")
    @ResponseBody
    public ResponseEntity<?> procesoPagoTarjata(@Valid @RequestBody  DatosCompra requestCompra, BindingResult result, Model model){
        System.out.println("CVV recibido: " + requestCompra.getCodigoCvv());
        System.out.println("Datos recibidos: " + requestCompra);
        System.out.println("Número de tarjeta recibido: '" + requestCompra.getNumeroTarjeta() + "'");
        System.out.println("fecha de expiracion ingresada:" + requestCompra.getFechaExpiracion());
        System.out.println("correo ingresado: "+requestCompra.getCorreo());
        if (result.hasErrors()) {
            // Crear un mapa para devolver los errores
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errores); // Devuelve los errores como JSON
        }
        try {

            String response = servicioCompraDAO.realizarCompra(requestCompra);
            Map<String, String> success = new HashMap<>();
            success.put("message", response);
            return ResponseEntity.ok(success);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Fecha de expiración incorrecta")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("fechaExpiracion", ex.getMessage()));
            }
            if (ex.getMessage().contains("expirada")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("tarjetaExpirada", ex.getMessage()));
            } else if (ex.getMessage().contains("Saldo insuficiente")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("saldoInsuficiente", ex.getMessage()));
            } else if (ex.getMessage().contains("codigo de cvv no es valido")){
                return ResponseEntity.badRequest().body(Collections.singletonMap("codigoCvvV", ex.getMessage()));
            }
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", ex.getMessage()));
        }
    }

    @PostMapping("/orden/procesaryenviar")
    public String procesarPago(@ModelAttribute DatosCompra datosCompra, RedirectAttributes redirectAttributes) {
        Ordenes orden = ordenesRepository.findById(datosCompra.getOrdenId())
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        String boucherPath = pdfService.generateBoucher(orden, datosCompra);
        // Enviar el correo con el boucher adjunto
        emailService.sendOrderConfirmation(datosCompra.getCorreo(), boucherPath);
        redirectAttributes.addFlashAttribute("success", "Pago procesado correctamente. Revisa tu correo.");
        return "redirect:/usuario/lista_pedidos";
    }

    @GetMapping("/pago")
    public String pago(Model model) {

        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = usuarioRepository.findByCorreo(correo);
        Zona zona = usuarioActual.getZona();

        List<Ordenes> ordenes = ordenesRepository.findAll();
        model.addAttribute("ordenes", ordenes);

        List<Pagos> pagosPendientes = pagosRepository.findByUsuario_ZonaAndEstadoPago(zona,"Pendiente");
        List<Pagos> pagosRealizados = pagosRepository.findByUsuario_ZonaAndEstadoPago(zona,"Completado");

        model.addAttribute("pagosPendientes", pagosPendientes);
        model.addAttribute("pagosRealizados", pagosRealizados);
        model.addAttribute("activePage", "pago");
        return "Usuariofinal/pago";
    }

    /*
    @GetMapping("/resenia")
    public String resenia(Model model){
        model.addAttribute("activePage", "resenia");

        return "Usuariofinal/resenia";
    }*/
    @GetMapping("/resenia")
    public String mostrarResenias(Model model) {
        model.addAttribute("activePage", "resenia");
        List<Resenia> resenias = reseniaRepository.findAll(); // Cargar todas las reseñas desde la base de datos
        model.addAttribute("resenias", resenias); // Pasar las reseñas al modelo
        return "Usuariofinal/resenia"; // Nombre de tu archivo HTML de Thymeleaf (resenias.html)
    }
    // Método para mostrar todas las reseñas
    @GetMapping("/mostrarResenias")
    public String mostrarResenias(Model model, @RequestParam(value = "search", required = false) String search) {
        model.addAttribute("activePage", "resenia");
        List<Resenia> resenias;

        // Si se recibe un parámetro de búsqueda, buscar por tituloresena
        if (search != null && !search.isEmpty()) {
            resenias = reseniaRepository.findByTituloresenaContainingIgnoreCase(search);
        } else {
            // Si no se recibe búsqueda, cargar todas las reseñas
            resenias = reseniaRepository.findAll();
        }

        // Pasar las reseñas filtradas o todas las reseñas al modelo
        model.addAttribute("resenias", resenias);
        model.addAttribute("search", search); // Mantener el valor de búsqueda en la vista
        return "Usuariofinal/resenia"; // Nombre de tu archivo HTML de Thymeleaf
    }

    @GetMapping("/respuesta_resenia")
    public String respuesta_resenia(Model model){
        model.addAttribute("activePage", "resenia");

        return "Usuariofinal/respuesta_resenia";
    }

    // Método GET: Muestra el formulario para crear reseñas
    @GetMapping("/crear_resenia")
    public String mostrarFormularioResenia(Model model) {
        model.addAttribute("activePage", "resenia");

        // Obtener el usuario autenticado desde el contexto de seguridad
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioRepository.findByCorreo(correo);

        // Verificar si el usuario existe
        if (usuarioLogueado == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el correo: " + correo);
        }

        // Cargar lista de productos
        List<Producto> productos = productoRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("usuario", usuarioLogueado); // Pasar el usuario logueado a la vista

        return "Usuariofinal/crear_resenia"; // Vista Thymeleaf
    }

    @PostMapping("/guardar_resenia")
    public String guardarResenia(@RequestParam("fotoProducto") MultipartFile fotoProducto,
                                 @RequestParam("calidad") String calidad,
                                 @RequestParam("rapido") String rapidez,
                                 @RequestParam("puntuacion") int puntuacion,
                                 @RequestParam("tituloresena") String tituloresena,
                                 @RequestParam("tipoPublicacion") String tipoPublicacion,
                                 @RequestParam("productoId") Integer productoId,
                                 @RequestParam("usuarioId") Integer usuarioId,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Crear y configurar la reseña
            Resenia resenia = new Resenia();
            resenia.setCalidad(calidad);
            resenia.setRapidez(rapidez);
            resenia.setPuntuacion(puntuacion);
            resenia.setTituloresena(tituloresena);
            resenia.setTipoPublicacion(tipoPublicacion);

            // Guardar la foto
            if (!fotoProducto.isEmpty()) {
                byte[] bytes = fotoProducto.getBytes();
                Blob fotoBlob = new SerialBlob(bytes);
                resenia.setFoto(fotoBlob);
            }

            // Asignar Producto y Usuario
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            Usuario usuario = usuarioRepository.findById(usuarioId);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
            }

            resenia.setProducto(producto);
            resenia.setUsuario(usuario);

            // Guardar la reseña
            reseniaRepository.save(resenia);
            redirectAttributes.addFlashAttribute("mensaje", "¡Reseña guardada con éxito!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la reseña: " + e.getMessage());
        }

        // Redirigir al listado de reseñas
        return "redirect:/usuario/mostrarResenias";
    }

    @GetMapping("/unete")
    public String mostrarFormulario(Model model) {
        model.addAttribute("activePage", "unete");
        return "Usuariofinal/unete"; // Retorna la vista del formulario
    }

    @PostMapping("/save")
    public String guardarInformacion(@RequestParam("ruc") String ruc,
                                     @RequestParam("jurisdiccion") String jurisdiccion,
                                     @RequestParam("telefono") String telefono,
                                     @RequestParam("despachador") String despachador,
                                     @RequestParam("razonSocial") String razonSocial,
                                     HttpSession session) {
        // Obtener el usuario autenticado desde la sesión
        Usuario usuarioEnSesion = (Usuario) session.getAttribute("usuario");
        System.out.println("Usuario: " + usuarioEnSesion.getNombre());

        if (usuarioEnSesion != null) {
            // Actualizar los valores del formulario
            usuarioEnSesion.setRuc(ruc);
            usuarioEnSesion.setJurisdiccion(jurisdiccion);
            usuarioEnSesion.setTelefono(telefono);
            usuarioEnSesion.setDespachador(despachador);
            usuarioEnSesion.setRazonSocial(razonSocial);
            usuarioEnSesion.setSolicitud(1);


            // Guardar el usuario actualizado en la base de datos
            usuarioRepository.save(usuarioEnSesion);
        }

        return "redirect:/usuario/inicio_usuariofinal"; // Redirigir al formulario nuevamente
    }

    @GetMapping("/editar_perfil")
    public String editar_perfil(Model model){

        /*int id =4;
        String nombre = usuarioRepository.findnombre(id);
        model.addAttribute("nombre", nombre);

        String apellido = usuarioRepository.findapellido(id);
        model.addAttribute("apellido", apellido);

        String telefono = usuarioRepository.findtelefono(id);
        model.addAttribute("telefono", telefono);

        String correo = usuarioRepository.findcorreo(id);
        model.addAttribute("correo", correo);

        String distrito = usuarioRepository.findistrito(id);
        model.addAttribute("distrito", distrito);

        String direccion = usuarioRepository.finddireccion(id);
        model.addAttribute("direccion", direccion);*/

        return "Usuariofinal/editar_perfil";
    }

    @PostMapping("/editar_perfil")
    public String ActualizarPerfil( @RequestParam("id") int id){



        return "Usuariofinal/editar_perfil";
    }

    @GetMapping("/cambio_contra")
    public String cambioContraAgente(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("id", id);
        // Obtener la contraseña almacenada en la base de datos
        return "Usuariofinal/cambio_contra"; // Retornar la vista con el formulario
    }

    @PostMapping("/cambia_contra")
    public String ActualizarContraAgente(
            @ModelAttribute("id") Integer id,
            @RequestParam("currentPassword") String currentPassword,  // Contraseña actual
            @RequestParam("newPassword") String newPassword, // Nueva contraseña
            @RequestParam("confirmPassword") String confirmNewPassword, // Confirmación de la nueva contraseña
            Model model) {


        System.out.println("ID recibido: " + id);
        // Obtener la contraseña almacenada en la base de datos
        String storedPassword = contrasenaAgenteRespository.findcontrasena(id);
        String hashcurrentPassword = passwordEncoder.encode(currentPassword);
        String hashnewPassword = passwordEncoder.encode(newPassword);
        String hashconfirmNewPassword = passwordEncoder.encode(confirmNewPassword);


        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(currentPassword, storedPassword)) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            System.out.println("1" + hashnewPassword);
            model.addAttribute("id", id);
            return "redirect:/usuario/inicio_usuariofinal"; // Redirigir a tu html
        }

        // Verificar que la nueva contraseña y su confirmación coincidan
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            System.out.println("2" + hashnewPassword);
            return "redirect:/usuario/inicio_usuariofinal"; // Retornar a la vista con mensaje de error
        }

        // Si la nueva contraseña es igual a la contraseña actual, prevenir el cambio
        if (currentPassword.equals(newPassword)) {
            model.addAttribute("error", "La nueva contraseña no puede ser igual a la contraseña actual.");
            System.out.println("3" + hashnewPassword);
            return "redirect:/usuario/inicio_usuariofinal"; // Retornar a la vista con mensaje de error
        }

        // Actualizar la contraseña en la base de datos
        contrasenaAgenteRespository.updatecontrasena(id, hashnewPassword);
        System.out.println("aaaa" +  newPassword);
        // Redireccionar al perfil del agente con mensaje de éxito
        model.addAttribute("success", "Contraseña cambiada exitosamente.");
        return "redirect:/usuario/inicio_usuariofinal"; // Redirigir al perfil
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, @RequestParam("id") Integer id) {

        Optional<Usuario> optUsuario = coordinadorRepository.findById(id);

        if (optUsuario.isPresent()) {
            Usuario user = optUsuario.get();
            model.addAttribute("usuario", user);
            return "Usuariofinal/perfil_usuario";
        } else {
            return "redirect:/usuario";
        }
    }
    @GetMapping("/chat")
    public ModelAndView getMonitorPage(Model model, HttpSession session) {
        model.addAttribute("paginaActual", "chat_usuario");
        Usuario usuarioFinal = (Usuario) session.getAttribute("usuario");
        Integer idUsuario = usuarioFinal.getId();
        Integer idAgente = usuarioFinal.getIdSuperior().getId();
        System.out.println("Id agente: " + idAgente);

        ModelAndView modelAndView = new ModelAndView("Usuariofinal/chat_usuario"); // Crea un ModelAndView con la vista correcta
        Set<String> activeRooms = chatRoomService.getActiveRooms(); // Obtiene las salas activas
        modelAndView.addObject("activeRooms", activeRooms); // Agrega las salas activas al modelo
        modelAndView.addObject("idAgente", idAgente);
        modelAndView.addObject("idUsuario", idUsuario);
        return modelAndView; // Devuelve el ModelAndView
    }

}
