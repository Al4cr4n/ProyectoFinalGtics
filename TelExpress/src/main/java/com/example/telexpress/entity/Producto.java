package com.example.telexpress.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private Integer idProducto;

    @Column(name = "nombreProducto")
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @Column(name = "categoria")
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @Column(name = "cantidadDisponible")
    @NotNull(message = "La cantidad disponible es obligatoria")
    //@Min(value = 1, message = "La cantidad debe ser mayor que 0")
    private Integer cantidadDisponible;

    @Column(name = "descripcion")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;


    @Column(name = "precio")
    @NotNull(message = "El precio es obligatorio")
    //@DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private Double precio;

    @Column(name = "costoEnvio")
    @NotNull(message = "El costo de envío es obligatorio")
    //@DecimalMin(value = "0.00", message = "El costo de envío debe ser mayor o igual que 0")
    private Double costoEnvio;



    @Column(name = "cantidadComprada")
    private int cantidadComprada;

    // Relación con Resenia
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Resenia> resenias;

    @ManyToMany
    @JoinTable(
            name = "producto_has_proveedor",
            joinColumns = @JoinColumn(name = "producto_idproducto"),
            inverseJoinColumns = @JoinColumn(name = "proveedor_idproveedor")
    )
    //@NotNull(message = "Debe seleccionar un proveedor")
   // private List<Proveedor> proveedores;

    private Set<Proveedor> proveedores = new HashSet<>();



    @ManyToMany(mappedBy = "productos")
   /* @JsonBackReference // Esto evita que se serialicen las órdenes dentro de un producto
    private Set<Ordenes> ordenes= new HashSet<>();*/
    private Set<Ordenes> ordenes;

    @OneToMany(mappedBy = "producto")
    private Set<ProductoOrdenes> productoHasOrdenes = new HashSet<>();


    @Column(name = "image")
    @Lob
    private byte[] image;

    @Column(name = "fechaArribo")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecharribo;


}
