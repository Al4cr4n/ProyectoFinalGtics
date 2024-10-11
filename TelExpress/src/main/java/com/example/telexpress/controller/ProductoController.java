package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoUsuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.ProductoRepository;
import com.example.telexpress.repository.ProductoUsuarioRepository;
import com.example.telexpress.repository.ProveedorRepository;
import com.example.telexpress.repository.ZonaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    final ProductoRepository productoRepository;
    final ProveedorRepository proveedorRepository;
    final ZonaRepository zonaRepository;

    final ProductoUsuarioRepository productoUsuarioRepository;
    public ProductoController(ProductoRepository productoRepository,
                              ProveedorRepository proveedorRepository,
                              ZonaRepository zonaRepository,
                              ProductoUsuarioRepository productoUsuarioRepository) {

        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.zonaRepository = zonaRepository;
        this.productoUsuarioRepository = productoUsuarioRepository;

    }


    @GetMapping({"/producto/lista", "/superadmin/inventario_superadmin", "/superadmin/producto/lista"})
    public String listarProductos(Model model) {
        model.addAttribute("activePage", "inventario");

        model.addAttribute("lista", productoRepository.findAll());
        return "SuperAdmin/inventario_superadmin";
    }


    @GetMapping("/producto/nuevo")
    public String nuevoProductoFrm(Model model) {
        model.addAttribute("activePage", "inventario");

        model.addAttribute("producto", new Producto());
        model.addAttribute("listaProveedores",proveedorRepository.findAll());
        //model.addAttribute("listaDepartamentos", departmentRepository.findAll());
        model.addAttribute("listaZona", zonaRepository.findAll());
        return "SuperAdmin/inventario_registrar_producto";
    }

    @PostMapping("/producto/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,
                                  BindingResult result,
                                  RedirectAttributes attr,
                                  Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
            return "SuperAdmin/inventario_registrar_producto"; // Volver a la vista de formulario
        }
        productoRepository.save(producto);
        return "redirect:/producto/lista";
    }

    @GetMapping("/producto/editar")
    public String editarProducto(Model model, @RequestParam("id") int id) {
        model.addAttribute("activePage", "inventario");

        Optional<Producto> optProduct = productoRepository.findById(id);

        if (optProduct.isPresent()) {
            Producto producto = optProduct.get();
            model.addAttribute("producto", producto);
            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
            return "SuperAdmin/inventario_editar_producto";
        } else {
            return "redirect:/producto/lista";
        }
    }

    @GetMapping("/producto/borrar")
    public String borrarProducto(Model model,
                                 @RequestParam("id") int id,
                                 RedirectAttributes attr){
        model.addAttribute("activePage", "inventario");

        //Optional<Producto> optProduct = productoRepository.findById(id);
        //if (optProduct.isPresent()) {
        //  productoRepository.deleteById(id);
        //}
        //return "redirect:/producto/lista";
        try {
            Optional<Producto> optProduct = productoRepository.findById(id);

            if (optProduct.isPresent()) {
                Producto producto = optProduct.get();

                // Verifica si el producto está relacionado con un usuario
                List<ProductoUsuario> relaciones = productoUsuarioRepository.findByProducto_IdProducto(producto.getIdProducto());

                if (relaciones.isEmpty()) {
                    // Si no hay relaciones, puedes eliminar el producto
                    productoRepository.deleteById(id);
                    attr.addFlashAttribute("success", "Producto eliminado exitosamente.");
                } else {
                    // Si hay relaciones, muestra un mensaje al usuario
                    attr.addFlashAttribute("error", "No se puede eliminar el producto porque está relacionado con usuarios.");
                }
            } else {
                attr.addFlashAttribute("error", "Producto no encontrado.");
            }
        } catch (EntityNotFoundException ex) {
            // Capturamos la excepción si el Usuario no es encontrado
            attr.addFlashAttribute("error", "No se pudo encontrar el usuario relacionado con el producto.");
        } catch (Exception ex) {
            // Capturamos cualquier otra excepción inesperada
            attr.addFlashAttribute("error", "No se puede eliminar el producto, debido a que ha sido comprado por algun usuario y/o proveedor.");
            ex.printStackTrace();  // Esto es útil para depurar, pero puedes eliminarlo en producción
        }
        return "redirect:/producto/lista";
    }
    @GetMapping("/producto/buscar")
    public String buscarProducto(@RequestParam("searchField") String searchField,
                                      Model model) {
        model.addAttribute("activePage", "inventario");

        List<Producto> listaProductos = productoRepository.buscarPorNombreOCategoria(searchField);
        System.out.println("Resultados encontrados: " + listaProductos.size());
        model.addAttribute("lista", listaProductos);

        return "Superadmin/inventario_superadmin";
    }

    @GetMapping("/producto/filtro")
    public String filtrarProductos(@RequestParam(value = "order", required = false, defaultValue = "asc") String order, Model model) {
        model.addAttribute("activePage", "inventario");

        List<Producto> listaProductos;

        // Verificar si el filtro es ascendente o descendente
        if (order.equals("desc")) {
            listaProductos = productoRepository.findAllByOrderByPrecioDesc();
        } else {
            listaProductos = productoRepository.findAllByOrderByPrecioAsc();
        }

        model.addAttribute("lista", listaProductos);
        model.addAttribute("order", order); // Pasar el tipo de orden actual a la vista
        return "SuperAdmin/inventario_superadmin"; // Cambiar esto según tu vista real
    }
}
