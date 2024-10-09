package com.example.telexpress.entity;

import jakarta.persistence.*;

@Entity
@Table(name="respuestas")
public class Respuesta {
    @EmbeddedId
    private RespuestaId id;

    @MapsId("reseniaid")
    @ManyToOne
    @JoinColumn(name="resenias_idresenias")
    private Resenia reseniaid;

    @MapsId("usuarioid")
    @ManyToOne
    @JoinColumn(name="usuario_idusuario")
    private Usuario usuarioid;
    @Column(name ="respuestas", nullable=true)
    private String comentario;


}
