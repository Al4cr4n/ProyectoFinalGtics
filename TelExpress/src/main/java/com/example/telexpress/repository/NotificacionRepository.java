package com.example.telexpress.repository;

import com.example.telexpress.entity.Notificacion;
import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion,Integer>{
    //Notificaciones no leidas, ordenadas por fecha
    List<Notificacion> findByUsuarioAndLeidoFalseOrderByFechacreadoAsc(Usuario usuario);

    //Notificaciones de un usuario
    List<Notificacion> findByUsuarioOrderByFechacreadoDesc(Usuario usuario);
}
