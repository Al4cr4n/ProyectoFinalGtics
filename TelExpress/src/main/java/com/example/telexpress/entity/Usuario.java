package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "fechanacim", nullable = false)
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

    @OneToOne
    @JoinColumn(name="idcodigoDespachador", nullable = true)
    private CodigoDespachador codigoDespachador;

    @Column(nullable = false)
    private Integer isBan = 0; // Inicializa con 0 por defecto
}
