package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT nombre FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findnombre(Integer idusuario);
    //@Query(value = "SELECT nombre from usuario where idroles =3 AND idusuario = 3",
    //        nativeQuery = true)
    //String findnombre(Integer idusuario);

    @Query(value = "UPDATE nombre from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String updatenombre(Integer idusuario);

    @Query(value = "SELECT apellido from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String findapellido(Integer idusuario);

    @Query(value = "UPDATE apellido from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String updateapellido(Integer idusuario);

    @Query(value = "SELECT codigoDespachador from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String findcodigoDespachador(Integer idusuario);

    @Query(value = "SELECT correo FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findcorreo(Integer idusuario);

    @Query(value = "SELECT telefono FROM usuario WHERE idusuario = ?1", nativeQuery = true)
    String findtelefono(Integer idusuario);



}
