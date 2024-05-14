package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CajaDaoImp implements CajaDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Cajas objC;

    public CajaDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objC = (Cajas) object;
        try {
            String sql = "insert into Caja (Serie_Comprobante, Num_Comprobante, Fecha, Pago, Id_Venta, Id_Comprobante, Estado, idEmpleado)"
                    + " VALUES ('" + objC.getSerieComprobante() + "', '"
                    + objC.getNumComprobante()+ "', '"
                    + objC.getFecha() + "', "
                    + objC.getPago() + ", "
                    + objC.getIdVenta().getId_Venta() + ", "
                    + objC.getIdComprobante().getIdComprobante() + ", "
                    + objC.getEstado() + ", "
                    + objC.getIdEmpleado() + ") ";
            pst = con.prepareStatement(sql);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean grabarSinComp(Object object) throws SQLException {
        objC = (Cajas) object;
        try {
            String sql = "insert into Caja (Serie_Comprobante, Num_Comprobante, Fecha, Pago, Id_Venta, Id_Comprobante, Estado, idEmpleado)"
                    + " VALUES (" + null + ", "
                    + null + ", '"
                    + objC.getFecha() + "', "
                    + objC.getPago() + ", "
                    + objC.getIdVenta().getId_Venta() + ", "
                    + null + ", "
                    + objC.getEstado() + ", "
                    + objC.getIdEmpleado() + ") ";
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

    @Override
    public ArrayList obtenerListaCaja() throws SQLException {
        ArrayList<Cajas> ltsCaja = new ArrayList();
        String sql = "SELECT * FROM Caja WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Cajas();
                objC.setIdCaja(rs.getInt(1));
                objC.setFecha(rs.getString(4));
                ltsCaja.add(objC);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsCaja;
    }

    @Override
    public boolean buscarVenta(int idVenta) throws SQLException {
        boolean estado = true;
        try {
            String sql = "select * from Caja where Id_Venta = " + idVenta;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                estado = false;
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return estado;
    }

    @Override
    public boolean validarAbrirCaja() throws SQLException {
        boolean estado = true;
        try {
            String sql = "select * from Caja_Cierre where Fecha_cierre is null";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                estado = false;
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return estado;
    }
}
