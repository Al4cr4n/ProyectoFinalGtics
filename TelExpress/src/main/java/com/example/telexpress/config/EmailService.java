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
        messageHelper.setSubject("Bienvenid@ a TelExpress");

        // Configurar el contenido del correo en HTML
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>¡Hola, " + userName + "!</h2>"
                + "<p>Gracias por registrarte en <b>TelExpress</b>. ¡Bienvenid@ a nuestra comunidad!</p>"
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

    public void sendForgotPasswordEmail(String toEmail, String tempPassword, String userName) throws MessagingException {
        // Crear el mensaje
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Configurar el correo
        messageHelper.setFrom("TelExpress <telexpress.adm@gmail.com>");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject("Restablecimiento de Contraseña - TelExpress");

        // Contenido del correo en HTML
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>¡Hola, " + userName + "!</h2>"
                + "<p>Hemos recibido una solicitud para restablecer tu contraseña.</p>"
                + "<p>Tu nueva contraseña temporal es: <b>" + tempPassword + "</b></p>"
                + "<p>Por favor, inicia sesión y cambia tu contraseña lo antes posible.</p>"
                + "<br/>"
                + "<img src='cid:logoImage' alt='TelExpress Logo' width='300' height='150'/>"
                + "</body>"
                + "</html>";

        messageHelper.setText(htmlContent, true);

        // Adjuntar el logo
        FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo2.jpg"));
        messageHelper.addInline("logoImage", logo);

        // Enviar el correo
        mailSender.send(mimeMessage);
    }

    public void sendRoleUpdateEmail(String toEmail, String tempPassword, Integer userId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setFrom("TelExpress <telexpress.adm@gmail.com>");
            messageHelper.setTo(toEmail);
            messageHelper.setSubject("Actualización de Rol - TelExpress");

            String linkCambioPassword = "http://localhost:8080/login?logout" + userId;
            String htmlContent = "<html>"
                    + "<body>"
                    + "<h2>¡Felicidades!</h2>"
                    + "<p>Ha sido aprobado como agente. Su contraseña temporal es: <b>" + tempPassword + "</b></p>"
                    + "<p>Por favor, acceda al siguiente enlace para establecer su nueva contraseña:</p>"
                    + "<a href='" + linkCambioPassword + "'>Cambiar Contraseña</a>"
                    + "</body>"
                    + "</html>";

            messageHelper.setText(htmlContent, true);
            // Adjuntar el logo
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo2.jpg"));
            messageHelper.addInline("logoImage", logo);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de actualización de rol", e);
        }
    }

    public void sendCoordinatorCredentialsEmail(String toEmail, String userName, String password) throws MessagingException {
        // Crear el mensaje
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Configurar el correo
        messageHelper.setFrom("TelExpress <telexpress.adm@gmail.com>");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject("Bienvenid@ a TelExpress - Credenciales de acceso");

        // Configurar el contenido del correo en HTML
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>¡Hola, " + userName + "!</h2>"
                + "<p>Gracias por ser parte de TelExpress. Aquí tienes tus credenciales de acceso:</p>"
                + "<p><b>Correo:</b> " + toEmail + "</p>"
                + "<p><b>Contraseña:</b> " + password + "</p>"
                + "<p>Por favor, inicia sesión con estas credenciales.</p>"
                + "<br/>"
                + "<img src='cid:logoImage' alt='TelExpress Logo' width='300' height='150'/>"
                + "</body>"
                + "</html>";

        messageHelper.setText(htmlContent, true);

        // Adjuntar el logo (si tienes uno)
        FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo2.jpg"));
        messageHelper.addInline("logoImage", logo);

        // Enviar el correo
        mailSender.send(mimeMessage);
    }


}
