package com.example.saba.datos;

public class CierreCaja {
    private int idCierre;
    private String fecha_abre;
    private String fecha_cierre;
    private double dinero;
    private Empleado idEmpleado;

    public CierreCaja() {
    }

    public CierreCaja(int idCierre, String fecha_abre, String fecha_cierre, double dinero, Empleado idEmpleado) {
        this.idCierre = idCierre;
        this.fecha_abre = fecha_abre;
        this.fecha_cierre = fecha_cierre;
        this.dinero = dinero;
        this.idEmpleado = idEmpleado;
    }

    public int getIdCierre() {
        return idCierre;
    }

    public void setIdCierre(int idCierre) {
        this.idCierre = idCierre;
    }

    public String getFecha_abre() {
        return fecha_abre;
    }

    public void setFecha_abre(String fecha_abre) {
        this.fecha_abre = fecha_abre;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
