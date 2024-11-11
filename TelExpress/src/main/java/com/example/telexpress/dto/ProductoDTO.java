package com.example.telexpress.dto;

public class ProductoDTO {
    private Integer idProducto;
    private String nombreProducto;
    private Double precio;
    private Integer cantidadDisponible;
    private Integer cantidadxproducto; // cantidad agregada en el carrito

    // Constructor
    public ProductoDTO(Integer idProducto, String nombreProducto, Double precio, Integer cantidadDisponible, Integer cantidadxproducto) {
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

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    public Integer getCantidadxproducto(){return cantidadxproducto;}
    public void setCantidadxproducto(Integer cantidadxproducto){this.cantidadxproducto=cantidadxproducto;}
//POR AHORA NO ESTÁ EN USO, PERO NO SE BORRA POR PRECAUCIÓN
}

