/*package com.example.telexpress.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, String boucherPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Confirmación de Compra - TelExpress");
            helper.setText("Usted ha realizado una compra a través de nuestra plataforma. En el archivo adjunto encontrará el boucher de su compra.");

            // Adjuntar el PDF
            FileSystemResource file = new FileSystemResource(new File(boucherPath));
            helper.addAttachment("Boucher.pdf", file);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}
*/