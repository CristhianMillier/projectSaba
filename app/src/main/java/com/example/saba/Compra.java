package com.example.saba;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saba.datos.Compras;
import com.example.saba.datos.DetCompra;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.Producto;
import com.example.saba.datos.Proveedores;
import com.example.saba.negocio.CompraBo;
import com.example.saba.negocio.DetCompraBo;
import com.example.saba.negocio.ProductoBo;
import com.example.saba.negocio.ProveedorBo;

import java.util.ArrayList;
import java.util.Calendar;

public class Compra extends Fragment {
    private ImageButton btnfecha, guardar, limpiar, agregar, eliminar, editar;
    private EditText pago, fecha, cantidad, precio;
    private Spinner comboProvee, comboProduc;
    private TableLayout tabla;

    private ArrayList<String> listaProveedor, listaProducto;
    private ArrayList<Proveedores> ltsProveedor = null;
    private ArrayList<Producto> ltsProducto = null;
    private int idProvee;
    private ArrayList<String[]> fila = new ArrayList<>();
    private String tipo, id;
    private TextView txtTabla;
    private Proveedores objP = new Proveedores();
    private Empleado objE = new Empleado();
    private String auxFecha = "";
    private int pagar = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vie = inflater.inflate(R.layout.compra, container, false);

        comboProvee = vie.findViewById(R.id.txt_CompProvee);
        pago = vie.findViewById(R.id.txt_CompPago);
        fecha = vie.findViewById(R.id.txt_CompFecha);
        comboProduc = vie.findViewById(R.id.txt_CompProd);
        cantidad = vie.findViewById(R.id.txt_ComCantidad);
        precio = vie.findViewById(R.id.txt_ComPrecio);
        btnfecha = vie.findViewById(R.id.comCalendar);
        tabla = vie.findViewById(R.id.tablaCompra);
        agregar = vie.findViewById(R.id.comAgregar);
        eliminar = vie.findViewById(R.id.comQuitar);
        limpiar = vie.findViewById(R.id.comLimpiar);
        guardar = vie.findViewById(R.id.conGuardar);
        editar = vie.findViewById(R.id.comEditar);

        fecha.setEnabled(false);
        pago.setEnabled(false);

