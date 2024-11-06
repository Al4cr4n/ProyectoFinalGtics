package com.example.telexpress.repository;

import com.example.telexpress.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    // Ordenar por precio ascendente
    List<Producto> findAllByOrderByPrecioAsc();

    // Ordenar por precio descendente
    List<Producto> findAllByOrderByPrecioDesc();
    @Query("SELECT p FROM Producto p WHERE p.nombreProducto LIKE %:keyword% OR p.categoria LIKE %:keyword%")
    List<Producto> buscarPorNombreOCategoria(@Param("keyword") String keyword);

    //usados para la vista de lista de productos, el "pageable" establece el tama;o de la pagina (uso de empaginado)
    Page<Producto> findByCantidadDisponible(Integer cantidadDisponible, Pageable pageable);
    Page<Producto> findByCantidadDisponibleGreaterThan(Integer cantidadDisponible, Pageable pageable);
    Page<Producto> findByNombreProductoContainingOrDescripcionContaining(String nombreProducto, String descripcion, Pageable pageable);



    /*@Query(value = "SELECT p.idproducto, " +
            "p.nombreProducto, " +
            "p.categoria, " +
            "p.cantidadDisponible, " +
            "p.descripcion, " +
            "p.precio, " +
            "p.costoEnvio, " +
            "p.cantidadTotal, " +
            "p.cantidadComprada, " +
            "p.modelo, " +
            "p.color " +
            "FROM producto p " +
            "JOIN producto_has_proveedor php ON p.idproducto = php.producto_idproducto " +
            "JOIN proveedor pr ON php.proveedor_idproveedor = pr.idproveedor " +
            "JOIN zona z ON pr.idzona = z.idzona " +
            "WHERE z.idzona = :idzona", nativeQuery = true)
    List<Producto> findProductosByZona(@Param("idzona") int idzona);

     */
    @Query("SELECT p FROM Producto p JOIN p.proveedores pr JOIN pr.zona z WHERE z.idzona = :idzona")
    List<Producto> findProductosByZona(@Param("idzona") int idzona);


    @Modifying
    @Query("UPDATE Producto p SET p.fecharribo = :fechaArribo WHERE p.idProducto = :idProducto")
    int actualizarFechaArribo(@Param("idProducto") int idProducto, @Param("fechaArribo") Date fechaArribo);

    // Opcional: si deseas obtener directamente los productos con fecha de arribo
    List<Producto> findByFecharriboIsNotNull();
}
