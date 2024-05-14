package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompraDao extends GeneralDao{
    public Compras buscar_Id(int id) throws SQLException;
    public Compras buscar_Fecha(String fecha, int id) throws SQLException;
    public ArrayList lista_Compras() throws SQLException;
}
