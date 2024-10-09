package com.example.telexpress.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class RespuestaId implements Serializable {
    @Column(name= "resenias_idresenias", nullable=false)
    private Integer reseniaid;

    @Column(name="usuario_idusuario", nullable=false)
    private Integer usuarioid;


}
