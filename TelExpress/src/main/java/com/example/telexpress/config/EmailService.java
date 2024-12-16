package com.example.telexpress.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

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

    public void sendWelcomeEmail(String toEmail, String userName) throws MessagingException {
        // Crear el mensaje
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Configurar el correo
        messageHelper.setFrom("TelExpress <telexpress.adm@gmail.com>");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject("Bienvenido a TelExpress");

        // Configurar el contenido del correo en HTML
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>¡Hola, " + userName + "!</h2>"
                + "<p>Gracias por registrarte en <b>TelExpress</b>. ¡Bienvenido a nuestra comunidad!</p>"
                + "<img src='cid:logoImage' alt='TelExpress Logo' width='300' height='150'/>"
                + "</body>"
                + "</html>";

        messageHelper.setText(htmlContent, true);

        // Adjuntar la imagen del logo
        FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo2.jpg"));
        messageHelper.addInline("logoImage", logo);

        // Enviar el correo
        mailSender.send(mimeMessage);
    }
}
