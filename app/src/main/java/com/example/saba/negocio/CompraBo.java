package com.example.saba.negocio;

import com.example.saba.datos.Clientes;
import com.example.saba.datos.Compras;
import com.example.saba.datos.CompraDao;
import com.example.saba.datos.CompraDaoImp;
import com.example.saba.datos.Conectar;

import java.sql.Connection;
import java.util.ArrayList;

public class CompraBo {
    public static boolean grabarCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CompraDao objC = new CompraDaoImp(con);
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
    public static boolean modificarCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CompraDao objC = new CompraDaoImp(con);
            objC.modificar(objetc);
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
    public static boolean eliminarCompra(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CompraDao objC = new CompraDaoImp(con);
            objC.eliminar(objetc);
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
    public static Compras buscar_Com_Fecha(String fecha, int id) throws Exception {
        Connection con = null;
        Compras objCom = new Compras();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CompraDao objC = new CompraDaoImp(con);
            objCom = objC.buscar_Fecha(fecha, id);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objCom;
    }
    public static ArrayList lista_Compra() throws Exception {
        Connection con = null;
        ArrayList<Compras> ltsCompra = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CompraDao objC = new CompraDaoImp(con);
            ltsCompra = objC.lista_Compras();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsCompra;
    }
}
