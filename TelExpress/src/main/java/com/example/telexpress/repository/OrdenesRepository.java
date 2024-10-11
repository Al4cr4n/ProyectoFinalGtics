package com.example.telexpress.repository;
import com.example.telexpress.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.telexpress.entity.Ordenes;
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

    Optional<Ordenes> findByUsuarioAndEstadoOrdenesUser(Usuario usuario, String estadoOrdenesUser);

    @Query(value = "SELECT o.idordenes, " +
            "o.estadoOrdenes, " +
            "o.fechaArribo, " +
            "o.usuario_idusuario, " +
            "o.mesCreacion, " +
            "o.fechaCreacion " +
            "FROM ordenes o " +
            "WHERE o.usuario_idusuario = :idusuario", nativeQuery = true)
    List<Ordenes> findOrdenesByUsuario(@Param("idusuario") int idusuario);

}
