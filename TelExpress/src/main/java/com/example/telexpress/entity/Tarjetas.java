package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate fechaExpiracion;

    private String codigoCvv;

    private Double saldo;
}
