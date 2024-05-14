package com.example.saba.datos;

public class Documento {
    private int idDocument;
    private String nombre;
    private int estado;

    public Documento() {
    }

    public Documento(int idDocument, String nombre, int estado) {
        this.idDocument = idDocument;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
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
