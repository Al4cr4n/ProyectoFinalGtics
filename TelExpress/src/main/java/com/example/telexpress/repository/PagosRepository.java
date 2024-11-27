package com.example.telexpress.repository;

import com.example.telexpress.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {
    List<Pagos> findByEstadoPago(String estadoPago);
}