package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CierreCajaDaoImp implements CierreCajaDAo{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private CierreCaja objC;

    public CierreCajaDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objC = (CierreCaja) object;
        try {
            String sql = "INSERT INTO Caja_Cierre (Fecha_abre, Fecha_cierre, Dinero, Id_Empleado)"
                    + " VALUES ('" + objC.getFecha_abre() + "', "
                    + objC.getFecha_cierre() + ", "
                    + 0 + ", "
                    + null + ") ";
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
        objC = (CierreCaja) object;
        try {
            String sql = "UPDATE Caja_Cierre SET Fecha_cierre=?, Dinero=?, Id_Empleado=? "
                    + " WHERE Id_Cierre = " + objC.getIdCierre();
            pst = con.prepareStatement(sql);
            pst.setString(1, objC.getFecha_cierre());
            pst.setDouble(2, objC.getDinero());
            pst.setInt(3, objC.getIdEmpleado().getIdEmpleado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public DetCierre buscarDetCierre(int idCaja) throws SQLException {
        DetCierre objCie = null;
        try {
            String sql = "select * FROM Detalle_Cierre where Id_Caja = " + idCaja;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objCie = new DetCierre();
                objCie.setId_DetCierre(rs.getInt(1));
                objCie.setId_Cierra(rs.getInt(2));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            throw e;
        }
        return objCie;
    }

    @Override
    public int buscarCierre() throws SQLException {
        int idCierre = 0;
        try {
            String sql = "select * from Caja_Cierre where Fecha_cierre is null";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idCierre = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return idCierre;
    }

    @Override
    public double cierreCaja() throws SQLException {
        double dinero = 0;
        try{
            String sql = "SELECT SUM(Pago) FROM Caja WHERE Cerrada = 0";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dinero = rs.getDouble(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return dinero;
    }

    @Override
    public void CajaCerrada(Object object) throws SQLException {
        Cajas caja = (Cajas) object;
        try {
            String sql = "UPDATE Caja SET Cerrada=? WHERE Id_Caja = " + caja.getIdCaja();
            pst = con.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
