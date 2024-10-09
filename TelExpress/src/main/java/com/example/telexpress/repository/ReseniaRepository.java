package com.example.telexpress.repository;

import com.example.telexpress.entity.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import  java.util.List;

public interface ReseniaRepository extends JpaRepository<Resenia, Integer> {
    // Método personalizado para obtener todas las reseñas de un producto específico
    List<Resenia> findByProductoIdProducto(Integer idProducto);

    // Método para calcular el promedio de las puntuaciones de las reseñas de un producto
    @Query("SELECT AVG(r.puntuacion) FROM Resenia r WHERE r.producto.idProducto = :idproducto")
    Double obtenerPromedioPuntuacion(@Param("idproducto") Integer idproducto);

    // Contar las reseñas relacionadas con un producto específico
    Long countByProductoIdProducto(Integer idProducto);
}
