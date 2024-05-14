package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ZaranderDaoImp implements ZaranderDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Zarander objZ;

    public ZaranderDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objZ = (Zarander) object;
        try {
            String sql = "insert into Producto_Zarandeado (Precio_Venta, Stock, Id_Tipo, Id_Producto, Estado)"
                    + " VALUES (" + objZ.getPrecio() + ", '"
                    + objZ.getStock() + "', "
                    + objZ.getIdTipo().getIdTipo() + ", "
                    + objZ.getIdProd().getId_Product() + ", "
                    + objZ.getEstado() + ") ";
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
        objZ = (Zarander) object;
        try {
            String sql = "UPDATE Producto_Zarandeado SET Precio_Venta=?, Stock=? WHERE Id_Producto = " + objZ.getIdProd().getId_Product();
            pst = con.prepareStatement(sql);
            pst.setInt(1, 0);
            pst.setInt(2, 0);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean modificar(Object object) throws SQLException {
        objZ = (Zarander) object;
        try {
            String sql = "UPDATE Producto_Zarandeado SET Precio_Venta=?, Stock=? WHERE Id_Producto = " + objZ.getIdProd().getId_Product()
                    + " and Id_Tipo = " + objZ.getIdTipo().getIdTipo();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, objZ.getPrecio());
            pst.setInt(2, objZ.getStock());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public ArrayList lista_ZarandeoCombo() throws SQLException {
        ArrayList<Zarander> ltsZarander = new ArrayList();
        String sql = "SELECT * FROM Producto_Zarandeado WHERE Estado = 0 and Stock > 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objZ = new Zarander();
                objZ.setIdZarandear(rs.getInt(1));
                objZ.setPrecio(rs.getDouble(2));
                objZ.setStock(rs.getInt(3));

                //buscamos tipo soya
                TipoSoyaDao objT = new TipoSoyaDaoImp(con);
                objZ.setIdTipo(objT.buscar_tipo_id(rs.getInt(4)));

                //buscamos producto
                ProductoDao objP = new ProductoDaoImp(con);
                objZ.setIdProd(objP.buscar_prod_id(rs.getInt(5)));


                objZ.setEstado(rs.getInt(6));
                ltsZarander.add(objZ);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  ltsZarander;
    }

    @Override
    public ArrayList lista_BuscaZarandeo(int idProd) throws SQLException {
        ArrayList<Zarander> ltsZarander = new ArrayList();
        String sql = "SELECT * FROM Producto_Zarandeado WHERE Estado = 0 and Id_Producto = " + idProd + " and Stock > 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objZ = new Zarander();
                objZ.setIdZarandear(rs.getInt(1));
                objZ.setPrecio(rs.getDouble(2));
                objZ.setStock(rs.getInt(3));

                //buscamos tipo soya
                TipoSoyaDao objT = new TipoSoyaDaoImp(con);
                objZ.setIdTipo(objT.buscar_tipo_id(rs.getInt(4)));

                //buscamos producto
                ProductoDao objP = new ProductoDaoImp(con);
                objZ.setIdProd(objP.buscar_prod_id(rs.getInt(5)));


                objZ.setEstado(rs.getInt(6));
                ltsZarander.add(objZ);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  ltsZarander;
    }

    @Override
    public Zarander buscarzarander(int idZarandeo) throws SQLException {
        objZ = new Zarander();
        String sql = "SELECT * FROM Producto_Zarandeado WHERE Id_Zarandeo = " + idZarandeo;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objZ.setIdZarandear(rs.getInt(1));
                objZ.setPrecio(rs.getDouble(2));
                objZ.setStock(rs.getInt(3));

                //buscamos tipo soya
                TipoSoyaDao objT = new TipoSoyaDaoImp(con);
                objZ.setIdTipo(objT.buscar_tipo_id(rs.getInt(4)));

                //buscamos producto
                ProductoDao objP = new ProductoDaoImp(con);
                objZ.setIdProd(objP.buscar_prod_id(rs.getInt(5)));


                objZ.setEstado(rs.getInt(6));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return objZ;
    }

    @Override
    public boolean actualizarVenta(Zarander objP, int cantidad) throws SQLException {
        int stock = 0;
        try {
            String modificar = "SELECT Stock FROM Producto_Zarandeado where Id_Zarandeo = " + objP.getIdZarandear();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(modificar);
            while (rs.next()) {
                stock = rs.getInt(1);
            }
            stock = stock + cantidad;
            String sql = "UPDATE Producto_Zarandeado SET Stock=? WHERE Id_Zarandeo = " + objP.getIdZarandear();
            pst = con.prepareStatement(sql);
            pst.setInt(1, stock);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }
}
