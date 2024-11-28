package com.example.telexpress.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
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

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Cambia estos métodos para que coincidan con los nombres de columna
    public String getTitle() { return titulo; }
    public void setTitle(String titulo) { this.titulo = titulo; }

    public String getContent() { return contenido; }
    public void setContent(String contenido) { this.contenido = contenido; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}