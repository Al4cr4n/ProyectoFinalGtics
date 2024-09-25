package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "producto_has_proveedor")
public class ProductoProveedor {

    @EmbeddedId
    private ProductoProveedorId id;

    @ManyToOne
    @JoinColumn(name = "proveedor_idproveedor")
    @MapsId("proveedorIdProveedor")
    private Proveedor proveedorId;

    @ManyToOne
    @JoinColumn(name = "producto_idproducto")
    @MapsId("productoIdProducto")
    private Producto productoId;

}
