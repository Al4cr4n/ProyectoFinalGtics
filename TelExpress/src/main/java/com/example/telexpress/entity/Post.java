package com.example.telexpress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name ="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpost")
    private Long id;

    @NotBlank(message = "Título no puede estar vacío")
    @Size(max = 255, message = "El título debe tener máximo 255 caracteres")
    @Column(name = "title", nullable = false, length = 255)
    private String titulo;

    @NotBlank(message = "Contenido no puede estar vacío")
    @Size(max = 5000, message = "El contenido debe tener máximo 5000 caracteres")
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Post() {
        this.createdAt = LocalDateTime.now();
    }


}