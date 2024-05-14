package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DetCompraDao extends GeneralDao{
    public ArrayList obtenerListaDetCompra(int idCompra) throws SQLException;
}
