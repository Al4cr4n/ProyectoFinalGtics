package com.example.telexpress.repository;

import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Pagos;
import com.example.telexpress.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {
    List<Pagos> findByEstadoPago(String estadoPago);

    List<Pagos> findByUsuario_ZonaAndEstadoPago(Zona zona, String estadoPago);

}