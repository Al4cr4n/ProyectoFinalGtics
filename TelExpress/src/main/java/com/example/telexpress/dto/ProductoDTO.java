package com.example.telexpress.dto;

public class ProductoDTO {
    private int idProducto;
    private String nombreProducto;
    private Double precio;
    private int cantidadDisponible;
    private int cantidadxproducto; // cantidad agregada en el carrito

    // Constructor
    public ProductoDTO(int idProducto, String nombreProducto, Double precio, int cantidadDisponible, Integer cantidadxproducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible; //cantidad total disponible
        this.cantidadxproducto = cantidadxproducto; // cantidad del producto en la orden
    }

    // Getters and Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
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

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    public Integer getCantidadxproducto(){return cantidadxproducto;}
    public void setCantidadxproducto(Integer cantidadxproducto){this.cantidadxproducto=cantidadxproducto;}
}

