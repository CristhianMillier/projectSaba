package com.example.saba.negocio;

import com.example.saba.datos.Conectar;
import com.example.saba.datos.Documento;
import com.example.saba.datos.DocumentoDao;
import com.example.saba.datos.DocumentoDaoImp;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.EmpleadoDao;
import com.example.saba.datos.EmpleadoDaoImp;

import java.sql.Connection;
import java.util.ArrayList;

public class DocumentoBo {
    public static ArrayList lista_documento() throws Exception {
        Connection con = null;
        ArrayList<Documento> ltsDocumento = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            DocumentoDao objD = new DocumentoDaoImp(con);
            ltsDocumento = objD.lista_documento();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsDocumento;
    }
}
