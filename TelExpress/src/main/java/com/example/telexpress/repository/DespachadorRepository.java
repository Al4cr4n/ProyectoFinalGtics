package com.example.telexpress.repository;
import com.example.telexpress.entity.Despachador;
import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DespachadorRepository extends JpaRepository<Despachador,Integer> {
    Optional<Despachador> findByDespachador(String codigo);


}
