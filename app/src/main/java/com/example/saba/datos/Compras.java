package com.example.saba.datos;

public class Compras {
    private int idCompra;
    private double pago;
    private String fecha;
    private Proveedores idProvee;
    private Empleado idEmpleado;
    private int estado;

    public Compras() {
    }

    public Compras(int idCompra, double pago, String fecha, Proveedores idProvee, Empleado idEmpleado, int estado) {
        this.idCompra = idCompra;
        this.pago = pago;
        this.fecha = fecha;
        this.idProvee = idProvee;
        this.idEmpleado = idEmpleado;
        this.estado = estado;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Proveedores getIdProvee() {
        return idProvee;
    }

    public void setIdProvee(Proveedores idProvee) {
        this.idProvee = idProvee;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
