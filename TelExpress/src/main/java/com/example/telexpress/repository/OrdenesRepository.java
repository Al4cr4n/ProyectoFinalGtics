package com.example.telexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.telexpress.entity.Ordenes;

import java.util.List;

@Repository
public interface OrdenesRepository extends JpaRepository<Ordenes, Integer> {

    // Contar órdenes por estado
    long countByEstadoOrdenes(String estado);
    long countByMesCreacion(String mesCreacion);

    @Query(value = "select * from ordenes where usuario_idusuario = ?1",
            nativeQuery= true)
    List<Ordenes> findByUsuarioId(Integer id);



}
