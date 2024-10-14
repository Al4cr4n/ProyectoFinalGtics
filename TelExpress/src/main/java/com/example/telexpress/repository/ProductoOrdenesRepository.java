package com.example.telexpress.repository;

import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.ProductoOrdenes;
import com.example.telexpress.entity.ProductoOrdenesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoOrdenesRepository extends JpaRepository<ProductoOrdenes, ProductoOrdenesId> {
    List<ProductoOrdenes> findByProductoIdProductoAndOrdenesIdOrdenes(Integer productoId, Integer idOrdenes);
    List<ProductoOrdenes> findByOrdenesIdOrdenes(Integer idOrdenes);
    List<ProductoOrdenes> findByOrdenes(Ordenes ordenes);
    List<ProductoOrdenes> findByIdOrdenesId(Integer ordenesId);

}
