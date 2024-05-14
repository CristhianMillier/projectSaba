package com.example.saba.datos;

public class DetCierre {
    private int id_DetCierre;
    private Cajas Id_Caja;
    private int id_Cierra;

    public DetCierre() {
    }

    public DetCierre(int id_DetCierre, Cajas Id_Caja, int id_Cierra) {
        this.id_DetCierre = id_DetCierre;
        this.Id_Caja = Id_Caja;
        this.id_Cierra = id_Cierra;
    }

    public int getId_DetCierre() {
        return id_DetCierre;
    }

    public void setId_DetCierre(int id_DetCierre) {
        this.id_DetCierre = id_DetCierre;
    }

    public Cajas getId_Caja() {
        return Id_Caja;
    }

    public void setId_Caja(Cajas Id_Caja) {
        this.Id_Caja = Id_Caja;
    }

    public int getId_Cierra() {
        return id_Cierra;
    }

    public void setId_Cierra(int id_Cierra) {
        this.id_Cierra = id_Cierra;
    }
}
