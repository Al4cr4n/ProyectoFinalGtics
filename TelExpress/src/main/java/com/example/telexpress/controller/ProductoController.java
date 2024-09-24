package com.example.telexpress.controller;

import com.example.telexpress.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;

    }

    @GetMapping({"/producto/lista"})
    public String listarProductos(Model model) {

        model.addAttribute("lista", productoRepository.findAll());
        return "SuperAdmin/inventario_superadmin";
    }

    @GetMapping("/producto/nuevo")
    public String nuevoProductoFrm(Model model) {
        //model.addAttribute("listaPuestos",jobRepository.findAll());
        //model.addAttribute("listaDepartamentos", departmentRepository.findAll());
        return "SuperAdmin/inventario_registrar_producto";
    }

}
