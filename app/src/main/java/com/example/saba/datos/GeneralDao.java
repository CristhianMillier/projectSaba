package com.example.saba.datos;

import java.sql.SQLException;

public interface GeneralDao {
    public boolean grabar(Object object) throws SQLException;
    public boolean eliminar(Object object) throws SQLException;
    public boolean modificar(Object object) throws SQLException;
}
