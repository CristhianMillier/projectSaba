package com.example.saba.negocio;

import com.example.saba.datos.ClienteDao;
import com.example.saba.datos.ClienteDaoImp;
import com.example.saba.datos.Clientes;
import com.example.saba.datos.Conectar;

import java.sql.Connection;
import java.util.ArrayList;

public class ClienteBo {
    public static boolean grabarCliente(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao objC = new ClienteDaoImp(con);
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
    public static boolean eliminarCliente(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao objC = new ClienteDaoImp(con);
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
    public static boolean modficarCliente(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao objC = new ClienteDaoImp(con);
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
    public static ArrayList listaCliente() throws Exception {
        Connection con = null;
        ArrayList<Clientes> ltsCliente = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao objC = new ClienteDaoImp(con);
            ltsCliente = objC.lista_cliente();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsCliente;
    }
    public static Clientes buscar_cli_dni(String nro) throws Exception {
        Connection con = null;
        Clientes objC = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao obj = new ClienteDaoImp(con);
            objC = obj.buscar_cli_dni(nro);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objC;
    }
    public static Clientes buscar_cli_razon(String razon) throws Exception {
        Connection con = null;
        Clientes objC = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao obj = new ClienteDaoImp(con);
            objC = obj.buscar_cli_razon(razon);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objC;
    }
    public static Clientes buscar_cli_id(int id) throws Exception {
        Connection con = null;
        Clientes objC = new Clientes();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ClienteDao obj = new ClienteDaoImp(con);
            objC = obj.buscar_cli_id(id);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objC;
    }
}
