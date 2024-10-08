package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "despachador")
public class CodigoDespachador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddespachador")
    private int iddespachador;

    @Column(name = "despachador")
    private String despachador;

    @Column(name = "estado")
    private String estado;

}
