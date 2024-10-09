package com.example.telexpress.controller;
import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.Resenia;
import com.example.telexpress.repository.*;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ReseniaRepository reseniaRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, ProductoRepository productoRepository,
                             ReseniaRepository reseniaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.reseniaRepository = reseniaRepository;
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
        // Añadir el producto al modelo
        model.addAttribute("producto", producto);
        model.addAttribute("promedioPuntuacion", promedioPuntuacion);

        return "Usuariofinal/detalle_producto";
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
    public String FAQS(){
        return "Usuariofinal/FAQS";
    }

    @GetMapping("/Foro")
    public String Foro(){
        return "Usuariofinal/Foro";
    }

    @GetMapping("/lista_pedidos")
    public String lista_pedidos(){
        return "Usuariofinal/lista_pedidos";
    }

    @GetMapping("/lista_productos")
    public String lista_productos(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "9") int size){
        // Definir el paginador
        Pageable pageable = PageRequest.of(page, size);
        // Obtener los productos paginados desde el servicio
        Page<Producto> pagproductos = productoRepository.findAll(pageable);

        // Pasar los productos y la información de paginación al modelo
        model.addAttribute("productos", pagproductos.getContent());
        model.addAttribute("totalPages", pagproductos.getTotalPages());
        model.addAttribute("currentPage", page);

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



