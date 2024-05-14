package com.example.saba.datos;

public class Ventas {
    private int id_Venta;
    private String fecha;
    private double pagar;
    private Clientes idCliente;
    private Empleado idEmpleado;
    private int estado;

    public Ventas(int id_Venta, String fecha, double pagar, Clientes idCliente, Empleado idEmpleado, int estado) {
        this.id_Venta = id_Venta;
        this.fecha = fecha;
        this.pagar = pagar;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.estado = estado;
    }

    public Ventas() {
    }

    public int getId_Venta() {
        return id_Venta;
    }

    public void setId_Venta(int id_Venta) {
        this.id_Venta = id_Venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPagar() {
        return pagar;
    }

    public void setPagar(double pagar) {
        this.pagar = pagar;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
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
