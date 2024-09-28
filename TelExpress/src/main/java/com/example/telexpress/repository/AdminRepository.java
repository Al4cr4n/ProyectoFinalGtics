package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Usuario,Integer>{
    /*Query para obtener a todos los usuarios por su id_rol=4(usuario final)*/
    @Query(value = "select * from usuario where idroles = ?1",
            nativeQuery= true)
    List<Usuario> buscarUsuarioPorRol(Integer idRol);

    //Query para obtener a todos los agentes por su id_rol=3(agente)
    @Query(value = "select * from usuario where idroles= 3",
            nativeQuery= true)
    List<Usuario> buscarAgentePorRol();

    /*Query para obtener a todos los coordis por su id_rol=2()
    @Query(value = "select * from usuario where roles_idRoles= 2",
            nativeQuery= true)
    List<Usuario> buscarCoordiPorRol();*/

    @Query(value = "SELECT * FROM usuario WHERE nombre LIKE CONCAT('%', :searchTerm, '%') OR "
            + "apellido LIKE CONCAT('%', :searchTerm, '%') OR "
            + "dni LIKE CONCAT('%', :searchTerm, '%')", nativeQuery = true)
    List<Usuario> searchByNameOrDni(@Param("searchTerm") String searchTerm);

    // busqueda por nombre, apellido o DNI entre los usuarios con rol (rolid)
    @Query("SELECT u FROM Usuario u WHERE u.rol.id = :rolId AND (u.nombre LIKE %:searchTerm% OR u.apellido LIKE %:searchTerm% OR u.dni LIKE %:searchTerm%)")
    List<Usuario> searchByNameOrDniAndRol(@Param("searchTerm") String searchTerm, @Param("rolId") int rolId);





}
