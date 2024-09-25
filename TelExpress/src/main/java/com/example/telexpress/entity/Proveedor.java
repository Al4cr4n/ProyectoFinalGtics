package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproveedor")
    private int idProveedor;

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

    @ManyToOne
    @JoinColumn(name = "zona_idzona1")
    private Zona zona;

}