package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ZaranderDao extends GeneralDao{
    public ArrayList lista_ZarandeoCombo() throws SQLException;
    public ArrayList lista_BuscaZarandeo(int idProd) throws SQLException;
    public Zarander buscarzarander(int idZarandeo) throws SQLException;
    public boolean actualizarVenta(Zarander objP, int cantidad) throws SQLException;
}
