package com.example.saba.negocio;

import android.widget.Toast;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.Login;
import com.example.saba.datos.LoginDao;
import com.example.saba.datos.LoginDaoImp;

import java.sql.Connection;

public class LoginBo {
    public static boolean grabarLogin(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            objL.grabar(objetc);
            con.commit();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    public static boolean modificarLogin(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            objL.modificar(objetc);
            con.commit();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    public static boolean eliminarLogin(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            objL.eliminar(objetc);
            con.commit();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    public static Login validarLogin(String user, String clave) throws Exception {
        Connection con = null;
        Login objLog = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            objLog = objL.validadUsuario(user, clave);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objLog;
    }
    public static boolean buscarLogin(String user) throws Exception {
        Connection con = null;
        boolean estado = false;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            if (objL.buscarLogin(user)){
                estado = true;
            }
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return estado;
    }
    public static Login buscarId(int id) throws Exception {
        Connection con = null;
        Login objLog = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            LoginDao objL = new LoginDaoImp(con);
            objLog = objL.buscarId(id);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objLog;
    }
}
