package com.example.telexpress.service;

import com.example.telexpress.entity.*;
import com.example.telexpress.repository.*;
import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private Pusher pusher;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private ZonaRepository zonaRepository;

    //Notificacion para el cambio de estado de la orden
    public void orderChangeNotification(String message,Usuario usuario, Ordenes orden){
        //Crear y guardar la notificacion en la bd
        Notificacion notificacion= new Notificacion();
        notificacion.setContenido(message);
        notificacion.setUsuario(usuario);
        notificacion.setOrdenes(orden);
        notificacion.setFechacreado(LocalDateTime.now());
        notificacion.setLeido(false);
        notificacionRepository.save(notificacion);

        //Enviar la notificacion a traves de Pusher
        Map<String,String> notificationData = new HashMap<>();
        notificationData.put("message", message);
        notificationData.put("orderId", orden.getIdOrdenes().toString());
        notificationData.put("userId", usuario.getId().toString());

        pusher.trigger("ordenes-"+usuario.getId(), "orden-actualizada", notificationData);

    }
    //Obtener las notificaciones no leidas para un usuario especifico
    public List<Notificacion> getUnreadNotifications(Usuario usuario){
        return notificacionRepository.findByUsuarioAndLeidoFalseOrderByFechacreadoAsc(usuario);
    }

    //Notificacion que indicará cuando exista bajo stock y cuando exista stock adecuado
    public void stockNotification(String message, Zona zona){
        //obtener los cordis de la zona
        List<Usuario> coordinadores = usuarioRepository.findByRol_IdAndAndZona_Idzona(rolRepository.findById(2).get(),zona);
        //Crear y guardar notificaciones para cada coordi

        for (Usuario usuario: coordinadores){
            Notificacion notificacion= new Notificacion();
            notificacion.setContenido(message);
            notificacion.setUsuario(usuario);
            notificacion.setFechacreado(LocalDateTime.now());
            notificacion.setLeido(false);
            notificacionRepository.save(notificacion);
        }
        //Crear el payload para el evento
        Map<String, String> notificationData= new HashMap<>();
        notificationData.put("message", message);
        notificationData.put("zonaId", zona.getIdzona().toString());
        notificationData.put("rol",rolRepository.findById(2).get().getRol());


        //Usar el canal basado en rol y zona
        pusher.trigger("stock-admin-zona-" + zona.getIdzona(), "reposicion-stock",notificationData );

    }
    //Marcar una notificacion como leida
    public void markAsRead(Integer Notificacion){
        Notificacion notificacion= notificacionRepository.findById(Notificacion).orElse(null);

        if (notificacion!= null){
            notificacion.setLeido(true);
            notificacionRepository.save(notificacion);
        }

    }

    //Obtener todas las notificacicones para un usuario
    public List<Notificacion> getAllNotifications(Usuario usuario){
        return notificacionRepository.findByUsuarioOrderByFechacreadoDesc(usuario);
    }





}