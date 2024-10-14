package com.example.telexpress.repository;
import com.example.telexpress.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.telexpress.entity.Ordenes;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;

@Repository
public interface OrdenesRepository extends JpaRepository<Ordenes, Integer> {

    // Contar órdenes por estado
    long countByEstadoOrdenes(String estado);
    long countByMesCreacion(String mesCreacion);

    @Query(value = "select * from ordenes where usuario_idusuario = ?1",
            nativeQuery= true)
    List<Ordenes> findByUsuarioId(Integer id);


    // Si necesitas métodos adicionales para búsquedas personalizadas, puedes declararlos aquí.
    List<Ordenes> findByEstadoOrdenes(String estadoOrdenes);

    List<Ordenes> findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCaseOrEstadoOrdenesContainingIgnoreCase(String nombre,String apellido, String estado);

    List<Ordenes> findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(String nombre, String apellido);
    List<Ordenes> findByEstadoOrdenesContainingIgnoreCase(String estado);

    List<Ordenes> findByEstadoOrdenesAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(String estado, String nombre, String apellido);

    // Método para buscar por nombre o apellido y filtrar por los estados permitidos
    List<Ordenes> findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCaseAndEstadoOrdenesIn(
            String nombre, String apellido, List<String> estados);

    // Buscar por estados permitidos y nombre o apellido
    List<Ordenes> findByEstadoOrdenesInAndUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(
            List<String> estados, String nombre, String apellido);

    // Búsqueda de todas las órdenes por estado permitido
    List<Ordenes> findByEstadoOrdenesIn(List<String> estados);

    Optional<Ordenes> findByUsuarioAndEstadoOrdenes(Usuario usuario, String estadoOrdenes);

    @Query(value = "SELECT o.idordenes, " +
            "o.estadoOrdenes, " +
            "o.fechaArribo, " +
            "o.usuario_idusuario, " +
            "o.mesCreacion, " +
            "o.fechaCreacion " +
            "FROM ordenes o " +
            "WHERE o.usuario_idusuario = :idusuario", nativeQuery = true)
    List<Ordenes> findOrdenesByUsuario(@Param("idusuario") int idusuario);

    @Query(value = "SELECT o.idordenes, " +
            "o.estadoOrdenes, " +
            "o.fechaArribo, " +
            "o.usuario_idusuario, " +
            "o.mesCreacion, " +
            "o.fechaCreacion, " +
            "o.agentexorden " +
            "FROM ordenes o " +
            "WHERE o.usuario_idusuario = :idusuario AND o.estadoOrdenes IN :estados", nativeQuery = true)
    List<Ordenes> findOrdenesByUsuarioAAndEstadoOrdenes(@Param("idusuario") int idusuario,@Param("estados") List<String> estados);


    @Query("SELECT o FROM Ordenes o JOIN Usuario u ON o.usuario.id = u.id " +
            "WHERE (u.nombre LIKE %:search% OR u.apellido LIKE %:search% OR o.estadoOrdenes LIKE %:search%)")
    List<Ordenes> buscarOrdenes(@Param("search") List<String> search);

    @Query("SELECT o FROM Ordenes o WHERE o.estadoOrdenes IN :estados")
    List<Ordenes> findAllByEstadoIn(@Param("estados") List<String> estados);

    // Native query para actualizar el agente encargado de las órdenes
    @Modifying
    @Transactional
    @Query(value = "UPDATE ordenes o SET o.agentexorden = (SELECT u.idsuperior FROM usuario u WHERE u.idusuario = o.usuario_idusuario) " +
            "WHERE o.usuario_idusuario = :usuarioId " +
            "AND o.estadoOrdenes IN ('CREADO', 'EN VALIDACION')", nativeQuery = true)
    void updateAgenteEncargadoByUsuario(
            @Param("usuarioId") Integer usuarioId);

}

