package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.ProveedorDao;
import com.example.saba.datos.ProveedorDaoImp;
import com.example.saba.datos.Proveedores;

import java.sql.Connection;
import java.util.ArrayList;

public class ProveedorBo {
    public static boolean grabarProveedor(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            objP.grabar(objetc);
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
    public static boolean eliminarProveedor(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            objP.eliminar(objetc);
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
    public static boolean modificarProveedor(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            objP.modificar(objetc);
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
    public static Proveedores buscar_prov_dni(String nro) throws Exception {
        Connection con = null;
        Proveedores objPr = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            objPr = objP.buscar_prov_dni(nro);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objPr;
    }
    public static Proveedores buscar_prov_nombre(String nombre) throws Exception {
        Connection con = null;
        Proveedores objPr = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            objPr = objP.buscar_prov_nombre(nombre);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objPr;
    }
    public static ArrayList lista_Proveedores() throws Exception {
        Connection con = null;
        ArrayList<Proveedores> ltsProveedor = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProveedorDao objP = new ProveedorDaoImp(con);
            ltsProveedor = objP.lista_Proveedores();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsProveedor;
    }
}
