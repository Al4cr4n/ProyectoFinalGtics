package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private Integer id;

    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String dni;
    private String telefono;

    private String ruc;

    @ManyToOne
    @JoinColumn(name= "roles_idRoles")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name="distritos_iddistritos")
    private Distrito distrito;


}
