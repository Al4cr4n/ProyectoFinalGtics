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

    @Column(name = "nombreProducto")
    private String nombreProducto;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "cantDisponible")
    private int cantDisponible;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "costoEnvio")
    private Double costoEnvio;

    @Column(name = "cantidadTotal")
    private int cantTotal;


}
