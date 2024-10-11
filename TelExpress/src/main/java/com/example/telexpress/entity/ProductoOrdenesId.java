package com.example.telexpress.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductoOrdenesId implements Serializable {

    private int productoId;
    private int ordenesId;

    // Constructor, equals() y hashCode()

    public ProductoOrdenesId() {}

    public ProductoOrdenesId(int productoId, int ordenesId) {
        this.productoId = productoId;
        this.ordenesId = ordenesId;
    }

    // Getters y setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoOrdenesId that = (ProductoOrdenesId) o;
        return productoId == that.productoId && ordenesId == that.ordenesId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, ordenesId);
    }
}