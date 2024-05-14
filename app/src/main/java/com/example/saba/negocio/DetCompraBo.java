package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.DetCompra;
import com.example.saba.datos.DetCompraDao;
import com.example.saba.datos.DetCompraDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class DetCompraBo {
    public static boolean grabarDetCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetCompraDao objDt = new DetCompraDaoImp(con);
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
    public static boolean modificarDetCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetCompraDao objDt = new DetCompraDaoImp(con);
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
    public static boolean eliminarDetCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetCompraDao objDt = new DetCompraDaoImp(con);
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
    public static ArrayList lista_DetCompra(int idCompra) throws Exception {
        Connection con = null;
        ArrayList<DetCompra> ltsDetCompra = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetCompraDao objDt = new DetCompraDaoImp(con);
            ltsDetCompra = objDt.obtenerListaDetCompra(idCompra);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsDetCompra;
    }
}
