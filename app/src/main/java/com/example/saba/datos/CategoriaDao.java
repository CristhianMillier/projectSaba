package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoriaDao extends  GeneralDao{
    public Categoria buscar_cat_id(int id) throws SQLException;
    public ArrayList lista_Categoria() throws SQLException;
}
