package com.example.saba.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompraDaoImp implements CompraDao{
    private PreparedStatement pst;
    private Statement st;
    private Connection con;
    private Compras objC;

    public CompraDaoImp(Connection con) {
        this.con = con;
    }

    @Override
    public boolean grabar(Object object) throws SQLException {
        objC = (Compras) object;
        try {
            String sql = "insert into Compra (Pagar, Fecha, Id_Proveedor, Id_Empleado, Estado)"
                    + " VALUES (" + objC.getPago() + ", '"
                    + objC.getFecha() + "', "
                    + objC.getIdProvee().getIdProveedor() + ", "
                    + objC.getIdEmpleado().getIdEmpleado()+ ", "
                    + objC.getEstado() + ") ";
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
        objC = (Compras) object;
        try {
            String sql = "UPDATE Compra SET Estado=? WHERE Id_Compra = " + objC.getIdCompra();
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
        objC = (Compras) object;
        try {
            String sql = "UPDATE Compra SET Pagar=?, Fecha=?, Id_Proveedor=?, Id_Empleado=?, "
                    + "Estado=? WHERE Id_Compra = " + objC.getIdCompra();
            pst = con.prepareStatement(sql);
            pst.setDouble(1, objC.getPago());
            pst.setString(2, objC.getFecha());
            pst.setInt(3, objC.getIdProvee().getIdProveedor());
            pst.setInt(4, objC.getIdEmpleado().getIdEmpleado());
            pst.setInt(5, objC.getEstado());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Compras buscar_Id(int id) throws SQLException {
        objC = new Compras();
        String sql = "SELECT * FROM Compra WHERE Id_Compra = " + id;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC.setIdCompra(rs.getInt(1));
                objC.setPago(rs.getDouble(2));
                objC.setFecha(rs.getString(3));

                //buscamos proveedor
                ProveedorDao objP = new ProveedorDaoImp(con);
                objC.setIdProvee(objP.buscar_prov_id(rs.getInt(4)));

                //buscamos empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objC.setIdEmpleado(objE.buscar_em_id(rs.getInt(5)));

                objC.setEstado(rs.getInt(6));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public Compras buscar_Fecha(String fecha, int id) throws SQLException {
        objC = null;
        String sql = "SELECT * FROM Compra WHERE Fecha = '" + fecha + "' and Id_Proveedor = " + id + " and Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Compras();
                objC.setIdCompra(rs.getInt(1));
                objC.setPago(rs.getDouble(2));
                objC.setFecha(rs.getString(3));

                //buscamos proveedor
                ProveedorDao objP = new ProveedorDaoImp(con);
                objC.setIdProvee(objP.buscar_prov_id(rs.getInt(4)));

                //buscamos empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objC.setIdEmpleado(objE.buscar_em_id(rs.getInt(5)));

                objC.setEstado(rs.getInt(6));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  objC;
    }

    @Override
    public ArrayList lista_Compras() throws SQLException {
        ArrayList<Compras> ltsCompra = new ArrayList();
        String sql = "SELECT * FROM Compra WHERE Estado = 0";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                objC = new Compras();
                objC.setIdCompra(rs.getInt(1));
                objC.setPago(rs.getDouble(2));
                objC.setFecha(rs.getString(3));

                //buscamos proveedor
                ProveedorDao objP = new ProveedorDaoImp(con);
                objC.setIdProvee(objP.buscar_prov_id(rs.getInt(4)));

                //buscamos empleado
                EmpleadoDao objE = new EmpleadoDaoImp(con);
                objC.setIdEmpleado(objE.buscar_em_id(rs.getInt(5)));

                objC.setEstado(rs.getInt(6));
                ltsCompra.add(objC);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return  ltsCompra;
    }
}
