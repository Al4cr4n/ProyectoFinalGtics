package com.example.telexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.telexpress.entity.Ordenes;

@Repository
public interface OrdenesRepository extends JpaRepository<Ordenes, Integer> {

    // Contar órdenes por estado
    long countByEstadoOrdenes(String estado);

}
