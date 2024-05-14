package com.example.saba.datos;

public class Login {
    private int idLogin;
    private String user;
    private String contra;
    private Empleado idEmp;
    private int estado;

    public Login() {
    }

    public Login(int idLogin, String user, String contra, Empleado idEmp, int estado) {
        this.idLogin = idLogin;
        this.user = user;
        this.contra = contra;
        this.idEmp = idEmp;
        this.estado = estado;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public Empleado getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Empleado idEmp) {
        this.idEmp = idEmp;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
