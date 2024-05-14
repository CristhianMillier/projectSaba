package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DetVentaDao extends GeneralDao{
    public ArrayList obtenerListaDetVenta(int IdVenta) throws SQLException;
}
