package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetVentaDaoImp implements DetVentaDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private DetVenta objDt;

    public DetVentaDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objDt = (DetVenta) object;
        int idVenta = 0;
        try {
            String sql1 = "SELECT MAX(Id_Venta) AS id FROM Venta";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            while (rs.next()){
                idVenta = rs.getInt(1);
            }

            String sql = "INSERT INTO Detalle_Venta (Cantidad, SubTotal, Descuento, Id_ProdZar, Id_Venta)"
                    + " VALUES (" + objDt.getCantidad() + ", "
                    + objDt.getSubTotal() + ", "
                    + objDt.getDescuento() + ", "
                    + objDt.getIdProdZar().getIdZarandear() + ", "
                    + idVenta + ") ";
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
        int idVemta = (int) object;
        try {
            String sql = "delete from Detalle_Venta WHERE Id_Venta = " + idVemta;
            pst = con.prepareStatement(sql);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean modificar(Object object) throws SQLException {
        objDt = (DetVenta) object;
        try {
            String sql = "INSERT INTO Detalle_Venta (Cantidad, SubTotal, Descuento, Id_ProdZar, Id_Venta)"
                    + " VALUES (" + objDt.getCantidad() + ", "
                    + objDt.getSubTotal() + ", "
                    + objDt.getDescuento() + ", "
                    + objDt.getIdProdZar().getIdZarandear() + ", "
                    + objDt.getIdVenta().getId_Venta() + ") ";
            pst = con.prepareStatement(sql);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public ArrayList obtenerListaDetVenta(int IdVenta) throws SQLException {
        ArrayList<DetVenta> ltsDetVenta = new ArrayList();
        String sql = "SELECT * FROM Detalle_Venta WHERE Id_Venta = " + IdVenta;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objDt = new DetVenta();
                objDt.setId_DetVenta(rs.getInt(1));
                objDt.setCantidad(rs.getInt(2));
                objDt.setSubTotal(rs.getDouble(3));
                objDt.setDescuento(rs.getInt(4));

                //Obtenemos objeto Producto Zarandeado
                ZaranderDao objP = new ZaranderDaoImp(con);
                objDt.setIdProdZar(objP.buscarzarander(rs.getInt(5)));

                //Obtenemos objeto Venta
                VentaDao objV = new VentaDaoImp(con);
                objDt.setIdVenta(objV.buscarVenta(rs.getInt(6)));

                ltsDetVenta.add(objDt);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsDetVenta;
    }
}
