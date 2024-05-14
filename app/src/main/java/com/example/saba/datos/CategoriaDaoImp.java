package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaDaoImp implements CategoriaDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Categoria objC;

    public CategoriaDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        return false;
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
    public Categoria buscar_cat_id(int id) throws SQLException {
        objC = new Categoria();
        String sql = "SELECT * FROM Categoria WHERE Id_Categoria = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC.setIdCategoria(rs.getInt(1));
                objC.setNombre(rs.getString(2));
                objC.setEstado(rs.getInt(3));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public ArrayList lista_Categoria() throws SQLException {
        ArrayList<Categoria> ltsCategoria = new ArrayList();
        String sql = "SELECT * FROM Categoria WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Categoria();
                objC.setIdCategoria(rs.getInt(1));
                objC.setNombre(rs.getString(2));
                objC.setEstado(rs.getInt(3));
                ltsCategoria.add(objC);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsCategoria;
    }
}