        //Llenamos los combos
        obtenerLista();
        comboProvee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idProvee = ltsProveedor.get(i - 1).getIdProveedor();
                } else{
                    idProvee = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        comboProduc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    tipo = String.valueOf(ltsProducto.get(i - 1).getIdCategoria().getNombre());
                    id = String.valueOf(ltsProducto.get(i - 1).getId_Product());
                } else{
                    tipo = "";
                    id = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Para Fecha
        btnfecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);

                DatePickerDialog calendar = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = day + "-" + (month + 1) + "-" + year;
                        auxFecha = year + "-" + (month + 1) + "-" + day;
                        fecha.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });

        //Llenamos la tabla
        agregarHeadres();

        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validadAgregar()){
                    String[] cuerpo = {tipo, cantidad.getText().toString().trim(), precio.getText().toString().trim(), id};
                    if (buscarTabla()){
                        fila.add(cuerpo);
                        TableRow row = new TableRow(getContext());
                        for (int i = 0; i < 3; i++){
                            txtTabla = new TextView(getContext());
                            txtTabla.setGravity(Gravity.CENTER);
                            txtTabla.setTextSize(20);
                            txtTabla.setBackgroundColor(Color.WHITE);
                            txtTabla.setText(cuerpo[i]);
                            row.addView(txtTabla, margen());
                        }
                        tabla.addView(row);
                        pagar = pagar + (Integer.parseInt(cantidad.getText().toString().trim()) *
                                Integer.parseInt(precio.getText().toString().trim()));
                        pago.setText(String.valueOf(pagar));
                        limpiarListado();
                    } else {
                        Toast.makeText(getContext(), "El Producto ya se encuentra en operación.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos del Listado Producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tabla.removeAllViews();
                agregarHeadres();
                fila = new ArrayList<>();
                pago.setText("");
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                limpiar();
                Intent nuevo = new Intent(getContext(), EditarCompra.class);
                startActivity(nuevo);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validar()){
                    cargarObject();
                    Compras objC = new Compras(0, Double.parseDouble(pago.getText().toString().trim()),
                            auxFecha, objP, objE, 0);
                    try {
                        if (CompraBo.grabarCompra(objC)){
                            Toast.makeText(getContext(), "Compra: Registro Correcto.", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getContext(), "Compra: No se pudo registrar", Toast.LENGTH_SHORT).show();
                        }
                        //Registramos el Datalle Compra
                        for (int i = 0; i < fila.size(); i++){
                            String[] insert = fila.get(i);

                            Producto objPro = new Producto();
                            objPro.setId_Product(Integer.parseInt(insert[3]));

                            DetCompra objD = new DetCompra(0, Integer.parseInt(insert[1]),
                                    Double.parseDouble(insert[2]), objPro, null);

                            ProductoBo.agregar_Producto(objPro, Integer.parseInt(insert[1]), Double.parseDouble(insert[2]));
                            DetCompraBo.grabarDetCompra(objD);
                        }
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  vie;
    }

    private void obtenerLista() {
        try {
            // Para Producto
            listaProducto = new ArrayList<String>();
            ltsProducto = new ArrayList();
            ltsProducto = ProductoBo.lista_Producto();
            listaProducto.add("-Seleccione-");
            for (int i = 0; i < ltsProducto.size(); i++){
                listaProducto.add(ltsProducto.get(i).getNombre() + " - " + ltsProducto.get(i).getIdCategoria().getNombre());
            }

            //Para Proveedor
            listaProveedor = new ArrayList<String>();
            ltsProveedor = new ArrayList();
            ltsProveedor = ProveedorBo.lista_Proveedores();
            listaProveedor.add("-Selecione-");
            for (int i = 0; i < ltsProveedor.size(); i++){
                listaProveedor.add(ltsProveedor.get(i).getNombre());
            }

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProducto);
        comboProduc.setAdapter(adatarD);

        ArrayAdapter<CharSequence> adatarDe = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProveedor);
        comboProvee.setAdapter(adatarDe);
    }

    private boolean validadAgregar(){
        boolean estado = true;
        if (cantidad.getText().toString().trim().equals("") || precio.getText().toString().trim().equals("") || tipo.equals("")){
            estado = false;
        }
        return estado;
    }

    private boolean buscarTabla(){
        boolean estado = true;
        if (fila.size() != 0){
            for (int i = 0; i < fila.size(); i++){
                String[] busca = fila.get(i);
                if (busca[0].equals(tipo)){
                    estado = false;
                }
            }
        }
        return estado;
    }

    private TableRow.LayoutParams margen(){
        TableRow.LayoutParams param = new TableRow.LayoutParams();
        param.setMargins(1, 1, 1, 1);
        param.weight = 1;
        return param;
    }

    private void agregarHeadres(){
        String[] encabezado = {"Tipo", "Cantidad", "Precio C/U"};
        TableRow row = new TableRow(getContext());
        for (int i = 0; i < 3; i++){
            txtTabla = new TextView(getContext());
            txtTabla.setGravity(Gravity.CENTER);
            txtTabla.setTextSize(20);
            txtTabla.setBackgroundColor(Color.GRAY);
            txtTabla.setText(encabezado[i]);
            row.addView(txtTabla, margen());
        }
        tabla.addView(row);
    }
    private  void limpiarListado(){
        comboProduc.setSelection(0);
        cantidad.setText("");
        precio.setText("");
    }
    private void limpiar(){
        comboProvee.setSelection(0);
        pago.setText("");
        fecha.setText("");
        comboProduc.setSelection(0);
        cantidad.setText("");
        precio.setText("");

        tabla.removeAllViews();
        agregarHeadres();
        fila = new ArrayList<>();

        idProvee = 0;
        tipo = "";
        id = "";
    }
    private boolean validar(){
        boolean estado = true;
        if (idProvee == 0 || pago.getText().toString().trim().equals("") ||
                fecha.getText().toString().trim().equals("") || fila.size() == 0){
            estado = false;
        }
        return  estado;
    }
    private void cargarObject(){
        objP.setIdProveedor(idProvee);

        Bundle recibe = getActivity().getIntent().getExtras();
        int idE = Integer.parseInt(recibe.getString("idEmp"));
        objE.setIdEmpleado(idE);
    }
}