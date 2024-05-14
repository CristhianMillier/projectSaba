package com.example.saba.datos;

public class Comprobante {
    private int idComprobante;
    private String nombre;
    private int estado;

    public Comprobante() {
    }

    public Comprobante(int idComprobante, String nombre, int estado) {
        this.idComprobante = idComprobante;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(int idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
