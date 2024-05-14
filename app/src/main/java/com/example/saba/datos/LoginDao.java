package com.example.saba.datos;

import java.sql.SQLException;

public interface  LoginDao extends GeneralDao{
    public Login validadUsuario(String user, String clave) throws SQLException;
    public Login buscarId(int id) throws  SQLException;
    public boolean buscarLogin(String user) throws  SQLException;
}
