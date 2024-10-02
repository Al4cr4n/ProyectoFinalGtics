package com.example.telexpress.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailSolicitud(String destinatario, String producto, String categoria) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Solicitud de reposición de producto");
        mensaje.setText("Se ha solicitado la reposición del producto: " + producto +
                "\nCategoría: " + categoria +
                "\nPor favor, proceda con la reposición.");

        mailSender.send(mensaje);
    }
}
