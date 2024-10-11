package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity(name= "distritos")
@Getter
@Setter
public class Distrito implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="iddistritos")
    private Integer id;

    @Column(name="nombreDistrito")
    private String nombredistrito;

    @ManyToOne
    @JoinColumn(name="idzona")
    private Zona zona;
}
