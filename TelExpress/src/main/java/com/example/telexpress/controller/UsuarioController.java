package com.example.telexpress.controller;
import com.example.telexpress.dto.ProductoDTO;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.telexpress.repository.DistritoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.telexpress.repository.UsuarioRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

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


    public UsuarioController(UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ReseniaRepository reseniaRepository,
                             OrdenesRepository ordenesRepository, DistritoRepository distritoRepository, CoordinadorRepository coordinadorRepository,
                             ProductoOrdenesRepository productoOrdenesRepository, ContrasenaAgenteRespository contrasenaAgenteRespository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.reseniaRepository = reseniaRepository;
        this.ordenesRepository = ordenesRepository;
        this.distritoRepository = distritoRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.productoOrdenesRepository = productoOrdenesRepository;
        this.contrasenaAgenteRespository = contrasenaAgenteRespository;
        this.passwordEncoder = passwordEncoder;
    }

    private final OrdenesRepository ordenesRepository;
    private final DistritoRepository distritoRepository;


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

    @GetMapping("/chat")
    public String chat(Model model){

        model.addAttribute("activePage", "chat");

        return "Usuariofinal/chat";
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
    public String Foro(Model model){

        model.addAttribute("activePage", "foro");
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
            }
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
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "9") int size,
                                  @RequestParam(value = "filtroStock", required = false) String filtroStock){
        model.addAttribute("activePage", "lista_productos");

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

    @GetMapping("/pagar")
    public String pagar(){
        return "Usuariofinal/pagar";
    }

    @GetMapping("/pago")
    public String pago(Model model){

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

}
