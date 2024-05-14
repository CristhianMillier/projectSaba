package com.example.saba.datos;

public class Cajas {
    private int idCaja;
    private String serieComprobante;
    private String numComprobante;
    private String fecha;
    private double pago;
    private Ventas idVenta;
    private Comprobante idComprobante;
    private int Estado;
    private int idEmpleado;

    public Cajas() {
    }

    public Cajas(int idCaja, String serieComprobante, String numComprobante, String fecha, double pago, Ventas idVenta, Comprobante idComprobante, int estado, int idEmpleado) {
        this.idCaja = idCaja;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.pago = pago;
        this.idVenta = idVenta;
        this.idComprobante = idComprobante;
        this.Estado = estado;
        this.idEmpleado = idEmpleado;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public Ventas getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Ventas idVenta) {
        this.idVenta = idVenta;
    }

    public Comprobante getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Comprobante idComprobante) {
        this.idComprobante = idComprobante;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
