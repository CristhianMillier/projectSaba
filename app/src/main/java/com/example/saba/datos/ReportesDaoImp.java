package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReportesDaoImp implements ReportesDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;

    public ReportesDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public ArrayList<String[]> ClienteAño(String fecha, String cliente) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Cliente, Empleado, Pago from reporteCliente where Fecha >= '" +
                fecha + "' and Cliente = '" + cliente  + "' ";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String clie = rs.getString(1);
                String emp = rs.getString(2);
                String pago = String.valueOf(rs.getDouble(3));

                lts.add(new String[]{clie, emp, pago});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> CalidadVenta(String calidad) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Cliente, Producto, Calidad, Categoria, sum(Cantidad) Canitdad, sum(Total) Total " +
                "from reporteTipoSoya where Calidad = '" + calidad  + "' Group By Cliente, Producto, Calidad, Categoria";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String clie = rs.getString(1);
                String prod = rs.getString(2);
                String cal = rs.getString(3);
                String cat = rs.getString(4);
                String cantid = String.valueOf(rs.getInt(5));
                String total = String.valueOf(rs.getDouble(6));

                lts.add(new String[]{clie, prod, cal, cat, cantid, total});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> VentaAñoMes(int anio, int mes) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Cliente, Categoria, Fecha, sum(Cantidad) from reporteTipoSoya where YEAR(Fecha) = " + anio +
                " and MONTH(Fecha) = " + mes  + " Group By Cliente, Categoria, Fecha";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String clie = rs.getString(1);
                String cat = rs.getString(2);
                String fecha = rs.getString(3);
                String cantid = String.valueOf(rs.getInt(4));

                lts.add(new String[]{clie, cat, fecha, cantid});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> ProveedorAño(String nombre, String apellido, String fecha) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Nombre_Proveedor, Apellido_Proveedor, Empleado, sum(Pago) from reporteProveedor " +
                "where Nombre_Proveedor = '" + nombre + "' and Apellido_Proveedor = '" + apellido  +
                "' and Fecha >= '" + fecha + "' Group By Nombre_Proveedor, Apellido_Proveedor, Empleado";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String nomb = rs.getString(1);
                String apel = rs.getString(2);
                String empl = rs.getString(3);
                String pago = String.valueOf(rs.getDouble(4));

                lts.add(new String[]{nomb, apel, empl, pago});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> CompraAñoMes(int anio, int mes) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Proveedor_Apellido, Categoria, Fecha, sum(Cantidad) from reporteTipoSoyaCompra where YEAR(Fecha) = " + anio +
                " and MONTH(Fecha) = " + mes  + " Group By Proveedor_Apellido, Categoria, Fecha";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String prove = rs.getString(1);
                String cat = rs.getString(2);
                String fecha = rs.getString(3);
                String cantid = String.valueOf(rs.getInt(4));

                lts.add(new String[]{prove, cat, fecha, cantid});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> CategoriaCompra(String categoria) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Proveedor_Apellido, Producto, Categoria, sum(Cantidad) Canitdad, sum(Total) Total  " +
                "from reporteTipoSoyaCompra where Categoria = '" + categoria  + "' Group By Proveedor_Apellido, Producto, Categoria, Categoria";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String prov = rs.getString(1);
                String prod = rs.getString(2);
                String cat = rs.getString(3);
                String cantid = String.valueOf(rs.getInt(4));
                String total = String.valueOf(rs.getDouble(5));

                lts.add(new String[]{prov, prod, cat, cantid, total});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> CajaAñoa(String fecha, String cliente) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Cliente, Empleado, sum(Pago) from reporteCaja where fecha >= '" + fecha +
                "' and Cliente = '" + cliente + "' group by Cliente, Empleado";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String clie = rs.getString(1);
                String emp = rs.getString(2);
                String pago = String.valueOf(rs.getDouble(3));

                lts.add(new String[]{clie, emp, pago});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> Rotacion(String fechaI, String fechaF) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Producto, Calidad, Categoria, sum(Cantidad), sum(total)  from reporteRotacion" +
                " where Fecha >= '" + fechaI + "' and Fecha <= '" + fechaF + "' Group By Producto, Calidad, Categoria";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String prod = rs.getString(1);
                String calid = rs.getString(2);
                String cate = rs.getString(3);
                String cantid = String.valueOf(rs.getInt(4));
                String pago = String.valueOf(rs.getInt(5));

                lts.add(new String[]{prod, calid, cate, cantid, pago});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }

    @Override
    public ArrayList<String[]> CajaAñoMes(int anio, int mes) throws SQLException {
        ArrayList<String[]> lts = new ArrayList();
        String sql = "select Cliente, Categoria, sum(Cantidad) from reporteRotacion where YEAR(Fecha) = " + anio +
                " and MONTH(Fecha) = " + mes  + " Group By Cliente, Categoria";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String clie = rs.getString(1);
                String cat = rs.getString(2);
                String cantid = String.valueOf(rs.getInt(3));

                lts.add(new String[]{clie, cat, cantid});
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return lts;
    }
}
