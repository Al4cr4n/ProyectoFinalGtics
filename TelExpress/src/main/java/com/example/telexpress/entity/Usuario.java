package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String dni;
    private String telefono;
    private String contrasena;
    private String estadoUsuario;
    private String motivo;
/*
    @Column(name = "cantidadcompras")
    private int cantidadcompras;

 */

    @Column(name="razonSocial")
    private String razonSocial;


    @Column(name = "fechanacim", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechanacimiento;

    private String ruc;

    @ManyToOne
    @JoinColumn(name= "idroles")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name="distritos_iddistritos", nullable = true)
    private Distrito distrito;

    @ManyToOne
    @JoinColumn(name="idzona", nullable = false)
    private Zona zona;

    @ManyToOne
    @JoinColumn(name= "idproveedor", nullable = true)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name="despachador_iddespachador", nullable = true)
    private CodigoDespachador codigoDespachador;

    @ManyToOne
    @JoinColumn(name="idSuperior", nullable = true)
    private Usuario idSuperior;

    @Column(nullable = false)
    private Integer isBan = 0; // Inicializa con 0 por defecto

    /*@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ordenes> ordenes;*/

    @ManyToOne
    @JoinColumn(name="jurisdiccion_idjurisdiccion")
    private Jurisdiccion jurisdiccion;

}
