package com.example.telexpress.repository;

import com.example.telexpress.entity.Rol;
import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Contar usuarios por estado
    long countByEstadoUsuario(String estadoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol.id = 3 AND u.codigoDespachador.estado = :estado")
    List<Usuario> findByRolAndEstado(@Param("estado") String estado);

    List<Usuario> findByRol_Id(int rolId);
    List<Usuario> findByRol_IdAndCodigoDespachador_Estado(int rolId, String estado);
    long countByRol_Id(Long id);




}
