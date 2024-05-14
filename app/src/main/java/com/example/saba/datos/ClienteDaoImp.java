package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteDaoImp implements ClienteDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Clientes objC;

    public ClienteDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objC = (Clientes) object;
        try {
            String sql = "insert into Cliente (Razon_Social, Telefono, Direccion, Id_Documento, nroDocumento, Estado)"
                    + " VALUES ('" + objC.getRazonSocial() + "', "
                    + objC.getTelefefono() + ", '"
                    + objC.getDireccion() + "', "
                    + objC.getIdDoc().getIdDocument() + ", '"
                    + objC.getNroDoc() + "', "
                    + objC.getEstado() + ") ";
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
        objC = (Clientes) object;
        try {
            String sql = "UPDATE Cliente SET Estado=? WHERE Id_Cliente = " + objC.getIdCliente();
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
        objC = (Clientes) object;
        try {
            String sql = "UPDATE Cliente SET Razon_Social=?, Telefono=?, Direccion=?, Id_Documento=?, nroDocumento=?, "
                    + "Estado=? WHERE Id_Cliente = " + objC.getIdCliente();
            pst = con.prepareStatement(sql);
            pst.setString(1, objC.getRazonSocial());
            pst.setInt(2, objC.getTelefefono());
            pst.setString(3, objC.getDireccion());
            pst.setInt(4, objC.getIdDoc().getIdDocument());
            pst.setString(5, objC.getNroDoc());
            pst.setInt(6, objC.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Clientes buscar_cli_dni(String nro) throws SQLException {
        objC = null;
        String sql = "SELECT * FROM Cliente WHERE nroDocumento ='" + nro + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Clientes();
                objC.setIdCliente(rs.getInt(1));
                objC.setRazonSocial(rs.getString(2));
                objC.setTelefefono(rs.getInt(3));
                objC.setDireccion(rs.getString(4));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objC.setIdDoc(objD.buscar_doc_id(rs.getInt(5)));

                objC.setNroDoc(rs.getString(6));
                objC.setEstado(rs.getInt(7));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public Clientes buscar_cli_razon(String razon) throws SQLException {
        objC = null;
        String sql = "SELECT * FROM Cliente WHERE Razon_Social ='" + razon + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Clientes();
                objC.setIdCliente(rs.getInt(1));
                objC.setRazonSocial(rs.getString(2));
                objC.setTelefefono(rs.getInt(3));
                objC.setDireccion(rs.getString(4));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objC.setIdDoc(objD.buscar_doc_id(rs.getInt(5)));

                objC.setNroDoc(rs.getString(6));
                objC.setEstado(rs.getInt(7));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public Clientes buscar_cli_id(int id) throws SQLException {
        objC = new Clientes();
        String sql = "SELECT * FROM Cliente WHERE Id_Cliente = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC.setIdCliente(rs.getInt(1));
                objC.setRazonSocial(rs.getString(2));
                objC.setTelefefono(rs.getInt(3));
                objC.setDireccion(rs.getString(4));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objC.setIdDoc(objD.buscar_doc_id(rs.getInt(5)));

                objC.setNroDoc(rs.getString(6));
                objC.setEstado(rs.getInt(7));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public ArrayList lista_cliente() throws SQLException {
        ArrayList<Clientes> ltsCliente = new ArrayList();
        String sql = "SELECT * FROM Cliente WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Clientes();
                objC.setIdCliente(rs.getInt(1));
                objC.setRazonSocial(rs.getString(2));
                objC.setTelefefono(rs.getInt(3));
                objC.setDireccion(rs.getString(4));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objC.setIdDoc(objD.buscar_doc_id(rs.getInt(5)));

                objC.setNroDoc(rs.getString(6));
                objC.setEstado(rs.getInt(7));
                ltsCliente.add(objC);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsCliente;
    }
}
