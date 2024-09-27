package com.example.telexpress.repository;

import com.example.telexpress.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    // Ordenar por precio ascendente
    List<Producto> findAllByOrderByPrecioAsc();

    // Ordenar por precio descendente
    List<Producto> findAllByOrderByPrecioDesc();
    @Query("SELECT p FROM Producto p WHERE p.nombreProducto LIKE %:keyword% OR p.categoria LIKE %:keyword%")
    List<Producto> buscarPorNombreOCategoria(@Param("keyword") String keyword);

}
