package com.example.telexpress.repository;
import com.example.telexpress.entity.Despachador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachadorRepository extends JpaRepository<Despachador,Integer> {

}
