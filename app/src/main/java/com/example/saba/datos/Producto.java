package com.example.saba.datos;

public class Producto {
    private int id_Product;
    private String nombre;
    private int stock;
    private double precio;
    private Categoria idCategoria;
    private int Estado;

    public Producto() {
    }

    public Producto(int id_Product, String nombre, int stock, double precio, Categoria idCategoria, int Estado) {
        this.id_Product = id_Product;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.Estado = Estado;
    }

    public int getId_Product() {
        return id_Product;
    }

    public void setId_Product(int id_Product) {
        this.id_Product = id_Product;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }
}
