package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name= "distritos")
@Getter
@Setter
public class Distrito {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="iddistritos")
    private Integer id;

    @Column(name="nombreDistrito")
    private String nombredistrito;

    @ManyToOne
    @JoinColumn(name="zona_idzona")
    private Zona zona;
}
