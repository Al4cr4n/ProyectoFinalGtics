package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CoordinadorRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT u.* FROM usuario u WHERE u.idroles = 3 AND u.idzona = :idzona", nativeQuery = true)
    List<Usuario> buscarAgentePorZona(@Param("idzona") Integer idzona);
}
