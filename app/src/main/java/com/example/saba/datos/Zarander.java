package com.example.saba.datos;

public class Zarander {
    private int idZarandear;
    private double precio;
    private int stock;
    private TipoSoya idTipo;
    private Producto idProd;
    private int estado;

    public Zarander() {
    }

    public Zarander(int idZarandear, double precio, int stock, TipoSoya idTipo, Producto idProd, int estado) {
        this.idZarandear = idZarandear;
        this.precio = precio;
        this.stock = stock;
        this.idTipo = idTipo;
        this.idProd = idProd;
        this.estado = estado;
    }

    public int getIdZarandear() {
        return idZarandear;
    }

    public void setIdZarandear(int idZarandear) {
        this.idZarandear = idZarandear;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public TipoSoya getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoSoya idTipo) {
        this.idTipo = idTipo;
    }

    public Producto getIdProd() {
        return idProd;
    }

    public void setIdProd(Producto idProd) {
        this.idProd = idProd;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
