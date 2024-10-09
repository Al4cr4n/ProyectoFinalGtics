package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="producto_has_ordenes")
public class ProductoOrdenes {

    @EmbeddedId
    private ProductoOrdenesId id;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_idproducto")
    private Producto producto;

    @ManyToOne
    @MapsId("ordenesId")
    @JoinColumn(name = "ordenes_idordenes")
    private Ordenes ordenes;

    @Column(name = "cantidadxproducto")
    private int cantidadXProducto;
}
