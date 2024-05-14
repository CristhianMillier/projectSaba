package com.example.saba.negocio;

import com.example.saba.datos.Comprobante;
import com.example.saba.datos.ComprobanteDao;
import com.example.saba.datos.ComprobanteDaoImp;
import com.example.saba.datos.Conectar;

import java.sql.Connection;
import java.util.ArrayList;

public class ComprobanteBo {
    public static ArrayList lista_Comprobante() throws Exception {
        Connection con = null;
        ArrayList<Comprobante> ltsComprobante = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            ComprobanteDao objC = new ComprobanteDaoImp(con);
            ltsComprobante = objC.lista_Comprobante();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsComprobante;
    }
}
