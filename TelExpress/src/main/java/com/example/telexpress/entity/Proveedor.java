package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedor")
    private int idProveedor;

    @Column(name = "nombretienda")
    private String nombreTienda;

    @Column(name = "nombreproveedor")
    private String nombreProveedor;

    @Column(name = "dni")
    private String dni;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "correo")
    private String correo;

    @ManyToOne
    @JoinColumn(name = "idzona")
    private Zona zona;

    @ManyToMany(mappedBy = "proveedores")
    private List<Producto> productos;
}