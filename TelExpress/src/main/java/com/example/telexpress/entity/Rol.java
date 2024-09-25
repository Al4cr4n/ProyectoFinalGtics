package com.example.telexpress.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity(name="roles")
@Getter
@Setter
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRoles")
    private Integer id;

    private String nombrerol;
}
