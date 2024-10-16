package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="despachador")
public class Despachador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="iddespachador")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String despachador;

    @Column(nullable= false)
    private String estado;

}
