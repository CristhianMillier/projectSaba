package com.example.saba.datos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface  EmpleadoDao extends GeneralDao{
    public Empleado buscar_emp_dni(String nro) throws SQLException;
    public Empleado buscar_em_id(int id) throws SQLException;
    public ArrayList lista_empleado() throws SQLException;
}
