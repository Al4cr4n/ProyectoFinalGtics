package com.example.telexpress.repository;

import com.example.telexpress.entity.ProductoProveedor;
import com.example.telexpress.entity.ProductoProveedorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, ProductoProveedorId> {

}
