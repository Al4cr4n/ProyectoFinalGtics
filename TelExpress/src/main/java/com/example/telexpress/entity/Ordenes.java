package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ordenes")
public class Ordenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idordenes")
    private int idOrdenes;

    @Column(name = "estadoOrdenes")
    private String estadoOrdenes;

    @Column(name = "fechaArribo")
    @Temporal(TemporalType.DATE)
    private Date fechaArribo;

    @Column(name = "mesCreacion")
    private String mesCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_idusuario")
    private Usuario usuario;
}
