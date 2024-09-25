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
    private Integer idusuario;

    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private String dni;
    private String telefono;

    private String ruc;


}
