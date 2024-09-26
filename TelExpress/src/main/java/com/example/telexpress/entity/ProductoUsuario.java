package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos_has_usuario")
public class ProductoUsuario {
    @EmbeddedId
    private ProductoUsuarioId id_pu;

    @MapsId("usuarioId")
    @ManyToOne
    @JoinColumn(name = "usuario_idusuario")
    private Usuario usuario;


    @MapsId("productoId")
    @ManyToOne
    @JoinColumn(name = "productos_idproductos")
    private Producto producto;

}
