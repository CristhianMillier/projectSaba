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

import com.example.saba.datos.Documento;
import com.example.saba.datos.Proveedores;
import com.example.saba.negocio.DocumentoBo;
import com.example.saba.negocio.ProveedorBo;

import java.util.ArrayList;

public class Proveedor extends Fragment {
    private ImageButton buscar, eliminar, nuevo, guardar, limpiar;
    private EditText buscarNro, nombre, apellido, telef, direccion, nroDoc;
    private CheckBox est;
    private Spinner comboDocumemt;

    private int estado = 0, documento = 0;
    private ArrayList<String> listaDocumento;
    private ArrayList<Documento> ltsDocumento = null;
    private Proveedores objProveedor = null;
    private String doc = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.proveedor, container, false);

        buscarNro = view.findViewById(R.id.txt_BuscarProvee);
        nombre = view.findViewById(R.id.txt_nameProvee);
        apellido = view.findViewById(R.id.txt_ApeeProvee);
        telef = view.findViewById(R.id.txt_TelfProvee);
        direccion = view.findViewById(R.id.txt_DireccProvee);
        comboDocumemt = view.findViewById(R.id.txt_DocProvee);
        nroDoc = view.findViewById(R.id.txt_NroDocProvee);
        est = view.findViewById(R.id.txt_estadoProvee);

        buscar = view.findViewById(R.id.buscarProvee);
        eliminar = view.findViewById(R.id.eliminarProvee);
        nuevo = view.findViewById(R.id.nuevoProvee);
        guardar = view.findViewById(R.id.guardarProvee);
        limpiar = view.findViewById(R.id.limpiarProvee);

        activar(true);
        //Llenamos el combo
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

        //Eventos Clic
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activar(false);
                limpiar();
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activar(true);
                limpiar();
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
                        if (ProveedorBo.buscar_prov_dni(num) != null){
                            objProveedor = ProveedorBo.buscar_prov_dni(num);
                            llenarFormulario(objProveedor);
                            activar(false);
                        } else {
                            if (ProveedorBo.buscar_prov_nombre(num) != null){
                                objProveedor = ProveedorBo.buscar_prov_nombre(num);
                                llenarFormulario(objProveedor);
                                activar(false);
                            } else {
                                Toast.makeText(getContext(), "Proveedor no encontrado.", Toast.LENGTH_SHORT).show();
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
                    try {
                        if (ProveedorBo.buscar_prov_dni(num) != null){
                            objProveedor = ProveedorBo.buscar_prov_dni(num);
                            confirmarEliminar();
                        } else {
                            if (ProveedorBo.buscar_prov_nombre(num) != null){
                                objProveedor = ProveedorBo.buscar_prov_nombre(num);
                                confirmarEliminar();
                            } else {
                                Toast.makeText(getContext(), "Proveedor no encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                buscarNro.setText("");
                objProveedor = null;
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()){
                    estado();
                    Documento objD = new Documento(documento, "", 0);
                    Proveedores prove = new Proveedores(0, nombre.getText().toString().trim(), apellido.getText().toString().trim(),
                            Integer.parseInt(telef.getText().toString().trim()),
                            direccion.getText().toString().trim(), objD,
                            nroDoc.getText().toString().trim(), estado);
                    if (validarDoc()){
                        if (objProveedor == null){
                            try {
                                if (ProveedorBo.grabarProveedor(prove)){
                                    Toast.makeText(getContext(), "Se registro Correctamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "No se pudo registrar.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            prove.setIdProveedor(objProveedor.getIdProveedor());
                            try {
                                if (ProveedorBo.modificarProveedor(prove)){
                                    Toast.makeText(getContext(), "Se modificó Correctamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "No se pudo modificar.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        limpiar();
                        activar(true);
                    } else {
                        Toast.makeText(getContext(), "Ingrese correctamente el Nro. Documento.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void confirmarEliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Desea eliminar a " + objProveedor.getNombre());
        builder.setTitle("Eliminar Proveedor");
        Proveedores finalObj = objProveedor;
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (ProveedorBo.eliminarProveedor(finalObj)){
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

    private void activar(boolean estado){
        buscarNro.setEnabled(estado);
        buscar.setEnabled(estado);
        eliminar.setEnabled(estado);
        nuevo.setEnabled(estado);

        nombre.setEnabled(!estado);
        apellido.setEnabled(!estado);
        telef.setEnabled(!estado);
        direccion.setEnabled(!estado);
        comboDocumemt.setEnabled(!estado);
        nroDoc.setEnabled(!estado);
        est.setEnabled(!estado);
        limpiar.setEnabled(!estado);
        guardar.setEnabled(!estado);
    }

    private void limpiar(){
        buscarNro.setText("");
        comboDocumemt.setSelection(0);
        nombre.setText("");
        apellido.setText("");
        telef.setText("");
        direccion.setText("");
        nroDoc.setText("");
        est.setChecked(true);

        objProveedor = null;
        estado = 0;
        documento = 0;
        doc = "";
    }

    private boolean validar(){
        boolean estado = true;
        if (nombre.getText().toString().trim().equals("") || apellido.getText().toString().trim().equals("")
                || telef.getText().toString().trim().equals("") ||
                direccion.getText().toString().trim().equals("") || nroDoc.getText().toString().trim().equals("") ||
                documento == 0 ){
            estado = false;
        }
        return  estado;
    }

    private void estado(){
        if (est.isChecked() == true){
            estado = 0; //activo
        } else {
            estado = 1; // inactivo
        }
    }

    private void llenarFormulario (Proveedores obj){
        buscarNro.setText("");

        nombre.setText(obj.getNombre());
        apellido.setText(obj.getApellido());
        telef.setText(String.valueOf(obj.getTelef()));
        direccion.setText(obj.getDireccion());
        nroDoc.setText(String.valueOf(obj.getNroDoc()));
        comboDocumemt.setSelection((obj.getIdDocumento().getIdDocument() - 1));
        est.setChecked(true);
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