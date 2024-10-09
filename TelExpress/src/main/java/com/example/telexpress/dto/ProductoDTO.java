package com.example.telexpress.dto;

public class ProductoDTO {
    private int idProducto;
    private String nombreProducto;
    private Double precio;
    private int cantidadDisponible;

    // Constructor
    public ProductoDTO(int idProducto, String nombreProducto, Double precio, int cantidadDisponible) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters and Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}

