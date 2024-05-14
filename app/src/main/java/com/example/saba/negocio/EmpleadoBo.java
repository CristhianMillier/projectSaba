package com.example.saba.negocio;

import com.example.saba.datos.*;
import java.sql.Connection;
import java.util.ArrayList;

public class EmpleadoBo {
    public static boolean grabarEmpleado(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao objE = new EmpleadoDaoImp(con);
            objE.grabar(objetc);
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
    public static boolean modificarEmpleado(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao objE = new EmpleadoDaoImp(con);
            objE.modificar(objetc);
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
    public static boolean eliminarEmpleado(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao objE = new EmpleadoDaoImp(con);
            objE.eliminar(objetc);
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
    public static Empleado buscarEmp_nro(String objetc) throws Exception {
        Connection con = null;
        Empleado objEmp = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao objE = new EmpleadoDaoImp(con);
            objEmp = objE.buscar_emp_dni(objetc);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objEmp;
    }
    public static ArrayList listaEmpleado() throws Exception {
        Connection con = null;
        ArrayList<Empleado> ltsEmpleado = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao objE = new EmpleadoDaoImp(con);
            ltsEmpleado = objE.lista_empleado();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsEmpleado;
    }
    public static Empleado buscar_emp_dni(String nro) throws Exception {
        Connection con = null;
        Empleado objE = new Empleado();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao obj = new EmpleadoDaoImp(con);
            objE = obj.buscar_emp_dni(nro);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objE;
    }
    public static Empleado buscar_em_id(int id) throws Exception {
        Connection con = null;
        Empleado objE = new Empleado();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            EmpleadoDao obj = new EmpleadoDaoImp(con);
            objE = obj.buscar_em_id(id);
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return objE;
    }
}
