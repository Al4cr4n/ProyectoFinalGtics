package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProductoController {

    final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;

    }

    @GetMapping({"/producto/lista", "inventario_superadmin"})
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

    @PostMapping("/producto/guardar")
    public String guardarProducto(Producto producto, RedirectAttributes attr) {
        productoRepository.save(producto);
        return "redirect:/producto/lista";
    }

    @GetMapping("/producto/editar")
    public String editarProducto(Model model, @RequestParam("id") int id) {

        Optional<Producto> optProduct = productoRepository.findById(id);

        if (optProduct.isPresent()) {
            Producto producto = optProduct.get();
            model.addAttribute("producto", producto);
            //model.addAttribute("listaCategorias",categoryRepository.findAll());
            //model.addAttribute("listaProveedores",supplierRepository.findAll());
            return "SuperAdmin/editarProducto";
        } else {
            return "redirect:/producto/lista";
        }
    }

    @GetMapping("/producto/borrar")
    public String borrarProducto(Model model,
                                 @RequestParam("id") int id,
                                 RedirectAttributes attr) {

        Optional<Producto> optProduct = productoRepository.findById(id);

        if (optProduct.isPresent()) {
            productoRepository.deleteById(id);
        }
        return "redirect:/producto/lista";

    }

}
