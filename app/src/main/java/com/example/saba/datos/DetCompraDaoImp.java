package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetCompraDaoImp implements DetCompraDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private DetCompra objDt;

    public DetCompraDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objDt = (DetCompra) object;
        int idCompra = 0;
        try {
            String sql1 = "SELECT MAX(Id_Compra) AS id FROM Compra";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            while (rs.next()){
                idCompra = rs.getInt(1);
            }

            String sql = "INSERT INTO Detalle_Compra (Precio, Cantidad, Id_Producto, Id_Compra)"
                    + " VALUES (" + objDt.getPrecio() + ", "
                    + objDt.getCantidad() + ", "
                    + objDt.getId_Producto().getId_Product() + ", "
                    + idCompra + ") ";
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
        int idCompra = (int) object;
        try {
            String sql = "delete from Detalle_Compra WHERE Id_Compra = " + idCompra;
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
        objDt = (DetCompra) object;
        try {
            String sql = "UPDATE Detalle_Compra SET Precio=?, Cantidad=?, Id_Producto=?, Id_Compra=? WHERE Id_DetCompra = " + objDt.getId_DetCompr();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, objDt.getPrecio());
            pst.setInt(2, objDt.getCantidad());
            pst.setInt(3, objDt.getId_Producto().getId_Product());
            pst.setInt(4, objDt.getId_Compra().getIdCompra());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public ArrayList obtenerListaDetCompra(int idCompra) throws SQLException {
        ArrayList<DetCompra> ltsDetCompra = new ArrayList();
        String sql = "SELECT * FROM Detalle_Compra WHERE Id_Compra = " + idCompra;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objDt = new DetCompra();
                objDt.setId_DetCompr(rs.getInt(1));
                objDt.setPrecio(rs.getDouble(2));
                objDt.setCantidad(rs.getInt(3));

                //Obtenemos objeto Producto
                ProductoDao objP = new ProductoDaoImp(con);
                objDt.setId_Producto(objP.buscar_prod_id(rs.getInt(4)));

                //Obtenemos objeto Compra
                CompraDao objC = new CompraDaoImp(con);
                objDt.setId_Compra(objC.buscar_Id(rs.getInt(5)));

                ltsDetCompra.add(objDt);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsDetCompra;
    }
}
