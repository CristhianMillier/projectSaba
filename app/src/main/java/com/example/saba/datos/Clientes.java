package com.example.saba.datos;

public class Clientes {
    private int idCliente;
    private String razonSocial;
    private int telefefono;
    private String direccion;
    private Documento idDoc;
    private String nroDoc;
    private int Estado;

    public Clientes() {
    }

    public Clientes(int idCliente, String razonSocial, int telefefono, String direccion, Documento idDoc, String nroDoc, int Estado) {
        this.idCliente = idCliente;
        this.razonSocial = razonSocial;
        this.telefefono = telefefono;
        this.direccion = direccion;
        this.idDoc = idDoc;
        this.nroDoc = nroDoc;
        this.Estado = Estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getTelefefono() {
        return telefefono;
    }

    public void setTelefefono(int telefefono) {
        this.telefefono = telefefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Documento getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(Documento idDoc) {
        this.idDoc = idDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }
}
