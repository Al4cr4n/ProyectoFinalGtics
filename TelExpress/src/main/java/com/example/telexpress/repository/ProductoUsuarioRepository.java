package com.example.telexpress.repository;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.ProductoUsuario;
import com.example.telexpress.entity.ProductoUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoUsuarioRepository extends JpaRepository<ProductoUsuario, ProductoUsuarioId> {
    //List<ProductoUsuario> findByProductoId(Producto producto);
    List<ProductoUsuario> findByProducto_IdProducto(int idProducto);
}
