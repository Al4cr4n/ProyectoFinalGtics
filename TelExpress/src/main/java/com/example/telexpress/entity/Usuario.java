package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
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
    private String ruc;

    @Column(name = "notificaciones", length = 1024)
    private String notificaciones;

    @Column(name = "razonSocial", length = 100)
    private String razonSocial;

    @Column(name = "fechanacim", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechanacimiento;

    @ManyToOne
    @JoinColumn(name = "idroles")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "distritos_iddistritos", nullable = true)
    private Distrito distrito;

    @ManyToOne
    @JoinColumn(name = "idzona", nullable = false)
    private Zona zona;

    @ManyToOne
    @JoinColumn(name = "idproveedor", nullable = true)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "despachador", nullable = true)
    private CodigoDespachador codigoDespachador;

    @ManyToOne
    @JoinColumn(name = "idSuperior", nullable = true)
    private Usuario idSuperior;

    @Column(nullable = false)
    private Integer isBan = 0; // Inicializa con 0 por defecto

    /* Comentado según la solicitud */
    //@Column(name = "cantidadcompras")
    //private int cantidadcompras;

    //FOTOS
    /*
    @ManyToOne
    @JoinColumn(name = "fotos_idfotos", nullable = true)
    private Foto foto;*/

    @ManyToOne
    @JoinColumn(name = "jurisdiccion", nullable = true)
    private Jurisdiccion jurisdiccion;

    @OneToMany(mappedBy = "usuario")
    private List<Ordenes> ordenes = new ArrayList<>();
}
