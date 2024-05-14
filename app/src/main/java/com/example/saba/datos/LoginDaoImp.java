package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class LoginDaoImp implements LoginDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Login objL;

    public LoginDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objL = (Login) object;
        int idEmp = 0;
        try {
            String sql1 = "SELECT MAX(Id_Empleado) AS id FROM Empleado";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql1);
            while (rs.next()){
                idEmp = rs.getInt(1);
            }

            String sql2 = "insert into Login  (Usuario, Contraseña, Id_Empleado, Estado) VALUES ('"
                    + objL.getUser() + "', '"
                    + objL.getContra() + "', "
                    + idEmp + ", "
                    + objL.getEstado() + ") ";
            pst = con.prepareStatement(sql2);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean eliminar(Object object) throws SQLException {
        objL = (Login) object;
        try {
            String sql = "UPDATE Login SET Estado=? WHERE Id_Login = " + objL.getIdLogin();
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
        objL = (Login) object;
        try {
            String sql = "UPDATE Login SET Usuario=?, Contraseña=?, Estado=? WHERE Id_Login = " + objL.getIdLogin();
            pst = con.prepareStatement(sql);
            pst.setString(1, objL.getUser());
            pst.setString(2, objL.getContra());
            pst.setInt(3, objL.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Login validadUsuario(String user, String clave) throws SQLException {
        objL = null;
        String sql = "SELECT * FROM Login WHERE Usuario ='" + user + "' and Contraseña = '" +
                clave + "' and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objL = new Login();
                objL.setIdLogin(rs.getInt(1));
                objL.setUser(rs.getString(2));
                objL.setContra(rs.getString(3));

                //Buscar empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objL.setIdEmp(objE.buscar_em_id(rs.getInt(4)));

                objL.setEstado(rs.getInt(5));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return objL;
    }

    @Override
    public Login buscarId(int id) throws SQLException {
        objL = null;
        String sql = "SELECT * FROM Login WHERE Id_Login = " + id + " and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objL = new Login();
                objL.setIdLogin(rs.getInt(1));
                objL.setUser(rs.getString(2));
                objL.setContra(rs.getString(3));

                //Buscar empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objL.setIdEmp(objE.buscar_em_id(rs.getInt(4)));

                objL.setEstado(rs.getInt(5));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return objL;
    }

    @Override
    public boolean buscarLogin(String user) throws SQLException {
        String sql = "SELECT * FROM Login WHERE Usuario = '" + user + "'";
        boolean estado = true;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                estado = false;
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return estado;
    }
}
