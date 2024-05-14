package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DetCierreDaoImp implements DetCierreDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private DetCierre objC;

    public DetCierreDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objC = (DetCierre) object;
        try {
            String sql = "INSERT INTO Detalle_Cierre(Id_Caja, Id_Cierre)"
                    + " VALUES ("
                    + objC.getId_Caja().getIdCaja() + ", "
                    + objC.getId_Cierra() + ") ";
            pst = con.prepareStatement(sql);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean eliminar(Object object) throws SQLException {
        return false;
    }

    @Override
    public boolean modificar(Object object) throws SQLException {
        return false;
    }
}
