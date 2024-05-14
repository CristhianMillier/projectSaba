package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProveedorDao extends GeneralDao{
    public Proveedores buscar_prov_dni(String nro) throws SQLException;
    public Proveedores buscar_prov_nombre(String nombre) throws SQLException;
    public Proveedores buscar_prov_id(int id) throws SQLException;
    public ArrayList lista_Proveedores() throws SQLException;
}
