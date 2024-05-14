package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CajaDao extends GeneralDao{
    public ArrayList obtenerListaCaja() throws SQLException;
    public boolean buscarVenta (int idVenta) throws SQLException;
    public boolean grabarSinComp(Object object) throws SQLException;
    public boolean validarAbrirCaja() throws SQLException;
}
