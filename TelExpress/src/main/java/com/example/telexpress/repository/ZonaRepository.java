package com.example.telexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.telexpress.entity.Zona;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona,Integer>{

}

