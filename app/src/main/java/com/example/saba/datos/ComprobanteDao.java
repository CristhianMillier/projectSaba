package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComprobanteDao extends GeneralDao{
    public Comprobante buscar_comp_id(int id) throws SQLException;
    public ArrayList lista_Comprobante() throws SQLException;
}
