package com.example.telexpress.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private Integer calificacion;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;

    @Pattern(regexp = "\\d{9}", message = "El número de teléfono debe tener 9 dígitos")
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    //@NotBlank(message = "La contraseña es obligatoria")
    //@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;
    private String estadoUsuario;
    private String motivo;

    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    private String ruc;

    @Column(name = "notificaciones", length = 1024)
    private String notificaciones;

    @Column(name = "razonSocial", length = 100)
    private String razonSocial;

    @Column(name = "fechanacim", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechanacimiento;

    @ManyToOne
    @JoinColumn(name = "idroles")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "distritos_iddistritos", nullable = true)
    private Distrito distrito;

    @ManyToOne
    @JoinColumn(name = "idzona", nullable = false)
    @NotNull(message = "Debe seleccionar una zona")
    private Zona zona;

    @ManyToOne
    @JoinColumn(name = "idproveedor", nullable = true)
    private Proveedor proveedor;

    // Relación corregida a String para despachador
    @Column(name = "despachador",length = 50, nullable = true)
    private String despachador;

    @ManyToOne
    @JoinColumn(name = "idsuperior", nullable = true)
    private Usuario idSuperior;

    @Column(nullable = false)
    private Integer isBan = 0; // Inicializa con 0 por defecto


    @Column(name = "cantidadcompras")
    private Integer cantidadcompras;

    /*@ManyToOne
    @JoinColumn(name = "fotos_idfotos", nullable = true)
    private Foto foto;*/

    // Relación corregida para jurisdicción, ahora es un String
    @Column(name = "jurisdiccion", length = 50, nullable = true)
    private String jurisdiccion;

    @OneToMany(mappedBy = "usuario")

    private List<Ordenes> ordenes = new ArrayList<>();

    // Nuevo campo para manejar la solicitud de agente
    @Column(name = "solicitudAgente")
    private Integer solicitud;
}
