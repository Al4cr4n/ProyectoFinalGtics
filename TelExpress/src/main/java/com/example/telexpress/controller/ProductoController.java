package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.ProductoRepository;
import com.example.telexpress.repository.ProveedorRepository;
import com.example.telexpress.repository.ZonaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProductoController {

    final ProductoRepository productoRepository;
    final ProveedorRepository proveedorRepository;
    final ZonaRepository zonaRepository;
    public ProductoController(ProductoRepository productoRepository,
                              ProveedorRepository proveedorRepository,
                              ZonaRepository zonaRepository) {

        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.zonaRepository = zonaRepository;

    }


    @GetMapping({"/producto/lista", "/superadmin/inventario_superadmin", "/superadmin/producto/lista"})
    public String listarProductos(Model model) {

        model.addAttribute("lista", productoRepository.findAll());
        return "SuperAdmin/inventario_superadmin";
    }

    @GetMapping({"/producto/listaTop", "/superadmin/dashboard_superadmin", "/superadmin/producto/listaTop"})
    public String listarTopProductos(Model model) {

        model.addAttribute("listaTop", productoRepository.findAll());
        return "SuperAdmin/dashboard_superadmin";
    }

    @GetMapping("/producto/nuevo")
    public String nuevoProductoFrm(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaProveedores",proveedorRepository.findAll());
        //model.addAttribute("listaDepartamentos", departmentRepository.findAll());
        model.addAttribute("listaZona", zonaRepository.findAll());
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
            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
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
