package com.example.saba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saba.datos.Login;
import com.example.saba.negocio.LoginBo;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.txt_nombre);
        contra = (EditText) findViewById(R.id.txt_contra);
    }

    public void ingresar(View view){
        String usuario = user.getText().toString().trim();
        String contrasena = contra.getText().toString().trim();
        try {
            Login objL = LoginBo.validarLogin(usuario, contrasena);
            if (objL != null){
                Intent nuevo = new Intent(MainActivity.this, Principal.class);

                Bundle envio = new Bundle();
                envio.putString("nombre", objL.getIdEmp().getNombre());
                envio.putString("apellido", objL.getIdEmp().getApellido());
                envio.putString("cargo", objL.getIdEmp().getCargo());
                envio.putString("id_Doc", String.valueOf(objL.getIdEmp().getIdDocumento().getIdDocument()));
                envio.putString("telef", String.valueOf(objL.getIdEmp().getTelefono()));
                envio.putString("direcc", objL.getIdEmp().getDireccion());
                envio.putString("nroDoc", objL.getIdEmp().getNroDocumento());
                envio.putString("user", objL.getUser());
                envio.putString("contra", objL.getContra());
                envio.putString("idEmp", String.valueOf(objL.getIdEmp().getIdEmpleado()));
                envio.putString("idLog", String.valueOf(objL.getIdLogin()));

                nuevo.putExtras(envio);

                startActivity(nuevo);
            } else {
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
            }
            limpiar();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void registrar(View view){
        Intent nuevo = new Intent(this, RegistroEmpleado.class);
        startActivity(nuevo);
    }
    private void limpiar(){
        user.setText("");
        contra.setText("");
    }
}