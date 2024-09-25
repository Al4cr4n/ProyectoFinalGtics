package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private int idProducto;

    @Column(name = "nombreproducto")
    private String nombreProducto;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "cantidaddisponible")
    private int cantidadDisponible;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "costoenvio")
    private Double costoEnvio;

    @Column(name = "cantidadtotal")
    private int cantidadTotal;


}
