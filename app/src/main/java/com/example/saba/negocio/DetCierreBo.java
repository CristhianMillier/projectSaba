package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.DetCierreDao;
import com.example.saba.datos.DetCierreDaoImp;

import java.sql.Connection;

public class DetCierreBo {
    public static boolean grabarDetCierre(Object objetc) throws Exception {
        Connection con = null;
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DetCierreDao objC = new DetCierreDaoImp(con);
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
}
