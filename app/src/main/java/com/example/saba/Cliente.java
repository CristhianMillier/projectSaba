package com.example.saba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saba.datos.Clientes;
import com.example.saba.datos.Documento;
import com.example.saba.negocio.ClienteBo;
import com.example.saba.negocio.DocumentoBo;

import java.util.ArrayList;

public class Cliente extends Fragment {
    private ImageButton buscar, eliminar, nuevo, guardar, limpiar;
    private EditText buscarNro, razonS, telef, direccion, nroDoc;
    private CheckBox est;
    private int estado = 0, documento = 0;
    private Spinner comboDocumemt;
    private ArrayList<String> listaDocumento;
    private ArrayList<Documento> ltsDocumento = null;
    private Clientes objCliente = null;
    private String doc = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vie =  inflater.inflate(R.layout.cliente, container, false);

        buscarNro = vie.findViewById(R.id.txt_buscarCliente);
        razonS = vie.findViewById(R.id.txt_nomClie);
        telef = vie.findViewById(R.id.txt_telfClie);
        direccion = vie.findViewById(R.id.txt_direccClie);
        nroDoc = vie.findViewById(R.id.txt_nroClie);
        comboDocumemt = vie.findViewById(R.id.txt_docClie);
        est = vie.findViewById(R.id.txt_estadoClie);

        //LLenamos Combo
        obtenerLista();
        comboDocumemt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    documento = ltsDocumento.get(i - 1).getIdDocument();
                    doc = ltsDocumento.get(i - 1).getNombre();
                } else{
                    documento = 0;
                    doc = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Eventos para clic
        buscar = vie.findViewById(R.id.buscarClie);
        nuevo = vie.findViewById(R.id.nuevoClie);
        eliminar = vie.findViewById(R.id.eliminarClie);
        guardar = vie.findViewById(R.id.guardarClie);
        limpiar = vie.findViewById(R.id.limpiarClie);

        activar(false);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activar(true);
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                activar(false);
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = buscarNro.getText().toString().trim();
                if (num.equals("")){
                    Toast.makeText(getContext(), "Ingrese un dato.", Toast.LENGTH_SHORT).show();
                } else{
                    try {
                        if (ClienteBo.buscar_cli_dni(num) != null){
                            objCliente = ClienteBo.buscar_cli_dni(num);
                            llenarFormulario(objCliente);
                            activar(true);
                        } else {
                            if (ClienteBo.buscar_cli_razon(num) != null){
                                objCliente = ClienteBo.buscar_cli_razon(num);
                                llenarFormulario(objCliente);
                                activar(true);
                            } else {
                                Toast.makeText(getContext(), "Cliente no encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = buscarNro.getText().toString().trim();
                if (num.equals("")){
                    Toast.makeText(getContext(), "Ingrese un Dato.", Toast.LENGTH_SHORT).show();
                } else {
                    Clientes obj = null;
                    try {
                        if (ClienteBo.buscar_cli_dni(num) != null){
                            obj = ClienteBo.buscar_cli_dni(num);
                            confirmarElimar(obj);
                        } else {
                            if (ClienteBo.buscar_cli_razon(num) != null){
                                obj = ClienteBo.buscar_cli_razon(num);
                                confirmarElimar(obj);
                            } else {
                                Toast.makeText(getContext(), "Cliente no encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                buscarNro.setText("");
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()){
                    Toast.makeText(getContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    estado();
                    Documento objD = new Documento(documento, "", 0);
                    Clientes clie = new Clientes(0, razonS.getText().toString().trim(),
                            Integer.parseInt(telef.getText().toString().trim()),
                            direccion.getText().toString().trim(), objD,
                            nroDoc.getText().toString().trim(), estado);
                    if (validarDoc()){
                        if (objCliente == null){
                            try {
                                if (ClienteBo.grabarCliente(clie)){
                                    Toast.makeText(getContext(), "Se registro Correctamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "No se pudo registrar.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            clie.setIdCliente(objCliente.getIdCliente());
                            try {
                                if (ClienteBo.modficarCliente(clie)){
                                    Toast.makeText(getContext(), "Se modificó Correctamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "No se pudo modificar.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        limpiar();
                        activar(false);
                    } else {
                        Toast.makeText(getContext(), "Ingrese correctamente el Nro. Documento.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return vie;
    }

    private void confirmarElimar(Clientes obj){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Desea eliminar a " + obj.getRazonSocial());
        builder.setTitle("Eliminar Cliente");
        Clientes finalObj = obj;
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (ClienteBo.eliminarCliente(finalObj)){
                        Toast.makeText(getContext(), "Eliminación Correcta.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se pudo eliminar.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void obtenerLista() {
        try {
            listaDocumento = new ArrayList<String>();
            ltsDocumento = new ArrayList();
            ltsDocumento = DocumentoBo.lista_documento();
            listaDocumento.add("-Seleccione-");
            for (int i = 0; i < ltsDocumento.size(); i++){
                listaDocumento.add(ltsDocumento.get(i).getNombre());
            }
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaDocumento);
        comboDocumemt.setAdapter(adatarD);
    }

    private void activar (boolean estado){
        razonS.setEnabled(estado);
        telef.setEnabled(estado);
        direccion.setEnabled(estado);
        comboDocumemt.setEnabled(estado);
        nroDoc.setEnabled(estado);
        est.setEnabled(estado);
        limpiar.setEnabled(estado);
        guardar.setEnabled(estado);

        buscarNro.setEnabled(!estado);
        buscar.setEnabled(!estado);
        eliminar.setEnabled(!estado);
        nuevo.setEnabled(!estado);
    }
     private void limpiar(){
         buscarNro.setText("");
         razonS.setText("");
         telef.setText("");
         direccion.setText("");
         nroDoc.setText("");
         comboDocumemt.setSelection(0);
         est.setChecked(true);

         estado = 0;
         documento = 0;
         objCliente = null;
         doc = "";
     }

    private boolean validar(){
        boolean estado = false;
        if (razonS.getText().toString().trim().equals("") || telef.getText().toString().trim().equals("") ||
                direccion.getText().toString().trim().equals("") || nroDoc.getText().toString().trim().equals("") ||
                documento == 0 ){
            estado = true;
        }
        return  estado;
    }

    private void llenarFormulario (Clientes obj){
        buscarNro.setText("");

        razonS.setText(obj.getRazonSocial());
        telef.setText(String.valueOf(obj.getTelefefono()));
        direccion.setText(obj.getDireccion());
        nroDoc.setText(String.valueOf(obj.getNroDoc()));
        comboDocumemt.setSelection((obj.getIdDoc().getIdDocument() - 1));
        est.setChecked(true);
    }

    private void estado(){
        if (est.isChecked() == true){
            estado = 0; //activo
        } else {
            estado = 1; // inactivo
        }
    }

    private boolean validarDoc(){
        boolean estado = true;
        switch (doc){
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