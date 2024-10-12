package com.example.telexpress.repository;
import com.example.telexpress.entity.Despachador;
import com.example.telexpress.entity.Jurisdiccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JurisdiccionRepository extends JpaRepository<Jurisdiccion,Integer>{
    Optional<Jurisdiccion> findByJurisdiccion(String codigo);
}
