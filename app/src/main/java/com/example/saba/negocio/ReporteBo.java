package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.ReportesDao;
import com.example.saba.datos.ReportesDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class ReporteBo {
    public static ArrayList lista_ClienteAño(String fecha, String cliente) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.ClienteAño(fecha, cliente);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_CalidadVenta(String calidad) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.CalidadVenta(calidad);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_VentaMesAño(int anio, int mes) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.VentaAñoMes(anio, mes);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList ProveedorAño(String nombre, String apellido, String fecha) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.ProveedorAño(nombre, apellido, fecha);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_CompraMesAño(int anio, int mes) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.CompraAñoMes(anio, mes);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_CategoriaCompra(String categoria) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.CategoriaCompra(categoria);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_CajaAñoa(String fecha, String cliente) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.CajaAñoa(fecha, cliente);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_Rotacion(String fechaI, String fechaF) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.Rotacion(fechaI, fechaF);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
    public static ArrayList lista_CajaaMesAño(int anio, int mes) throws Exception {
        Connection con = null;
        ArrayList<String[]> lts = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ReportesDao objR = new ReportesDaoImp(con);
            lts = objR.CajaAñoMes(anio, mes);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return lts;
    }
}
