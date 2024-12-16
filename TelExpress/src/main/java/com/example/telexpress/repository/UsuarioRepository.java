package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {



    // Contar usuarios por estado
    long countByEstadoUsuario(String estadoUsuario);
    long countByZona(Zona zona);
    long countByZonaAndEstadoUsuario(Zona zona, String estadoUsuario);


    List<Usuario> findByZonaIdzonaAndRolId(Integer idzona, Integer rolId);
    @Query("SELECT u FROM Usuario u WHERE u.zona.idzona = :idzona ORDER BY u.cantidadcompras DESC")
    List<Usuario> findAllByZonaOrderByCantidadCompras(@Param("idzona") int idzona);

    List<Usuario> findByRol_Id(int rolId);
    //List<Usuario> findByRol_IdAndCodigoDespachador_Estado(int rolId, String estado);
    long countByRol_Id(Integer id);
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.id = :idRol AND u.estadoUsuario = :estado")
    long countAgentesActivos(@Param("idRol") int idRol, @Param("estado") String estado);




    // Método personalizado para obtener solo usuarios activos
    //List<Usuario> findByEstadoIgnoreCase(String estado);

    //para que acepte una lista de estados:
    List<Usuario> findByEstadoUsuarioIgnoreCaseIn(List<String> estados);

    // Buscar por estado, y coincidir nombre o apellido con el término de búsqueda
    List<Usuario> findByEstadoUsuarioIgnoreCaseInAndNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(List<String> estados, String nombre, String apellido);

    // Método para obtener todos los usuarios cuyo estado es 'Baneado'
    List<Usuario> findByEstadoUsuario(String estadoUsuario);

    Usuario findById(Integer userId);

    // Método para buscar por nombre o apellido (solo usuarios baneados)
    List<Usuario> findByEstadoUsuarioAndNombreContainingOrApellidoContaining(String estadoUsuario, String nombre, String apellido);



    // Comparar con el campo id del Usuario superior
    List<Usuario> findByIdSuperior_IdAndEstadoUsuarioIgnoreCaseInAndNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            Integer idSuperior, List<String> estados, String nombre, String apellido);

    List<Usuario> findByIdSuperior_IdAndEstadoUsuarioIgnoreCaseIn(Integer idSuperior, List<String> estados);

    // Métodos existentes para usuarios baneados (no es necesario modificarlos)
    List<Usuario> findByEstadoUsuarioAndNombreContainingOrApellidoContainingAndMotivoIsNotNull(
            String estadoUsuario, String nombre, String apellido);

    List<Usuario> findByEstadoUsuarioAndMotivoIsNotNull(String estadoUsuario);

    List<Usuario> findByZona_Idzona(Integer idzona);

   /* @Query(value = "SELECT contrasena from usuario where idroles =4 AND idusuario = 4",
            nativeQuery = true)
    String findcontrasena(Integer idusuario);

    @Query(value = "UPDATE contrasena from usuario where idroles =4 AND idusuario = 4",
            nativeQuery = true)
    String updatecontrasena(Integer idusuario, String newPassword);

    @Query(value = "SELECT nombre FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findnombre(Integer idusuario);

    @Query(value = "UPDATE nombre from usuario where idroles =4 AND idusuario = 4",
            nativeQuery = true)
    String updatenombre(Integer idusuario);

    @Query(value = "SELECT apellido from usuario where idroles =?1 AND idusuario =?1",
            nativeQuery = true)
    String findapellido(Integer idusuario);

    @Query(value = "UPDATE apellido from usuario where idroles =4 AND idusuario = 4",
            nativeQuery = true)
    String updateapellido(Integer idusuario);

    @Query(value = "SELECT codigoDespachador from usuario where idroles =4 AND idusuario = 4",
            nativeQuery = true)
    String findcodigoDespachador(Integer idusuario);

    @Query(value = "SELECT correo FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findcorreo(Integer idusuario);

    @Query(value = "SELECT telefono FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findtelefono(Integer idusuario);

    @Query(value = "SELECT distritos_iddistritos FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findistrito(Integer idusuario);

    @Query(value = "SELECT direccion FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String finddireccion(Integer idusuario);
*/
    List<Usuario> findByIdSuperior(Usuario idSuperior);
    public Usuario findByCorreo(String Correo);

    List<Usuario> findByLastLoginBeforeAndEstadoUsuario(LocalDateTime lastLogin, String estadoUsuario);


    // Native query para actualizar el agente encargado de las órdenes
    @Modifying
    @Transactional
    @Query(value = "UPDATE ordenes o SET o.agentexorden = (SELECT u.idsuperior FROM usuario u WHERE u.idusuario = o.usuario_idusuario) " +
            "WHERE o.usuario_idusuario = :usuarioId " +
            "AND o.estadoOrdenes IN ('CREADO', 'EN VALIDACION', 'EN PROCESO')", nativeQuery = true)
    void updateAgenteEncargadoByUsuario(
            @Param("usuarioId") Integer usuarioId);

    //Metodo para obtener los coordis de la zona correspondiente
    List<Usuario> findByRol_IdAndAndZona_Idzona(Rol rol, Zona zona);
}
