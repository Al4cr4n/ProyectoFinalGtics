package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

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

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechanacimiento;

    private String ruc;

    @ManyToOne
    @JoinColumn(name= "roles_idRoles")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name="distritos_iddistritos", nullable = true)
    private Distrito distrito;

    @ManyToOne
    @JoinColumn(name="zona_idzona", nullable = false)
    private Zona zona;
}
