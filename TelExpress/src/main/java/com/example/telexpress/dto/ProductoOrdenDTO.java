package com.example.telexpress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoOrdenDTO {
    private String title; // nombreProducto
    private Integer quantity; // cantidadxproducto
    private Double unitPrice; // precio

    public ProductoOrdenDTO(String title, Integer quantity, Double unitPrice) {
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
