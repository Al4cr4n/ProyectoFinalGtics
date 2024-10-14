package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Blob;

@Entity
@Getter
@Setter
@Table(name = "resenias")
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idresenias;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 500)
    private String calidad;

    @Column(length = 500)
    private String rapidez;

    private int puntuacion;

    @Lob
    private Blob foto;

    @ManyToOne
    @JoinColumn(name = "productos_idproductos", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_idusuario", nullable = true, foreignKey = @ForeignKey(name = "fk_resenias_usuario1"))
    private Usuario usuario;

    @Column(name = "tituloresena", length = 200)
    private String tituloresena;

    @Column(name = "tipoPublicacion", length = 45, nullable = false)
    private String tipoPublicacion;
}





