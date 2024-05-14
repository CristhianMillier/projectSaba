package com.example.saba.negocio;

import com.example.saba.datos.ClienteDao;
import com.example.saba.datos.ClienteDaoImp;
import com.example.saba.datos.Clientes;
import com.example.saba.datos.Conectar;
import com.example.saba.datos.Zarander;
import com.example.saba.datos.ZaranderDao;
import com.example.saba.datos.ZaranderDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class ZarandearBo {
    public static boolean grabarZarandear(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            objZ.grabar(objetc);
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
    public static boolean eliminarZarandear(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            objZ.eliminar(objetc);
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
    public static boolean modificarZarandear(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            objZ.modificar(objetc);
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
    public static ArrayList lista_ZarandeoCombo() throws Exception {
        Connection con = null;
        ArrayList<Zarander> ltsZarander = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            ltsZarander = objZ.lista_ZarandeoCombo();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsZarander;
    }
    public static ArrayList lista_BuscaZarandeo(int idProd) throws Exception {
        Connection con = null;
        ArrayList<Zarander> ltsZarander = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            ltsZarander = objZ.lista_BuscaZarandeo(idProd);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsZarander;
    }
    public static boolean actualizarZarandear(Zarander objP, int cantidad) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ZaranderDao objZ = new ZaranderDaoImp(con);
            objZ.actualizarVenta(objP, cantidad);
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
}
