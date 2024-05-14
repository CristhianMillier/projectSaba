package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductoDao extends GeneralDao{
    public Producto buscar_prod_id(int id) throws SQLException;
    public ArrayList<Producto> lista_Producto() throws SQLException;
    public boolean agregarCompra(Producto objP, int cantidad, double precio) throws SQLException;
    public boolean actualizarCompra(Producto objP, int cantidad, double precio) throws SQLException;
    public boolean agregarZarandeo(Producto objP, int cantidad) throws SQLException;
}
