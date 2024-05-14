package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClienteDao extends GeneralDao{
    public Clientes buscar_cli_dni(String nro) throws SQLException;
    public Clientes buscar_cli_razon(String razon) throws SQLException;
    public Clientes buscar_cli_id(int id) throws SQLException;
    public ArrayList lista_cliente() throws SQLException;
}
