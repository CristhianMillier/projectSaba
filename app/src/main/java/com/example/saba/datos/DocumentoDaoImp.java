package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DocumentoDaoImp implements DocumentoDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Documento objD;

    public DocumentoDaoImp(Connection con) {
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
    public Documento buscar_doc_id(int id) throws SQLException {
        objD = new Documento();
        String sql = "SELECT * FROM Tipo_Documento WHERE Id_Dcoumento = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objD.setIdDocument(rs.getInt(1));
                objD.setNombre(rs.getString(2));
                objD.setEstado(rs.getInt(3));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objD;
    }

    @Override
    public ArrayList lista_documento() throws SQLException {
        ArrayList<Documento> ltsDocumento = new ArrayList();
        String sql = "SELECT * FROM Tipo_Documento WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objD = new Documento();
                objD.setIdDocument(rs.getInt(1));
                objD.setNombre(rs.getString(2));
                objD.setEstado(rs.getInt(3));

                ltsDocumento.add(objD);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsDocumento;
    }
}
