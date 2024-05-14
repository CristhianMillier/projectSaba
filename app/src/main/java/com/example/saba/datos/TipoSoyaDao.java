package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TipoSoyaDao extends GeneralDao{
    public TipoSoya buscar_tipo_id(int id) throws SQLException;
    public ArrayList lista_TipoSoya() throws SQLException;
}
