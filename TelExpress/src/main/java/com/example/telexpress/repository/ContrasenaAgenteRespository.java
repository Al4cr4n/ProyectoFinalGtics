package com.example.telexpress.repository;

import com.example.telexpress.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContrasenaAgenteRespository extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT contrasena from usuario where idroles =3 AND idusuario = 3",
            nativeQuery = true)
    String findcontrasena(Integer idusuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE usuario SET contrasena = ?2 WHERE idusuario = ?1", nativeQuery = true)
    void updatecontrasena(Integer idusuario, String newPassword);


}
