package com.example.telexpress.repository;

import com.example.telexpress.entity.Producto;
import com.example.telexpress.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Integer> {
}
