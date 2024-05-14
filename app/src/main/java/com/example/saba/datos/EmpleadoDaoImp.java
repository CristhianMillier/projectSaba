package com.example.saba.datos;

import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDaoImp implements EmpleadoDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Empleado objE;

    public EmpleadoDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objE = (Empleado) object;
        try {
            String sql = "insert into Empleado (Nombre, Apellido, Telefono, Dreccion, Cargo, Id_Documento, nroDocumento, Estado)"
                    + " VALUES ('" + objE.getNombre() + "', '"
                    + objE.getApellido() + "', "
                    + objE.getTelefono() + ", '"
                    + objE.getDireccion() + "', '"
                    + objE.getCargo() + "', "
                    + objE.getIdDocumento().getIdDocument() + ", '"
                    + objE.getNroDocumento() + "', "
                    + objE.getEstado() + ") ";
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
        objE = (Empleado) object;
        try {
            String sql = "UPDATE Empleado SET Estado=? WHERE Id_Empleado = " + objE.getIdEmpleado();
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
        objE = (Empleado) object;
        try {
            String sql = "UPDATE Empleado SET Nombre=?, Apellido=?, Telefono=?, Dreccion=?, Cargo=?, Id_Documento=?, nroDocumento=?,"
                    + "Estado=? WHERE Id_Empleado = " + objE.getIdEmpleado();
            pst = con.prepareStatement(sql);
            pst.setString(1, objE.getNombre());
            pst.setString(2, objE.getApellido());
            pst.setInt(3, objE.getTelefono());
            pst.setString(4, objE.getDireccion());
            pst.setString(5, objE.getCargo());
            pst.setInt(6, objE.getIdDocumento().getIdDocument());
            pst.setString(7, objE.getNroDocumento());
            pst.setInt(8, objE.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Empleado buscar_emp_dni(String nro) throws SQLException {
        objE = new Empleado();
        String sql = "SELECT * FROM Empleado WHERE nroDocumento ='" + nro + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objE.setIdEmpleado(rs.getInt(1));
                objE.setNombre(rs.getString(2));
                objE.setApellido(rs.getString(3));
                objE.setTelefono(rs.getInt(4));
                objE.setDireccion(rs.getString(5));
                objE.setCargo(rs.getString(6));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objE.setIdDocumento(objD.buscar_doc_id(rs.getInt(7)));

                objE.setNroDocumento(rs.getString(8));
                objE.setEstado(rs.getInt(9));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objE;
    }

    @Override
    public Empleado buscar_em_id(int id) throws SQLException {
        objE = new Empleado();
        String sql = "SELECT * FROM Empleado WHERE Id_Empleado =" + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objE.setIdEmpleado(rs.getInt(1));
                objE.setNombre(rs.getString(2));
                objE.setApellido(rs.getString(3));
                objE.setTelefono(rs.getInt(4));
                objE.setDireccion(rs.getString(5));
                objE.setCargo(rs.getString(6));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                objE.setIdDocumento(objD.buscar_doc_id(rs.getInt(7)));

                objE.setNroDocumento(rs.getString(8));
                objE.setEstado(rs.getInt(9));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objE;
    }

    @Override
    public ArrayList lista_empleado() throws SQLException {
        ArrayList<Empleado> ltsEmpleado = new ArrayList();
        String sql = "SELECT * FROM Empleado WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objE = new Empleado();
                objE.setIdEmpleado(rs.getInt(1));
                objE.setNombre(rs.getString(2));
                objE.setApellido(rs.getString(3));
                objE.setTelefono(rs.getInt(4));
                objE.setDireccion(rs.getString(5));
                objE.setCargo(rs.getString(6));

                //buscamos documento
                DocumentoDao objD = new DocumentoDaoImp(con);
                Documento objDoc = objD.buscar_doc_id(rs.getInt(7));
                objE.setIdDocumento(objDoc);

                objE.setNroDocumento(rs.getString(8));
                objE.setEstado(rs.getInt(9));
                ltsEmpleado.add(objE);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return ltsEmpleado;
    }
}
