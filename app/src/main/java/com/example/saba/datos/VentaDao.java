package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VentaDao extends GeneralDao{
    public ArrayList obtenerListaVenta() throws SQLException;
    public int numTicket() throws SQLException;
    public Ventas buscarVenta(int idVenta ) throws SQLException;
}
