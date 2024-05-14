package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReportesDao {
    public ArrayList<String[]> ClienteAño(String fecha, String cliente) throws SQLException;
    public ArrayList<String[]> CalidadVenta(String calidad) throws SQLException;
    public ArrayList<String[]> VentaAñoMes(int anio, int mes) throws SQLException;

    public ArrayList<String[]> ProveedorAño(String nombre, String apellido, String fecha) throws SQLException;
    public ArrayList<String[]> CompraAñoMes(int anio, int mes) throws SQLException;
    public ArrayList<String[]> CategoriaCompra(String categoria) throws SQLException;

    public ArrayList<String[]> CajaAñoa(String fecha, String cliente) throws SQLException;
    public ArrayList<String[]> Rotacion(String fechaI, String fechaF) throws SQLException;
    public ArrayList<String[]> CajaAñoMes(int anio, int mes) throws SQLException;
}
