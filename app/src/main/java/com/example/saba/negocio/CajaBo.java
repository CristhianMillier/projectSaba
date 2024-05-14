package com.example.saba.negocio;

import com.example.saba.datos.CajaDao;
import com.example.saba.datos.CajaDaoImp;
import com.example.saba.datos.Cajas;
import com.example.saba.datos.Conectar;

import java.sql.Connection;
import java.util.ArrayList;

public class CajaBo {
    public static boolean grabarCaja(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CajaDao objC = new CajaDaoImp(con);
            objC.grabar(objetc);
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
    public static boolean grabarCajaSinComp(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CajaDao objC = new CajaDaoImp(con);
            objC.grabarSinComp(objetc);
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
    public static ArrayList lista_Caja() throws Exception {
        Connection con = null;
        ArrayList<Cajas> ltsCaja = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CajaDao objC = new CajaDaoImp(con);
            ltsCaja = objC.obtenerListaCaja();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsCaja;
    }
    public static boolean buscarVentaCaja(int idVenta) throws Exception {
        boolean estado;
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CajaDao objC = new CajaDaoImp(con);
            estado = objC.buscarVenta(idVenta);
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
    public static boolean validarAbrirCaja() throws Exception {
        boolean estado;
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CajaDao objC = new CajaDaoImp(con);
            estado = objC.validarAbrirCaja();
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
}
