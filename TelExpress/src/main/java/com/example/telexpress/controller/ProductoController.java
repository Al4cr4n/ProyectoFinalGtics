package com.example.telexpress.controller;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoUsuario;
import com.example.telexpress.entity.Proveedor;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
public class ProductoController {

    final ProductoRepository productoRepository;
    final ProveedorRepository proveedorRepository;
    final ZonaRepository zonaRepository;

    final ProductoUsuarioRepository productoUsuarioRepository;
    private final CategoriasRepository categoriasRepository;

    public ProductoController(ProductoRepository productoRepository,
                              ProveedorRepository proveedorRepository,
                              ZonaRepository zonaRepository,
                              ProductoUsuarioRepository productoUsuarioRepository,
                              CategoriasRepository categoriasRepository) {

        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.zonaRepository = zonaRepository;
        this.productoUsuarioRepository = productoUsuarioRepository;

        this.categoriasRepository = categoriasRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("image"); // Esto excluye el campo 'image' de la validación.
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
        model.addAttribute("listaCategoria",categoriasRepository.findAll());
        return "SuperAdmin/inventario_registrar_producto";
    }

    @PostMapping("/producto/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,
                                  BindingResult result,
                                  @RequestParam("image") MultipartFile image,
                                  @RequestParam("proveedores") List<Integer> proveedoresIds,
                                  RedirectAttributes attr,
                                  Model model) {

        if (result.hasErrors()) {
            System.out.println("Errores encontrados: " + result.getAllErrors());
            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaCategoria",categoriasRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
            return "SuperAdmin/inventario_registrar_producto"; // Volver a la vista de formulario
        }

        // Asociar los proveedores seleccionados al producto
        Set<Proveedor> proveedores = new HashSet<>();
        for (Integer proveedorId : proveedoresIds) {
            Proveedor proveedor = proveedorRepository.findById(proveedorId)
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con ID: " + proveedorId));
            proveedores.add(proveedor);
        }
        producto.setProveedores(proveedores);

        try {
            if (!image.isEmpty()) {
                producto.setImage(image.getBytes());
            }else {
                // Si no se subió una nueva imagen, conserva la existente
                Optional<Producto> existingProducto = productoRepository.findById(producto.getIdProducto());
                if (existingProducto.isPresent()) {
                    producto.setImage(existingProducto.get().getImage());
                }
            }
            System.out.println("producto guardado exitosamente");
            productoRepository.save(producto);
            attr.addFlashAttribute("success", "Producto guardado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            attr.addFlashAttribute("error", "Error al guardar la imagen del producto.");
        }

        return "redirect:/producto/lista";
    }

    @GetMapping("/producto/editar")
    public String editarProducto(Model model, @RequestParam("id") int id) {
        model.addAttribute("activePage", "inventario");

        Optional<Producto> optProduct = productoRepository.findById(id);

        if (optProduct.isPresent()) {
            Producto producto = optProduct.get();
            model.addAttribute("producto", producto);

            // Genera una URL para la imagen si existe
            String imageUrl = producto.getImage() != null ? "/producto/imagen/" + producto.getIdProducto() : null;
            model.addAttribute("imageUrl", imageUrl);

            model.addAttribute("listaCategoria",categoriasRepository.findAll());

            model.addAttribute("listaProveedores", proveedorRepository.findAll());
            model.addAttribute("listaZona", zonaRepository.findAll());
            return "SuperAdmin/inventario_editar_producto";
        } else {
            return "redirect:/producto/lista";
        }
    }


    // Endpoint para servir imágenes desde la base de datos
    @GetMapping("/producto/imagen/{id}")
    @ResponseBody
    public byte[] obtenerImagenProducto(@PathVariable int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        return producto.getImage();
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

        return "SuperAdmin/inventario_superadmin";
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
