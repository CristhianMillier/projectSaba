package com.example.saba.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saba.R;
import com.example.saba.databinding.FragmentHomeBinding;
import com.example.saba.datos.Documento;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.Login;
import com.example.saba.negocio.DocumentoBo;
import com.example.saba.negocio.EmpleadoBo;
import com.example.saba.negocio.LoginBo;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private TextView bienvenida;
    private String nombre, apell;
    private ImageButton nuevo, guardar, limpiar;
    private EditText name, apellido, telefono, direccion, nroDoccumento, usuario, password;
    private Spinner comboCargo, comboDocumemt;
    private ArrayList<String> listaCargo, listaDocumento;
    private String cargoCombo = "";
    private int docCombo = 0;
    private ArrayList<Documento> ltsDocumento = null;
    private Login objL = null;
    private String doc = "";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        bienvenida = root.findViewById(R.id.txt_bienvenida);
        comboCargo = root.findViewById(R.id.txt_cargoEmp);
        comboDocumemt = root.findViewById(R.id.txt_documen2);
        name = root.findViewById(R.id.txt_name2);
        apellido = root.findViewById(R.id.txt_apellido2);
        telefono = root.findViewById(R.id.txt_telef2);
        direccion = root.findViewById(R.id.txt_direcc2);
        nroDoccumento = root.findViewById(R.id.txt_nroDoc2);
        usuario = root.findViewById(R.id.txt_usuario2);
        password = root.findViewById(R.id.txt_pasword2);

        obtenerLista();
        comboCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    cargoCombo = listaCargo.get(i).toString();
                } else{
                    cargoCombo = "";
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
                    docCombo = ltsDocumento.get(i - 1).getIdDocument();
                    doc = ltsDocumento.get(i - 1).getNombre();
                } else{
                    docCombo = 0;
                    doc = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bienvenida();

        //Eventos para clic
        nuevo = root.findViewById(R.id.nuevoEmp);
        guardar = root.findViewById(R.id.guardarEmp);
        limpiar = root.findViewById(R.id.limpiarEmp);

        activar(false);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activar(true);
                cargarFormulario();
                comboCargo.setEnabled(false);
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                activar(false);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()){
                    Toast.makeText(getContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (objL.getUser().equals(usuario.getText().toString().trim())){
                            modificar();
                        } else {
                            if (LoginBo.buscarLogin(usuario.getText().toString().trim())){
                                modificar();
                            } else {
                                Toast.makeText(getContext(), "Login: El usuario ingresado ya existe.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        activar(false);
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return root;
    }

    private void modificar() throws Exception {
        Documento objDoc = new Documento(docCombo, "", 0);
        Empleado objE = new Empleado(0, name.getText().toString().trim(), apellido.getText().toString().trim(),
                Integer.parseInt(telefono.getText().toString().trim()), direccion.getText().toString().trim(), cargoCombo,
                objDoc, nroDoccumento.getText().toString().trim(), 0);
        Login objLog = new Login(0, usuario.getText().toString().trim(), password.getText().toString().trim(), null, 0);

        objE.setIdEmpleado(objL.getIdEmp().getIdEmpleado());
        objLog.setIdLogin(objL.getIdLogin());

        if (validarDoc()){
            try {
                if (EmpleadoBo.modificarEmpleado(objE)){
                    Toast.makeText(getContext(), "Empleado: Midificación Correcta", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(), "Empleado: Error al modificar.", Toast.LENGTH_SHORT).show();
                }

                if (LoginBo.modificarLogin(objLog)){
                    Toast.makeText(getContext(), "Login: Midificación Correcta.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(), "Login: Error al modificar.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                throw e;
            }
        } else {
            Toast.makeText(getContext(), "Ingrese correctamente el Nro. Documento.", Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarC = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaCargo);
        comboCargo.setAdapter(adatarC);

        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaDocumento);
        comboDocumemt.setAdapter(adatarD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void bienvenida(){
        Bundle recibe = getActivity().getIntent().getExtras();
        nombre = recibe.getString("nombre");
        apell = recibe.getString("apellido");
        bienvenida.setText("Bienvenido " + nombre + " " + apell);
    }

    private void cargarFormulario() {
        Bundle recibe = getActivity().getIntent().getExtras();

        try {
            objL = LoginBo.buscarId(Integer.parseInt(recibe.getString("idLog")));
            cargarNew(objL);
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void activar (boolean estado){
        name.setEnabled(estado);
        apellido.setEnabled(estado);
        telefono.setEnabled(estado);
        direccion.setEnabled(estado);
        nroDoccumento.setEnabled(estado);
        usuario.setEnabled(estado);
        password.setEnabled(estado);
        comboCargo.setEnabled(estado);
        comboDocumemt.setEnabled(estado);

        nuevo.setEnabled(!estado);
        limpiar.setEnabled(estado);
        guardar.setEnabled(estado);
    }

    private boolean validar(){
        boolean estado = false;
        if (name.getText().toString().trim().equals("") || apellido.getText().toString().trim().equals("") ||
                telefono.getText().toString().trim().equals("") || direccion.getText().toString().trim().equals("") ||
                cargoCombo.equals("") || docCombo == 0 ||
                nroDoccumento.getText().toString().trim().equals("") ||
                usuario.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("")){
            estado = true;
        }
        return  estado;
    }
    private void limpiar(){
        name.setText("");
        apellido.setText("");
        telefono.setText("");
        direccion.setText("");
        nroDoccumento.setText("");
        usuario.setText("");
        password.setText("");
        comboCargo.setSelection(0);
        comboDocumemt.setSelection(0);

        cargoCombo = "";
        docCombo = 0;
        doc = "";
    }
    private void cargarNew(Login objLog){
        name.setText(objLog.getIdEmp().getNombre());
        apellido.setText(objLog.getIdEmp().getApellido());
        telefono.setText(String.valueOf(objLog.getIdEmp().getTelefono()));
        direccion.setText(objLog.getIdEmp().getDireccion());
        nroDoccumento.setText(objLog.getIdEmp().getNroDocumento());
        usuario.setText(objLog.getUser());
        password.setText(objLog.getContra());
        comboDocumemt.setSelection(objLog.getIdEmp().getIdDocumento().getIdDocument() - 1);

        if (objLog.getIdEmp().getCargo().equals("Vendedor")){
            comboCargo.setSelection(1);
        } else if (objLog.getIdEmp().getCargo().equals("Cajero")){
            comboCargo.setSelection(2);
        } else {
            comboCargo.setSelection(3);
        }
    }

    private boolean validarDoc(){
        boolean estado = true;
        switch (doc){
            case "DNI":
                if (nroDoccumento.length() != 8){
                    estado = false;
                }
                break;
            case "RUC":
                if (nroDoccumento.length() != 11){
                    estado = false;
                }
                break;
            case "Pasaporte":
                if (nroDoccumento.length() != 9){
                    estado = false;
                }
                break;
        }
        return estado;
    }
}