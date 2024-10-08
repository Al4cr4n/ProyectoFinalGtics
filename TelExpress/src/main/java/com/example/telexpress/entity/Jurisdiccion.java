package com.example.telexpress.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="jurisdiccion")
public class Jurisdiccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idjurisdiccion")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String jurisdiccion;

    @Column(nullable = false)
    private String estado;
}
