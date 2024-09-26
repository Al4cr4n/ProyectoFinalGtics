package com.example.telexpress.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ProductoUsuarioId implements Serializable {
    @Column(name = "productos_idproductos")
    private int productoId;

    @Column(name = "usuario_idusuario")
    private int usuarioId;
}
