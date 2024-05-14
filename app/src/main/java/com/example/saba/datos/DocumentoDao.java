package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DocumentoDao extends GeneralDao{
    public Documento buscar_doc_id(int id) throws SQLException;
    public ArrayList lista_documento() throws SQLException;
}
