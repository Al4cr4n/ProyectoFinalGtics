package com.example.telexpress.repository;

import com.example.telexpress.entity.Rol;
import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Contar usuarios por estado
    long countByEstadoUsuario(String estadoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol.id = 3 AND u.codigoDespachador.estado = :estado")
    List<Usuario> findByRolAndEstado(@Param("estado") String estado);

    @Query("SELECT u FROM Usuario u WHERE u.zona.idzona = 1 AND u.codigoDespachador.estado = :zona")
    List<Usuario> findByZona(@Param("zona") String zona);

    List<Usuario> findByRol_Id(int rolId);
    List<Usuario> findByRol_IdAndCodigoDespachador_Estado(int rolId, String estado);
    long countByRol_Id(Long id);



    // Método personalizado para obtener solo usuarios activos
    //List<Usuario> findByEstadoIgnoreCase(String estado);

    //para que acepte una lista de estados:
    List<Usuario> findByEstadoUsuarioIgnoreCaseIn(List<String> estados);

    // Buscar por estado, y coincidir nombre o apellido con el término de búsqueda
    List<Usuario> findByEstadoUsuarioIgnoreCaseInAndNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(List<String> estados, String nombre, String apellido);

    // Método para obtener todos los usuarios cuyo estado es 'Baneado'
    List<Usuario> findByEstadoUsuario(String estadoUsuario);



    // Método para buscar por nombre o apellido (solo usuarios baneados)
    List<Usuario> findByEstadoUsuarioAndNombreContainingOrApellidoContaining(String estadoUsuario, String nombre, String apellido);



    // Método para buscar por nombre o apellido (solo usuarios baneados con motivo no nulo)
    List<Usuario> findByEstadoUsuarioAndNombreContainingOrApellidoContainingAndMotivoIsNotNull(String estadoUsuario, String nombre, String apellido);

    // Método para obtener todos los usuarios baneados con motivo no nulo
    List<Usuario> findByEstadoUsuarioAndMotivoIsNotNull(String estadoUsuario);



}
