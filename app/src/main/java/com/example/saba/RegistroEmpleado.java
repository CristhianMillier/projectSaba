package com.example.saba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saba.datos.Documento;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.Login;
import com.example.saba.negocio.DocumentoBo;
import com.example.saba.negocio.EmpleadoBo;
import com.example.saba.negocio.LoginBo;

import java.util.ArrayList;

public class RegistroEmpleado extends AppCompatActivity {
    private EditText nombre, apellido, telefono, direccion, nroDoc, user, pasword;
    private Spinner comboCargo, comboDocumemt;
    private ArrayList<String> listaCargo, listaDocumento;
    private String cargo = "";
    private int doc = 0;
    private ArrayList<Documento> ltsDocumento = null;
    private String documen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleado);

        comboCargo = (Spinner) findViewById(R.id.txt_cargo);
        comboDocumemt = (Spinner) findViewById(R.id.txt_documen);
        nombre = (EditText) findViewById(R.id.txt_name);
        apellido = (EditText) findViewById(R.id.txt_apellido);
        telefono = (EditText) findViewById(R.id.txt_telef);
        direccion = (EditText) findViewById(R.id.txt_direcc);
        nroDoc = (EditText) findViewById(R.id.txt_nroDoc);
        user = (EditText) findViewById(R.id.txt_usuario);
        pasword = (EditText) findViewById(R.id.txt_pasword);
        
        obtenerLista();
        comboCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    cargo = listaCargo.get(i).toString();
                } else{
                    cargo = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        comboDocumemt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    doc = ltsDocumento.get(i - 1).getIdDocument();
                    documen = ltsDocumento.get(i - 1).getNombre();
                } else{
                    doc = 0;
                    documen = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void obtenerLista() {
        //Para Cargo
        listaCargo = new ArrayList<String>();
        listaCargo.add("-Seleccione-");
        listaCargo.add("Vendedor");
        listaCargo.add("Cajero");
        listaCargo.add("Zarandeador");

        //Para Documento
        try {
            listaDocumento = new ArrayList<String>();
            ltsDocumento = new ArrayList();
            ltsDocumento = DocumentoBo.lista_documento();
            listaDocumento.add("-Seleccione-");
            for (int i = 0; i < ltsDocumento.size(); i++){
                listaDocumento.add(ltsDocumento.get(i).getNombre());
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarC = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCargo);
        comboCargo.setAdapter(adatarC);

        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaDocumento);
        comboDocumemt.setAdapter(adatarD);
    }

    public void registros(View view){
        if (validar()){
            Toast.makeText(getApplicationContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
        } else{
            Documento objDoc = new Documento(doc, "", 0);
            Empleado objE = new Empleado(0, nombre.getText().toString().trim(), apellido.getText().toString().trim(),
                    Integer.parseInt(telefono.getText().toString().trim()), direccion.getText().toString().trim(), cargo,
                    objDoc, nroDoc.getText().toString().trim(), 0);
            Login objL = new Login(0, user.getText().toString().trim(), pasword.getText().toString().trim(), null, 0);
            if (validarDoc()){
                try {
                    if (LoginBo.buscarLogin(user.getText().toString().trim())){
                        if (EmpleadoBo.grabarEmpleado(objE)){
                            Toast.makeText(getApplicationContext(), "Empleado: Registro Correcto", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Empleado: Error al Registrarse", Toast.LENGTH_SHORT).show();
                        }

                        if (LoginBo.grabarLogin(objL)){
                            Toast.makeText(getApplicationContext(), "Login: Registro Correcto", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Login: Error al Registrarse", Toast.LENGTH_SHORT).show();
                        }
                        limpiar();
                        Intent nuevo = new Intent(this, MainActivity.class);
                        startActivity(nuevo);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login: El usuario ingresado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Ingrese correctamente el Nro. Documento.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validar(){
        boolean estado = false;
        if (nombre.getText().toString().trim().equals("") || apellido.getText().toString().trim().equals("") ||
                telefono.getText().toString().trim().equals("") || direccion.getText().toString().trim().equals("") ||
                cargo.equals("") || doc == 0 ||
                nroDoc.getText().toString().trim().equals("") ||
                user.getText().toString().trim().equals("") ||
                pasword.getText().toString().trim().equals("")){
            estado = true;
        }
        return  estado;
    }
    private void limpiar(){
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        direccion.setText("");
        nroDoc.setText("");
        user.setText("");
        pasword.setText("");
        comboCargo.setSelection(0);
        comboDocumemt.setSelection(0);

        cargo = "";
        doc = 0;
        documen = "";
    }

    private boolean validarDoc(){
        boolean estado = true;
        switch (documen){
            case "DNI":
                if (nroDoc.length() != 8){
                    estado = false;
                }
                break;
            case "RUC":
                if (nroDoc.length() != 11){
                    estado = false;
                }
                break;
            case "Pasaporte":
                if (nroDoc.length() != 9){
                    estado = false;
                }
                break;
        }
        return estado;
    }
}