package com.example.telexpress.repository;

import com.example.telexpress.entity.Tarjetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarjetasRepository extends JpaRepository<Tarjetas, Integer> {
    Optional<Tarjetas> findTarjetasByNumero_tarjeta (String numeroTarjeta);
}
