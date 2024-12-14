package com.example.telexpress.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpagos")
    private Integer idPagos;

    @Column(name = "monto", nullable = false)
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private Double monto;

    @Column(name = "numeroTarjeta", nullable = false, length = 45)
    @NotBlank(message = "El número de tarjeta es obligatorio")
    private String numeroTarjeta;


    @Column(name = "fechaPago", nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // Cambiado a TIMESTAMP para incluir fecha y hora
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de pago es obligatoria")
    private Date fechaPago;

    @Column(name = "metodoPago", nullable = false, length = 45)
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Column(name = "estadoPago", nullable = false, length = 45)
    @NotBlank(message = "El estado del pago es obligatorio")
    private String estadoPago;

    @ManyToOne
    @JoinColumn(name = "ordenes_idordenes", nullable = false)
    @NotNull(message = "Debe asociar un pago a una orden")
    private Ordenes ordenes;

    @ManyToOne
    @JoinColumn(name = "usuario_idusuario", nullable = false)
    @NotNull(message = "Debe asociar un pago a un usuario")
    private Usuario usuario;
}
