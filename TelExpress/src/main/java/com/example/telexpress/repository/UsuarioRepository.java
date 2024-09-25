package com.example.telexpress.repository;

import
        com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Contar usuarios por estado
    long countByEstadoUsuario(String estadoUsuario);
}
