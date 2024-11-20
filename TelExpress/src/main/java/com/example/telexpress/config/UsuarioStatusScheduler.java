package com.example.telexpress.config;

import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UsuarioStatusScheduler {

    private final UsuarioRepository usuarioRepository;

    public UsuarioStatusScheduler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Scheduled(cron = "0 0 0 * * ?") // Ejecutar todos los días a medianoche
    @Scheduled(cron = "0 */1 * * * ?") // Ejecutar cada minuto

    public void actualizarEstadoUsuarios() {
        //LocalDateTime haceUnaSemana = LocalDateTime.now().minusDays(7);
        LocalDateTime haceDosMinutos = LocalDateTime.now().minusMinutes(2); // Cambiar a 2 minutos

        // Busca usuarios con lastLogin mayor a una semana
        //List<Usuario> usuariosInactivos = usuarioRepository.findByLastLoginBeforeAndEstadoUsuario(haceUnaSemana, "Activo");
        List<Usuario> usuariosInactivos = usuarioRepository.findByLastLoginBeforeAndEstadoUsuario(haceDosMinutos, "Activo");
        // Actualiza el estado a "Inactivo"
        for (Usuario usuario : usuariosInactivos) {
            usuario.setEstadoUsuario("Inactivo");
            usuarioRepository.save(usuario);
        }

    }



}
