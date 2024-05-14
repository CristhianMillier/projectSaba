package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TipoSoyaDaoImp implements TipoSoyaDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private TipoSoya objT;

    public TipoSoyaDaoImp(Connection con) {
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
    public TipoSoya buscar_tipo_id(int id) throws SQLException {
        objT = new TipoSoya();
        String sql = "SELECT * FROM Tipo_Soya WHERE Id_Tipo = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objT.setIdTipo(rs.getInt(1));
                objT.setNombre(rs.getString(2));
                objT.setEstado(rs.getInt(3));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objT;
    }

    @Override
    public ArrayList lista_TipoSoya() throws SQLException {
        ArrayList<TipoSoya> ltsTipo = new ArrayList();
        String sql = "SELECT * FROM Tipo_Soya WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objT = new TipoSoya();
                objT.setIdTipo(rs.getInt(1));
                objT.setNombre(rs.getString(2));
                objT.setEstado(rs.getInt(3));

                ltsTipo.add(objT);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsTipo;
    }
}
