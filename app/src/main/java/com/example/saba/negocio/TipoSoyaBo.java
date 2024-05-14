package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.Documento;
import com.example.saba.datos.DocumentoDao;
import com.example.saba.datos.DocumentoDaoImp;
import com.example.saba.datos.TipoSoya;
import com.example.saba.datos.TipoSoyaDao;
import com.example.saba.datos.TipoSoyaDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class TipoSoyaBo {
    public static ArrayList lista_tioSoya() throws Exception {
        Connection con = null;
        ArrayList<TipoSoya> ltstioSoya = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            TipoSoyaDao objT = new TipoSoyaDaoImp(con);
            ltstioSoya = objT.lista_TipoSoya();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltstioSoya;
    }
}
