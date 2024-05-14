package com.example.saba.negocio;

import com.example.saba.datos.CierreCajaDAo;
import com.example.saba.datos.CierreCajaDaoImp;
import com.example.saba.datos.Conectar;
import com.example.saba.datos.DetCierre;

import java.sql.Connection;

public class CierreCajaBo {
    public static boolean grabarCierre(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            objE.grabar(objetc);
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
    public static boolean modificarCierre(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            objE.modificar(objetc);
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
    public static DetCierre buscarDetCierre(int idCaja) throws Exception {
        Connection con = null;
        DetCierre obj = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            obj = objE.buscarDetCierre(idCaja);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return  obj;
    }
    public static int buscarCierre() throws Exception {
        Connection con = null;
        int idCierre = 0;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            idCierre = objE.buscarCierre();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return  idCierre;
    }
    public static double cierreCaja() throws Exception {
        Connection con = null;
        double dinero = 0;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            dinero = objE.cierreCaja();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return  dinero;
    }
    public static void CajaCerrada(Object object) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CierreCajaDAo objE = new CierreCajaDaoImp(con);
            objE.CajaCerrada(object);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
