package com.example.saba.datos;

public class DetVenta {
    private int id_DetVenta;
    private int cantidad;
    private double subTotal;
    private int descuento;
    private Zarander idProdZar;
    private Ventas idVenta;

    public DetVenta() {
    }

    public DetVenta(int id_DetVenta, int cantidad, double subTotal, int descuento, Zarander idProdZar, Ventas idVenta) {
        this.id_DetVenta = id_DetVenta;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.descuento = descuento;
        this.idProdZar = idProdZar;
        this.idVenta = idVenta;
    }

    public int getId_DetVenta() {
        return id_DetVenta;
    }

    public void setId_DetVenta(int id_DetVenta) {
        this.id_DetVenta = id_DetVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Zarander getIdProdZar() {
        return idProdZar;
    }

    public void setIdProdZar(Zarander idProdZar) {
        this.idProdZar = idProdZar;
    }

    public Ventas getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Ventas idVenta) {
        this.idVenta = idVenta;
    }
}
