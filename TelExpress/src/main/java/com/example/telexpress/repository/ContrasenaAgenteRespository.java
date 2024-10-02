package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContrasenaAgenteRespository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT contrasena from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String findcontrasena(Integer idusuario);

    @Query(value = "UPDATE contrasena from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String updatecontrasena(Integer idusuario, String newPassword);

}
