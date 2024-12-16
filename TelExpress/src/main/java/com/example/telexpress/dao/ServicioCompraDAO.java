package com.example.telexpress.dao;

import com.example.telexpress.dto.DatosCompra;
import com.example.telexpress.entity.LuhnValidacion;
import com.example.telexpress.entity.Ordenes;
import com.example.telexpress.entity.Pagos;
import com.example.telexpress.entity.Tarjetas;
import com.example.telexpress.repository.OrdenesRepository;
import com.example.telexpress.repository.PagosRepository;
import com.example.telexpress.repository.TarjetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class ServicioCompraDAO {
    @Autowired
    private TarjetasRepository tarjetasRepository;
    @Autowired
    private OrdenesRepository ordenesRepository;
    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String realizarCompra(DatosCompra requestCompra) {
        if (!LuhnValidacion.isValidCardNumber(requestCompra.getNumeroTarjeta())){ //validación
            throw new RuntimeException("El numero de la tarjeta no es valido por luhn");
        }
        Tarjetas tarjeta = tarjetasRepository.findTarjetasByNumeroTarjeta(requestCompra.getNumeroTarjeta())
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada en el sistema"));
        if (!tarjeta.getCodigoCvv().trim().equals(requestCompra.getCodigoCvv().trim())) {
            throw new RuntimeException("El codigo de cvv no es valido");
        }
       /* if (!tarjeta.getFechaExpiracion().equals(requestCompra.getFechaExpiracion())) {
            throw new RuntimeException("Fecha de expiración incorrecta");
        }*/
        System.out.println("Fecha solicitada_: " + requestCompra.getFechaExpiracion());
        System.out.println("Fecha en DB_: " + tarjeta.getFechaExpiracion());
        try {
            // Parsear la fecha ingresada (MM/yy)
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/yy");
            Date fechaIngresada = inputFormat.parse(requestCompra.getFechaExpiracion());

            // Extraemos solo el mes y el año de la fecha ingresada (Date del DTO)
            Calendar calSolicitada = Calendar.getInstance();
            calSolicitada.setTime(fechaIngresada);
            int mesSolicitado = calSolicitada.get(Calendar.MONTH) + 1; // Los meses en Calendar empiezan en 0
            int anioSolicitado = calSolicitada.get(Calendar.YEAR);
            System.out.println("Fecha solicitada: " + requestCompra.getFechaExpiracion());
            System.out.println("Fecha en DB: " + tarjeta.getFechaExpiracion());

            // Extraemos solo el mes y el año de la fecha almacenada en la base de datos
            Calendar calDB = Calendar.getInstance();
            calDB.setTime(tarjeta.getFechaExpiracion());
            int mesDB = calDB.get(Calendar.MONTH) + 1;
            int anioDB = calDB.get(Calendar.YEAR);
            System.out.println("Mes solicitado: " + mesSolicitado);
            System.out.println("Año solicitado: " + anioSolicitado);
            System.out.println("Mes en DB: " + mesDB);
            System.out.println("Año en DB: " + anioDB);

            // Comparamos mes y año
            if (mesSolicitado != mesDB || anioSolicitado != anioDB) {
                throw new RuntimeException("Fecha de expiración incorrecta");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la fecha de expiración: " + e.getMessage());
        }

        // Validación de fecha expirada
        Date ahora = new Date();
        if (tarjeta.getFechaExpiracion().before(ahora)) {
            throw new RuntimeException("La tarjeta ingresada está expirada.");
        }

        if (tarjeta.getSaldo().compareTo(requestCompra.getMonto()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        //actualización en la tarjeta
        tarjeta.setSaldo(tarjeta.getSaldo().subtract(requestCompra.getMonto()));
        tarjetasRepository.save(tarjeta);

        //cambiando el estado de la orden
        Ordenes orden = ordenesRepository.findById(requestCompra.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        orden.setEstadoOrdenes("CREADO");
        orden.setFechaCreacion(new Date());
        // Obtener el nombre del mes a partir de la fecha actual
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("es", "ES"));
        String mesCreacion = monthFormat.format(new Date()); // "Diciembre", "Enero", etc.
        orden.setMesCreacion(mesCreacion); // Actualiza el mes de creación
        ordenesRepository.save(orden);

        Pagos pago = new Pagos();
        pago.setMonto(requestCompra.getMonto().doubleValue());
        pago.setNumeroTarjeta(requestCompra.getNumeroTarjeta());
        pago.setFechaPago(new Date());
        pago.setMetodoPago("Tarjeta");
        pago.setEstadoPago("Completado");
        pago.setOrdenes(orden);
        pago.setUsuario(orden.getUsuario());
        pagosRepository.save(pago);

        return "Comprado exitosamente";
    }

    private void enviarBoleta(String correo, String numeroOrden, String zona, String direccion) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Boleta de Compra");
        mensaje.setText("Gracias por tu compra.\n\nDetalles de la Orden:\n" +
                "N° Orden: " + numeroOrden + "\n" +
                "Zona: " + zona + "\n" +
                "Dirección: " + direccion + "\n" +
                "Gracias por confiar en nosotros.");
        mailSender.send(mensaje);
    }
}
