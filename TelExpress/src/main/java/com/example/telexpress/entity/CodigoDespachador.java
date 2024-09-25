package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "codigodespachador")
public class CodigoDespachador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodigoDespachador")
    private int idcodigoDespachador;

    @Column(name = "codigoDespachador")
    private String codigoDespachador;

    @Column(name = "estado")
    private String estado;

}
