package com.example.telexpress.controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.AdminRepository;
import com.example.telexpress.repository.UsuarioRepository;
import com.example.telexpress.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.List;
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
    public String detalle_producto(){
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
    public String lista_productos(){
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

}



