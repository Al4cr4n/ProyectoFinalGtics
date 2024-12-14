package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity(name="tarjetas  ")
public class Tarjetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="numero_tarjeta", length = 16)
    private String numeroTarjeta;

    @Column(name = "fecha_expiracion", nullable = false)
    private Date fechaExpiracion;

    @Column(name="codigo_cvv", length = 3)
    private String codigoCvv;

    @Column(name = "saldo", precision = 12, scale = 2, nullable = false)
    private BigDecimal saldo;
}
