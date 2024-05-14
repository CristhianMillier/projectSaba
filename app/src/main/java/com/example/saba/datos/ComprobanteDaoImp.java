package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ComprobanteDaoImp implements ComprobanteDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Comprobante objC;

    public ComprobanteDaoImp(Connection con) {
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
    public Comprobante buscar_comp_id(int id) throws SQLException {
        objC = new Comprobante();
        String sql = "SELECT * FROM Tipo_Comprobante WHERE Id_Comprobante = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC.setIdComprobante(rs.getInt(1));
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
    public ArrayList lista_Comprobante() throws SQLException {
        ArrayList<Comprobante> ltsComprobante = new ArrayList();
        String sql = "SELECT * FROM Tipo_Comprobante WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Comprobante();
                objC.setIdComprobante(rs.getInt(1));
                objC.setNombre(rs.getString(2));
                objC.setEstado(rs.getInt(3));
                ltsComprobante.add(objC);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsComprobante;
    }
}
