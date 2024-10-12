package com.example.telexpress.repository;
import com.example.telexpress.entity.Jurisdiccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JurisdiccionRepository extends JpaRepository<Jurisdiccion,Integer>{
}
