package com.example.saba.datos;

public class DetCompra {
    private int id_DetCompr;
    private int cantidad;
    private double precio;
    private Producto id_Producto;
    private Compras id_Compra;

    public DetCompra() {
    }

    public DetCompra(int id_DetCompr, int cantidad, double precio, Producto id_Producto, Compras id_Compra) {
        this.id_DetCompr = id_DetCompr;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_Producto = id_Producto;
        this.id_Compra = id_Compra;
    }

    public int getId_DetCompr() {
        return id_DetCompr;
    }

    public void setId_DetCompr(int id_DetCompr) {
        this.id_DetCompr = id_DetCompr;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Producto getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(Producto id_Producto) {
        this.id_Producto = id_Producto;
    }

    public Compras getId_Compra() {
        return id_Compra;
    }

    public void setId_Compra(Compras id_Compra) {
        this.id_Compra = id_Compra;
    }
}
