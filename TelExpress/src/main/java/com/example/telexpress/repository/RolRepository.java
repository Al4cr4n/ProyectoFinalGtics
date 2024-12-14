package com.example.telexpress.repository;

import com.example.telexpress.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    Rol findByRol(String rol);
}