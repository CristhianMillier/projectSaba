package com.example.saba;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import com.example.saba.negocio.ClienteBo;
import com.example.saba.negocio.CompraBo;
import com.example.saba.negocio.DetCompraBo;
import com.example.saba.negocio.ProductoBo;
import com.example.saba.negocio.ProveedorBo;

import java.util.ArrayList;
import java.util.Calendar;

public class EditarCompra extends AppCompatActivity {
    private ImageButton btnfechausca, eliminarCompra, buscar, btnfecha, guardar, limpiar, agregar, eliminar, atras;
    private EditText fechabusca, pago, fecha, cantidad, precio;
    private Spinner comboProvee1, comboProvee2, comboProduc;
    private TableLayout tabla;

    private ArrayList<String> listaProveedor, listaProducto;
    private ArrayList<Proveedores> ltsProveedor = null;
    private ArrayList<Producto> ltsProducto = null;
    private ArrayList<String[]> fila = new ArrayList<>();
    private String tipo, cantTabla, precioTabla, id;
    private TextView txtTabla;
    private int idProvee = 0;
    private String auxFecha = "";
    private Compras objCrompra = null;
    private boolean clicEliminar = true;
    private int pagar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_compra);

        fechabusca = (EditText) findViewById(R.id.buscarFecha);
        comboProvee1 = (Spinner) findViewById(R.id.buscProvee);
        comboProvee2 = (Spinner) findViewById(R.id.txt_ComProvee2);
        pago = (EditText) findViewById(R.id.txt_CompPago2);
        fecha = (EditText) findViewById(R.id.txt_CompFecha2);
        comboProduc = (Spinner) findViewById(R.id.txt_CompProd2);
        cantidad = (EditText) findViewById(R.id.txt_ComCant2);
        precio = (EditText) findViewById(R.id.txt_ComPrecio2);
        tabla = (TableLayout) findViewById(R.id.tablaCompra2);

        btnfechausca = (ImageButton) findViewById(R.id.buscCalendar);
        buscar = (ImageButton) findViewById(R.id.buscCompra);
        eliminarCompra = (ImageButton) findViewById(R.id.elimCompra);
        btnfecha = (ImageButton) findViewById(R.id.comCalendar2);
        agregar = (ImageButton) findViewById(R.id.comAgregar2);
        eliminar = (ImageButton) findViewById(R.id.comEliminar2);
        atras = (ImageButton) findViewById(R.id.comAtras);
        limpiar = (ImageButton) findViewById(R.id.comLimpiar2);
        guardar = (ImageButton) findViewById(R.id.conGuardar2);

        fechabusca.setEnabled(false);
        fecha.setEnabled(false);
        pago.setEnabled(false);
        activar(true);
        agregarHeadres();

        //Para Combos
        obtenerLista();
        comboProvee1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        comboProvee2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }

    public void Calendario(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);

        DatePickerDialog calendar = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                auxFecha = year + "-" + (month + 1) + "-" + day;
                fechabusca.setText(date);
            }
        }, anio, mes, dia);
        calendar.show();
    }
    public void Calendario2(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);

        DatePickerDialog calendar = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                auxFecha = year + "-" + (month + 1) + "-" + day;
                fecha.setText(date);
            }
        }, anio, mes, dia);
        calendar.show();
    }

    public void buscar(View view){
        if (fechabusca.getText().toString().trim().length() > 0 && idProvee != 0){
            try {
                objCrompra = CompraBo.buscar_Com_Fecha(auxFecha, idProvee);
                if (objCrompra != null){
                    activar(false);
                    limpiarBusca();
                    llenarFormulario(objCrompra);
                } else {
                    Toast.makeText(this, "Compra no encontrada.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Seleecione una fecha y un proveedor.", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarCompra(View view){
        if (fechabusca.getText().toString().trim().length() > 0 && idProvee != 0){
            try {
                Compras objC = CompraBo.buscar_Com_Fecha(auxFecha, idProvee);
                if (objC != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setMessage("Desea eliminar la compra realizada el día " + objC.getFecha() +
                            " por el Proveedor " + objC.getIdProvee().getNombre());
                    builder.setTitle("Eliminar Compra");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                if (CompraBo.eliminarCompra(objC)){
                                    Toast.makeText(getBaseContext(), "Eliminación Correcta.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "No se pudo eliminar.", Toast.LENGTH_SHORT).show();
                                }
                                //Editamos Producto
                                ArrayList<DetCompra> det = DetCompraBo.lista_DetCompra(objC.getIdCompra());
                                for (DetCompra DC: det){
                                    ProductoBo.actualizar_Producto(DC.getId_Producto(), -DC.getCantidad(), -DC.getPrecio());
                                }
                            } catch (Exception e){
                                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(this, "Compra no encontrada.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            limpiarBusca();
        } else {
            Toast.makeText(getBaseContext(), "Seleecione una fecha y un proveedor.", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarTabla(View view){
        tabla.removeAllViews();
        agregarHeadres();
        try {
            //Eliminamos Compra y Datalle Compra
            for (int i = 0; i < fila.size(); i++){
                String[] insert = fila.get(i);

                Producto objPro = new Producto();
                objPro.setId_Product(Integer.parseInt(insert[3]));

                ProductoBo.actualizar_Producto(objPro, -Integer.parseInt(insert[1]), -Double.parseDouble(insert[2]));
                DetCompraBo.eliminarDetCompra(Integer.parseInt(insert[4]));
            }
        } catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clicEliminar = false;
        pago.setText("");
        fila = new ArrayList<>();
    }

    public void agregarDatos(View view){
        if (validadAgregar()){
            String[] cuerpo = {tipo, cantidad.getText().toString().trim(), precio.getText().toString().trim(), id};
            if (buscarTabla()){
                fila.add(cuerpo);
                TableRow row = new TableRow(getBaseContext());
                for (int i = 0; i < 3; i++){
                    txtTabla = new TextView(getBaseContext());
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
                Toast.makeText(getBaseContext(), "El Producto ya se encuentra en operación.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Ingrese los campos del Listado Producto.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarFormulario(View view){
        limpiar();
        activar(true);
    }

    public void cerrarFormulario(View view){
        this.finish();
    }

    public void guardar(View view){
        if (validar()){
            Compras objC = new Compras(objCrompra.getIdCompra(), Double.parseDouble(pago.getText().toString().trim()),
                    auxFecha, objCrompra.getIdProvee(), objCrompra.getIdEmpleado(), 0);
            try {
                if (CompraBo.modificarCompra(objC)){
                    Toast.makeText(getBaseContext(), "Compra: Registro Correcto.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getBaseContext(), "Compra: No se pudo registrar", Toast.LENGTH_SHORT).show();
                }

                if (clicEliminar){
                    //Eliminamos Compra y Datalle Compra
                    for (int i = 0; i < fila.size(); i++){
                        String[] insert = fila.get(i);

                        Producto objPro = new Producto();
                        objPro.setId_Product(Integer.parseInt(insert[3]));

                        ProductoBo.actualizar_Producto(objPro, -Integer.parseInt(insert[1]), -Double.parseDouble(insert[2]));
                        DetCompraBo.eliminarDetCompra(objC.getIdCompra());
                    }
                }

                //Registramos el Datalle Compra
                for (int i = 0; i < fila.size(); i++){
                    String[] insert = fila.get(i);

                    Producto objPro = new Producto();
                    objPro.setId_Product(Integer.parseInt(insert[3]));

                    DetCompra objD = new DetCompra(0, Integer.parseInt(insert[1]),
                            Double.parseDouble(insert[2]), objPro, null);

                    int cant = Integer.parseInt(insert[1]);
                    ProductoBo.agregar_Producto(objPro, cant, Double.parseDouble(insert[2]));
                    DetCompraBo.grabarDetCompra(objD);
                }
                limpiar();
                activar(true);
            } catch (Exception e){
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void llenarFormulario(Compras objC) {
        comboProvee2.setSelection(objC.getIdProvee().getIdProveedor());
        pago.setText(String.valueOf(objC.getPago()));
        fecha.setText(objC.getFecha());
        try {
            ArrayList<DetCompra> det = DetCompraBo.lista_DetCompra(objC.getIdCompra());
            for (DetCompra DC: det){
                String[] cuerpo = {DC.getId_Producto().getIdCategoria().getNombre(), String.valueOf(DC.getCantidad()),
                        String.valueOf(DC.getPrecio()), String.valueOf(DC.getId_Producto().getId_Product()), String.valueOf(objC.getIdCompra())};
                fila.add(cuerpo);
                TableRow row = new TableRow(getBaseContext());
                for (int i = 0; i < 3; i++){
                    txtTabla = new TextView(getBaseContext());
                    txtTabla.setGravity(Gravity.CENTER);
                    txtTabla.setTextSize(20);
                    txtTabla.setBackgroundColor(Color.WHITE);
                    txtTabla.setText(cuerpo[i]);
                    row.addView(txtTabla, margen());
                }
                tabla.addView(row);
                limpiarListado();
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProducto);
        comboProduc.setAdapter(adatarD);

        ArrayAdapter<CharSequence> adatarDe = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProveedor);
        comboProvee1.setAdapter(adatarDe);

        ArrayAdapter<CharSequence> adatarDer = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProveedor);
        comboProvee2.setAdapter(adatarDer);
    }

    private void activar(boolean estado){
        btnfechausca.setEnabled(estado);
        comboProvee1.setEnabled(estado);
        buscar.setEnabled(estado);
        eliminarCompra.setEnabled(estado);
        atras.setEnabled(estado);

        comboProvee2.setEnabled(!estado);
        btnfecha.setEnabled(!estado);
        comboProduc.setEnabled(!estado);
        cantidad.setEnabled(!estado);
        precio.setEnabled(!estado);
        agregar.setEnabled(!estado);
        eliminar.setEnabled(!estado);
        limpiar.setEnabled(!estado);
        guardar.setEnabled(!estado);
    }

    private void limpiarBusca(){
        fechabusca.setText("");
        comboProvee1.setSelection(0);
    }

    private void limpiar(){
        comboProvee2.setSelection(0);
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
        cantTabla = "";
        precioTabla = "";
        id = "";
        clicEliminar = true;
    }

    private void agregarHeadres(){
        String[] encabezado = {"Tipo", "Cantidad", "Precio C/U"};
        TableRow row = new TableRow(getBaseContext());
        for (int i = 0; i < 3; i++){
            txtTabla = new TextView(getBaseContext());
            txtTabla.setGravity(Gravity.CENTER);
            txtTabla.setTextSize(20);
            txtTabla.setBackgroundColor(Color.GRAY);
            txtTabla.setText(encabezado[i]);
            row.addView(txtTabla, margen());
        }
        tabla.addView(row);
    }

    private TableRow.LayoutParams margen(){
        TableRow.LayoutParams param = new TableRow.LayoutParams();
        param.setMargins(1, 1, 1, 1);
        param.weight = 1;
        return param;
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

    private  void limpiarListado(){
        comboProduc.setSelection(0);
        cantidad.setText("");
        precio.setText("");
    }

    private boolean validar(){
        boolean estado = true;
        if (idProvee == 0 || pago.getText().toString().trim().equals("") ||
                fecha.getText().toString().trim().equals("") || fila.size() == 0){
            estado = false;
        }
        return  estado;
    }
}