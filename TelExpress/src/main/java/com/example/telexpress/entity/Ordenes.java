package com.example.telexpress.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

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
    private Date fechaArribo;

    @Column(name = "mesCreacion")
    private String mesCreacion;

    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name="estadoOrdenes")
    private String estadoOrdenes;

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
