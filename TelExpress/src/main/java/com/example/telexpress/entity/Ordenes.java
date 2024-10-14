package com.example.telexpress.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "ordenes")
public class Ordenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idordenes")
    private Integer idOrdenes;


    @Column(name = "fechaArribo")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaArribo;

    @Column(name = "mesCreacion")
    private String mesCreacion;

    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)


    private Date fechaCreacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="estadoOrdenes")
    private String estadoOrdenes;

    @Column(name="agentexorden")
    private Integer agentexorden;

    @ManyToMany
    @JoinTable(
            name = "producto_has_ordenes",
            joinColumns = @JoinColumn(name = "ordenes_idordenes"),
            inverseJoinColumns = @JoinColumn(name = "producto_idproducto"))
   // private List<Producto> productos = new ArrayList<>();
    private Set<Producto> productos;

    /*@ManyToMany
    @JsonManagedReference // Serializa los productos al generar el JSON de una orden
    private Set<Producto> productos = new HashSet<>();*/

    // Método para agregar productos
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    @ManyToOne
    @JoinColumn(name = "usuario_idusuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "ordenes")
    private Set<ProductoOrdenes> productoHasOrdenes = new HashSet<>();
}
