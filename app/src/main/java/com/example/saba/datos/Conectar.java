package com.example.saba.datos;

import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar {
    private static Connection cn = null;

    public static Connection getConexion() throws Exception, ClassNotFoundException{
        Connection cn = null;
        StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politica);
        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        cn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.9;databaseName=SABA;user=sa;password=1234;");
        return cn;
    }
}
