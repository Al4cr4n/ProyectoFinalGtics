package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedor")
    private Integer idProveedor;

    @Column(name = "nombreTienda")
    private String nombreTienda;

    @Column(name = "nombreProveedor")
    private String nombreProveedor;

    @Column(name = "dni")
    private String dni;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "correo")
    private String correo;

    @Column(name = "estadoProveedor")
    private String estadoProveedor;

    @ManyToOne
    @JoinColumn(name = "idzona")
    private Zona zona;

    @ManyToMany(mappedBy = "proveedores")
    private List<Producto> productos;



}