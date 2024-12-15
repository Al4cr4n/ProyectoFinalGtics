package com.example.telexpress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.mapping.Set;
import java.util.Set;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.HashSet;
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

    @NotBlank(message = "El nombre de tienda es obligatorio")
    @Column(name = "nombreTienda")
    private String nombreTienda;

    @Column(name = "nombreProveedor")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreProveedor;

    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(name = "dni")
    private String dni;

    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    @Column(name = "ruc")
    private String ruc;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo")
    private String correo;

    //@Column(name = "estadoProveedor")
    //private String estadoProveedor;

    @Column(name = "estadoProveedor", nullable = false)
    private String estadoProveedor = "Activo"; // Valor predeterminado


    @NotNull(message = "Debe seleccionar una zona")
    @ManyToOne
    @JoinColumn(name = "idzona", nullable = false)
    private Zona zona;

   // @ManyToMany(mappedBy = "proveedores")
    //private List<Producto> productos;

    @DecimalMin(value = "0.0", message = "El rating no puede ser menor a 0.0")
    @DecimalMax(value = "5.0", message = "El rating no puede ser mayor a 5.0")
    @Column(name = "rating")
    private BigDecimal rating;

    @ManyToMany(mappedBy = "proveedores")
    private Set<Producto> productos = new HashSet<>();
    //private Set <Producto> productos = new HashSet<>();

}