package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.DetCompra;
import com.example.saba.datos.DetCompraDao;
import com.example.saba.datos.DetCompraDaoImp;
import com.example.saba.datos.DetVenta;
import com.example.saba.datos.DetVentaDao;
import com.example.saba.datos.DetVentaDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class DetVentaBo {
    public static boolean grabarDetVentaa(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetVentaDao objDt = new DetVentaDaoImp(con);
            objDt.grabar(objetc);
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
    public static boolean eliminarDetVentaa(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetVentaDao objDt = new DetVentaDaoImp(con);
            objDt.eliminar(objetc);
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
    public static boolean modificarDetVentaa(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetVentaDao objDt = new DetVentaDaoImp(con);
            objDt.modificar(objetc);
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
    public static ArrayList lista_DetVenta(int idVenta) throws Exception {
        Connection con = null;
        ArrayList<DetVenta> ltsDetVenta = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetVentaDao objDt = new DetVentaDaoImp(con);
            ltsDetVenta = objDt.obtenerListaDetVenta(idVenta);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsDetVenta;
    }
}
