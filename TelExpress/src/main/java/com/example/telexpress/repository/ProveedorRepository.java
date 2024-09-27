package com.example.telexpress.repository;


import com.example.telexpress.entity.Proveedor;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    List<Proveedor> findByNombreProveedorContaining(String nombre);
    List<Proveedor> findByZona_Idzona(Integer idzona);
    long countByEstadoProveedor(String estado);
}
