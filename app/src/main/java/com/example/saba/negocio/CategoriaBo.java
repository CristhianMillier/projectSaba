package com.example.saba.negocio;

import com.example.saba.datos.Categoria;
import com.example.saba.datos.CategoriaDao;
import com.example.saba.datos.CategoriaDaoImp;
import com.example.saba.datos.Conectar;

import java.sql.Connection;
import java.util.ArrayList;

public class CategoriaBo {
    public static ArrayList lista_Categoria() throws Exception {
        Connection con = null;
        ArrayList<Categoria> ltsCategoria = new ArrayList();
        try {
            con = Conectar.getConexion();
            con.setAutoCommit(false);
            CategoriaDao objC = new CategoriaDaoImp(con);
            ltsCategoria = objC.lista_Categoria();
            con.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return ltsCategoria;
    }
}
