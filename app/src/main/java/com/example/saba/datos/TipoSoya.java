package com.example.saba.datos;

public class TipoSoya {
    private int idTipo;
    private String nombre;
    private int estado;

    public TipoSoya() {
    }

    public TipoSoya(int idTipo, String nombre, int estado) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
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
