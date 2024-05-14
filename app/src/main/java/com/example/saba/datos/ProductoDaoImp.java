package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoDaoImp implements ProductoDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Producto objP;

    public ProductoDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objP = (Producto) object;
        try {
            String sql = "INSERT INTO Producto (Nombre, Stock, Precio, Id_Categoria, Estado)"
                    + " VALUES ('" + objP.getNombre() + "', "
                    + objP.getStock() + ", "
                    + objP.getPrecio() + ", "
                    + objP.getIdCategoria().getIdCategoria() + ", "
                    + objP.getEstado() + ") ";
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
        objP = (Producto) object;
        try {
            String sql = "UPDATE Producto SET Estado=? WHERE Id_Producto = " + objP.getId_Product();
            pst = con.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean modificar(Object object) throws SQLException {
        objP = (Producto) object;
        try {
            String sql = "UPDATE Producto SET Nombre=?, Stock=?, Precio=?, Id_Categoria=?, Estado=?"
                    + " WHERE Id_Producto = " + objP.getId_Product();
            pst = con.prepareStatement(sql);
            pst.setString(1, objP.getNombre());
            pst.setInt(2, objP.getStock());
            pst.setDouble(3, objP.getPrecio());
            pst.setInt(4, objP.getIdCategoria().getIdCategoria());
            pst.setInt(5, objP.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Producto buscar_prod_id(int id) throws SQLException {
        objP = null;
        String sql = "SELECT * FROM Producto WHERE Id_Producto = " + id ;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP = new Producto();
                objP.setId_Product(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setStock(rs.getInt(3));
                objP.setPrecio(rs.getDouble(4));

                //buscamos categoria
                CategoriaDao objC = new CategoriaDaoImp(con);
                objP.setIdCategoria(objC.buscar_cat_id(rs.getInt(5)));

                objP.setEstado(rs.getInt(6));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objP;
    }

    @Override
    public ArrayList<Producto> lista_Producto() throws SQLException {
        ArrayList<Producto> ltsProducto = new ArrayList();
        String sql = "SELECT * FROM Producto WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP = new Producto();
                objP.setId_Product(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setStock(rs.getInt(3));
                objP.setPrecio(rs.getDouble(4));

                //buscamos categoria
                CategoriaDao objC = new CategoriaDaoImp(con);
                objP.setIdCategoria(objC.buscar_cat_id(rs.getInt(5)));

                objP.setEstado(rs.getInt(6));
                ltsProducto.add(objP);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsProducto;
    }

    @Override
    public boolean agregarCompra(Producto objP, int cantidad, double precio) throws SQLException {
        int stock = 0;
        try {
            String modificar = "SELECT Stock FROM Producto where Id_Producto = " + objP.getId_Product();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(modificar);
            while (rs.next()) {
                stock = rs.getInt(1);
            }
            stock = stock + cantidad;
            String sql = "UPDATE  Producto SET Precio=?, Stock=? WHERE Id_Producto = " + objP.getId_Product();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, precio);
            pst.setInt(2, stock);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean actualizarCompra(Producto objP, int cantidad, double precio) throws SQLException {
        int stock = 0;
        double monto = 0.0;
        try {
            String modificar = "SELECT Stock, Precio FROM Producto where Id_Producto = " + objP.getId_Product();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(modificar);
            while (rs.next()) {
                stock = rs.getInt(1);
                monto = rs.getDouble(2);
            }
            stock = stock + cantidad;
            monto = monto + precio;
            String sql = "UPDATE  Producto SET Precio=?, Stock=? WHERE Id_Producto = " + objP.getId_Product();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, monto);
            pst.setInt(2, stock);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean agregarZarandeo(Producto objP, int cantidad) throws SQLException {
        try {
            String sql = "UPDATE  Producto SET Stock=? WHERE Id_Producto = " + objP.getId_Product();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, cantidad);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }
}
