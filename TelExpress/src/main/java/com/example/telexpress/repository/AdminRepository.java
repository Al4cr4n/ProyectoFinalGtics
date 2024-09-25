package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Usuario,Integer>{
    /*Query para obtener a todos los usuarios por su id_rol=4(usuario final)*/
    @Query(value = "select * from usuario where roles_idRoles = ?1",
            nativeQuery= true)
    List<Usuario> buscarUsuarioPorRol(Integer idRol);

    /*Query para obtener a todos los agentes por su id_rol=3(agente)
    @Query(value = "select * from usuario where roles_idRoles= 3",
            nativeQuery= true)
    List<Usuario> buscarAgentePorRol();

    Query para obtener a todos los coordis por su id_rol=2()
    @Query(value = "select * from usuario where roles_idRoles= 2",
            nativeQuery= true)
    List<Usuario> buscarCoordiPorRol();*/


}
