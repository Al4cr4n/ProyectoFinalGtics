package com.example.telexpress.controller;
import com.example.telexpress.dto.ProductoDTO;
import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.telexpress.repository.DistritoRepository;
import org.springframework.web.bind.annotation.GetMapping;

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


    public UsuarioController(UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ReseniaRepository reseniaRepository, OrdenesRepository ordenesRepository, DistritoRepository distritoRepository,CoordinadorRepository coordinadorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.reseniaRepository = reseniaRepository;
        this.ordenesRepository = ordenesRepository;
        this.distritoRepository = distritoRepository;
        this.coordinadorRepository = coordinadorRepository;
    }

    private final OrdenesRepository ordenesRepository;
    private final DistritoRepository distritoRepository;



    @GetMapping({"","/inicio"})
    public String index(Model model){
        model.addAttribute("activePage", "inicio");

        return "Usuariofinal/inicio_usuariofinal";
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
        Integer cantidad = data.get("cantidad"); // cantidad de los productos en una orden

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
            ordenPendiente.setEstadoOrdenes("Pendiente");
            ordenesRepository.save(ordenPendiente);
        }

        // Obtener el producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Verificar si el producto ya está en la orden
        /*Optional<ProductoOrdenes> productoOrden = ordenPendiente.getProductos().stream()
                .filter(p -> p.getProducto().getIdProducto().equals(productoId))
                .findFirst();
        if (productoOrden.isPresent()) {
            // Si el producto ya está en la orden, aumentar la cantidad
            ProductoOrdenes po = productoOrden.get();
            po.setCantidadXProducto(po.getCantidadXProducto() + cantidad); // Actualiza la cantidad
        } else {
            // Si no existe, se agrega un nuevo producto a la orden
            ProductoOrdenes nuevoProductoOrden = new ProductoOrdenes();
            nuevoProductoOrden.setProducto(producto);
            nuevoProductoOrden.setOrdenes(ordenPendiente);
            nuevoProductoOrden.setCantidadXProducto(cantidad); // Setear la cantidad
            ordenPendiente.getProductos().add(nuevoProductoOrden);
        }

        ordenesRepository.save(ordenPendiente);

        return "Producto agregado al carrito";*/

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
    public String lista_pedidos(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Ordenes> ordenes = ordenesRepository.findOrdenesByUsuario(18);
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Obtener el agente asignado a cada usuario a través del idsuperior
        Map<Integer, String> agentesMap = new HashMap<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getIdSuperior() != null) {
                // Obtener el agente directamente del idSuperior
                Usuario agente = usuario.getIdSuperior();
                agentesMap.put(usuario.getId(), agente != null ? agente.getNombre() : "Sin Asignar");
            } else {
                agentesMap.put(usuario.getId(), "Sin Asignar");
            }
        }

        // Pasar las órdenes, usuarios y agentes al modelo para la vista
        model.addAttribute("ordenes", ordenes);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("agentesMap", agentesMap);

        return "Usuariofinal/lista_pedidos";
    }


    @GetMapping("/pedidos/editar")
    public String editarPedidos(Model model, @RequestParam("id") int id) {

        Optional<Ordenes> optionalOrdenes = ordenesRepository.findById(id);

        if (optionalOrdenes.isPresent()) {
            Ordenes ordenes = optionalOrdenes.get();

            model.addAttribute("ordenes", ordenes);

            return "Usuariofinal/editar_orden";
        } else {
            return "redirect:/usuario/lista_pedidos";
        }
    }
    @PostMapping("/pedidos/guardar")
    public String guardarPedidos(Ordenes ordenes, RedirectAttributes attr) {
        ordenesRepository.save(ordenes);
        return "redirect:/usuario/lista_pedidos";
    }
    @GetMapping("/pedidos/borrar")
    public String borrarPedidos(Model model,
                                  @RequestParam("id") int id,
                                  RedirectAttributes attr) {

        Optional<Ordenes> optProduct = ordenesRepository.findById(id);


        if (optProduct.isPresent()) {
            ordenesRepository.deleteById(id);
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

    @GetMapping("/pagar")
    public String pagar(){
        return "Usuariofinal/pagar";
    }

    @GetMapping("/pago")
    public String pago(Model model){

        model.addAttribute("activePage", "pago");
        return "Usuariofinal/pago";
    }

    @GetMapping("/resenia")
    public String resenia(Model model){
        model.addAttribute("activePage", "resenia");

        return "Usuariofinal/resenia";
    }

    @GetMapping("/respuesta_resenia")
    public String respuesta_resenia(){
        return "Usuariofinal/respuesta_resenia";
    }

    @GetMapping("/unete")
    public String unete(Model model){

        model.addAttribute("activePage", "unete");
        return "Usuariofinal/unete";
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

    @GetMapping("/cambiar_contrasena")
    public String cambiar_contrasena(Model model){
        int id =4;
        //String passw = usuarioRepository.findcontrasena(id);
        //model.addAttribute("passw", passw);
        return "Usuariofinal/cambiar_contrasena";
    }
    @PostMapping("/cambiar_contrasena")
    public String ActualizarContraAgente(
            @RequestParam("password") String currentPassword,  // Contraseña actual
            @RequestParam("new-password-again") String newPassword, // Nueva contraseña
            @RequestParam("new-password") String confirmNewPassword, // Confirmación de la nueva contraseña
            Model model) {

        int id = 4; // ID del agente

        // Obtener la contraseña almacenada en la base de datos
        //String storedPassword = usuarioRepository.findcontrasena(id);

        // Verificar que la contraseña actual sea correcta
        /*if (!currentPassword.equals(storedPassword)) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "Agente/cambio_contra_agente"; // Retornar a la vista con mensaje de error
        }*/

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
        //usuarioRepository.updatecontrasena(id, newPassword);
        System.out.println(newPassword);
        // Redireccionar al perfil del agente con mensaje de éxito
        model.addAttribute("success", "Contraseña cambiada exitosamente.");
        return "Usuariofinal/cambiar_contrasena"; // Redirigir al perfil
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



