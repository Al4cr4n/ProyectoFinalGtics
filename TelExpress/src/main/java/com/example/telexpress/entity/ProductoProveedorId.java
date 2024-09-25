package com.example.telexpress.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Embeddable
public class ProductoProveedorId implements Serializable {
    @Column(name = "producto_idproducto")
    private int productoIdProducto;

    @Column(name = "proveedor_idproveedor")
    private int proveedorIdProveedor;
}
