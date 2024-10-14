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
    private Integer idresenias;  // Esto ya estaba correctamente configurado.

    @Column(length = 500)
    private String descripcion;  // Esto permanece igual, ya que la longitud está acorde a la tabla.

    @Column(length = 45)  // Esto fue modificado para ajustarse al tamaño de la tabla.
    private String calidad;

    @Column(length = 45)  // Esto fue modificado para ajustarse al tamaño de la tabla.
    private String rapidez;

    private int puntuacion;  // Esto permanece igual.

    @Lob
    private Blob foto;  // Esto permanece igual, ya que el tipo en la tabla es BLOB.

    @ManyToOne
    @JoinColumn(name = "productos_idproductos", nullable = true)
    private Producto producto;  // Esto permanece igual.

    @ManyToOne
    @JoinColumn(name = "usuario_idusuario", nullable = true, foreignKey = @ForeignKey(name = "fk_resenias_usuario1"))
    private Usuario usuario;  // Esto permanece igual.

    @Column(name = "tituloresena", length = 200)
    private String tituloresena;  // Esto permanece igual, ya que la longitud está acorde a la tabla.

    @Column(name = "tipoPublicacion", length = 45, nullable = false)  // Esto fue añadido como nuevo. Añadido nullable = false ya que la columna es NOT NULL.
    private String tipoPublicacion;
}
