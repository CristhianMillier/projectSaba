package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.VentaDao;
import com.example.saba.datos.VentaDaoImp;
import com.example.saba.datos.Ventas;

import java.sql.Connection;
import java.util.ArrayList;

public class VentaBo {
    public static boolean grabarVenta(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objV = new VentaDaoImp(con);
            objV.grabar(objetc);
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
    public static boolean modificarVenta(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objV = new VentaDaoImp(con);
            objV.modificar(objetc);
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
    public static boolean eliminarVenta(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objV = new VentaDaoImp(con);
            objV.eliminar(objetc);
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
    public static ArrayList lista_Venta() throws Exception {
        Connection con = null;
        ArrayList<Ventas> ltsVenta = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objV = new VentaDaoImp(con);
            ltsVenta = objV.obtenerListaVenta();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsVenta;
    }
    public static int numTicket() throws Exception {
        Connection con = null;
        int ticket = 0;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objV = new VentaDaoImp(con);
            ticket = objV.numTicket();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ticket;
    }
    public static Ventas buscarVenta(int idVenta) throws Exception {
        Connection con = null;
        Ventas objV = new Ventas();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            VentaDao objVenta = new VentaDaoImp(con);
            objV = objVenta.buscarVenta(idVenta);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objV;
    }
}
