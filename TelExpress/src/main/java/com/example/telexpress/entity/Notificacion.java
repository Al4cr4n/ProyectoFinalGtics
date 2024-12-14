package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name="Notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="idnotificacion", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="usuario_idusuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="ordenes_idordenes", nullable=false)
    private Ordenes ordenes;

    @Column(name="contenido", nullable = false)
    private String contenido;

    @Column(name="leido", nullable = false)
    private Boolean leido;

    @Column(name="fechacreado", nullable = false)
    private LocalDateTime fechacreado;

}
