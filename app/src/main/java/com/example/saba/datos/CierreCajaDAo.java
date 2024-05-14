package com.example.saba.datos;

import java.sql.SQLException;

public interface CierreCajaDAo extends GeneralDao{
    public DetCierre buscarDetCierre(int idCaja) throws SQLException;
    public int buscarCierre() throws SQLException;
    public double cierreCaja() throws SQLException;
    public void CajaCerrada(Object object) throws SQLException;
}
