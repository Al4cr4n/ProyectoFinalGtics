package com.example.telexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.telexpress.entity.Zona;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {

    // Método para encontrar una zona por su nombre
    Optional<Zona> findByNombre(String nombre);
}
