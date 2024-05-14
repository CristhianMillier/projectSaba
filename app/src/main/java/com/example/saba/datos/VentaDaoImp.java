package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VentaDaoImp implements VentaDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Ventas objV;

    public VentaDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objV = (Ventas) object;
        try {
            String sql = "insert into Venta (Fecha, Pagar, Id_Cliente, Id_Empleado, Estado)"
                    + " VALUES ('" + objV.getFecha() + "', "
                    + objV.getPagar() + ", "
                    + objV.getIdCliente().getIdCliente() + ", "
                    + objV.getIdEmpleado().getIdEmpleado() + ", "
                    + objV.getEstado() + ") ";
            pst = con.prepareStatement(sql);
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean eliminar(Object object) throws SQLException {
        objV = (Ventas) object;
        try {
            String sql = "UPDATE Venta SET Estado=? WHERE Id_Venta = " + objV.getId_Venta();
            pst = con.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean modificar(Object object) throws SQLException {
        objV = (Ventas) object;
        try {
            String sql = "UPDATE Venta SET Fecha=?, Pagar=? WHERE Id_Venta = " + objV.getId_Venta();
            pst = con.prepareStatement(sql);
            pst.setString(1, objV.getFecha());
            pst.setDouble(2, objV.getPagar());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public ArrayList obtenerListaVenta() throws SQLException {
        ArrayList<Ventas> ltsVenta = new ArrayList();
        String sql = "SELECT * FROM Venta WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objV = new Ventas();
                objV.setId_Venta(rs.getInt(1));
                objV.setFecha(rs.getString(2));
                objV.setPagar(rs.getDouble(3));

                //buscamos cliente
                ClienteDao objC = new ClienteDaoImp(con);
                objV.setIdCliente(objC.buscar_cli_id(rs.getInt(4)));

                //buscamos empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objV.setIdEmpleado(objE.buscar_em_id(rs.getInt(5)));

                objV.setEstado(rs.getInt(6));
                ltsVenta.add(objV);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  ltsVenta;
    }

    @Override
    public int numTicket() throws SQLException {
        int numVent = 0;
        try {
            String sql = "select * FROM Venta";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numVent = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        }
        return numVent;
    }

    @Override
    public Ventas buscarVenta(int idVenta) throws SQLException {
        objV = null;
        try{
            String sql = "select * FROM Venta WHERE Estado = 0 and Id_Venta = " + idVenta;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objV = new Ventas();
                objV.setId_Venta(rs.getInt(1));
                objV.setFecha(rs.getString(2));
                objV.setPagar(rs.getDouble(3));

                //buscamos cliente
                ClienteDao objC = new ClienteDaoImp(con);
                objV.setIdCliente(objC.buscar_cli_id(rs.getInt(4)));

                //buscamos empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objV.setIdEmpleado(objE.buscar_em_id(rs.getInt(5)));

                objV.setEstado(rs.getInt(6));
            }
        } catch (SQLException e) {
            throw e;
        }
        return objV;
    }
}
