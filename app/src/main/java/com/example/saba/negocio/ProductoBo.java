package com.example.saba.negocio;

import com.example.saba.datos.Clientes;
import com.example.saba.datos.Conectar;
import com.example.saba.datos.Producto;
import com.example.saba.datos.ProductoDao;
import com.example.saba.datos.ProductoDaoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoBo {
    public static boolean grabarProducto(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProductoDao objP = new ProductoDaoImp(con);
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
    public static ArrayList lista_Producto() throws Exception {
        Connection con = null;
        ArrayList<Producto> ltsProducto = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProductoDao objP = new ProductoDaoImp(con);
            ltsProducto = objP.lista_Producto();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsProducto;
    }
    public static boolean agregar_Producto(Producto obj, int cantidad, double precio) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProductoDao objP = new ProductoDaoImp(con);
            objP.agregarCompra(obj, cantidad, precio);
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
    public static boolean actualizar_Producto(Producto obj, int cantidad, double precio) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProductoDao objP = new ProductoDaoImp(con);
            objP.actualizarCompra(obj, cantidad, precio);
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
    public static boolean agregarZarandeo(Producto obj, int cantidad) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ProductoDao objP = new ProductoDaoImp(con);
            objP.agregarZarandeo(obj, cantidad);
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
