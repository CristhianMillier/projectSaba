package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProveedorDaoImp implements ProveedorDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Proveedores objP;

    public ProveedorDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objP = (Proveedores) object;
        try {
            String sql = "insert into Proveedor (Nombre, Apellido, Telefono, Direccion, Id_Documento, nroDocumento, Estado)"
                    + " VALUES ('" + objP.getNombre() + "', '"
                    + objP.getApellido() + "', "
                    + objP.getTelef() + ", '"
                    + objP.getDireccion() + "', "
                    + objP.getIdDocumento().getIdDocument() + ", '"
                    + objP.getNroDoc() + "', "
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
        objP = (Proveedores) object;
        try {
            String sql = "UPDATE Proveedor SET Estado=? WHERE Id_Proveedor = " + objP.getIdProveedor();
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
        objP = (Proveedores) object;
        try {
            String sql = "UPDATE Proveedor SET Nombre=?, Apellido=?, Telefono=?, Direccion=?, Id_Documento=?, nroDocumento=?, "
                    + "Estado=? WHERE Id_Proveedor = " + objP.getIdProveedor();
            pst = con.prepareStatement(sql);
            pst.setString(1, objP.getNombre());
            pst.setString(2, objP.getApellido());
            pst.setInt(3, objP.getTelef());
            pst.setString(4, objP.getDireccion());
            pst.setInt(5, objP.getIdDocumento().getIdDocument());
            pst.setString(6, objP.getNroDoc());
            pst.setInt(7, objP.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Proveedores buscar_prov_dni(String nro) throws SQLException {
        objP = null;
        String sql = "SELECT * FROM Proveedor WHERE nroDocumento ='" + nro + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP = new Proveedores();
                objP.setIdProveedor(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setApellido(rs.getString(3));
                objP.setTelef(rs.getInt(4));
                objP.setDireccion(rs.getString(5));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objP.setIdDocumento(objD.buscar_doc_id(rs.getInt(6)));

                objP.setNroDoc(rs.getString(7));
                objP.setEstado(rs.getInt(8));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objP;
    }

    @Override
    public Proveedores buscar_prov_nombre(String nombre) throws SQLException {
        objP = null;
        String sql = "SELECT * FROM Proveedor WHERE Nombre ='" + nombre + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP = new Proveedores();
                objP.setIdProveedor(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setApellido(rs.getString(3));
                objP.setTelef(rs.getInt(4));
                objP.setDireccion(rs.getString(5));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objP.setIdDocumento(objD.buscar_doc_id(rs.getInt(6)));

                objP.setNroDoc(rs.getString(7));
                objP.setEstado(rs.getInt(8));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objP;
    }

    @Override
    public Proveedores buscar_prov_id(int id) throws SQLException {
        objP = new Proveedores();
        String sql = "SELECT * FROM Proveedor WHERE Id_Proveedor =" + id ;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP.setIdProveedor(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setApellido(rs.getString(3));
                objP.setTelef(rs.getInt(4));
                objP.setDireccion(rs.getString(5));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objP.setIdDocumento(objD.buscar_doc_id(rs.getInt(6)));

                objP.setNroDoc(rs.getString(7));
                objP.setEstado(rs.getInt(8));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objP;
    }

    @Override
    public ArrayList lista_Proveedores() throws SQLException {
        ArrayList<Proveedores> ltsProveedor = new ArrayList();
        String sql = "SELECT * FROM Proveedor WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objP = new Proveedores();
                objP.setIdProveedor(rs.getInt(1));
                objP.setNombre(rs.getString(2));
                objP.setApellido(rs.getString(3));
                objP.setTelef(rs.getInt(4));
                objP.setDireccion(rs.getString(5));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objP.setIdDocumento(objD.buscar_doc_id(rs.getInt(6)));

                objP.setNroDoc(rs.getString(7));
                objP.setEstado(rs.getInt(8));
                ltsProveedor.add(objP);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsProveedor;
    }
}
