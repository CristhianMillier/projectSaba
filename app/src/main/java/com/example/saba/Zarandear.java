package com.example.saba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saba.datos.Compras;
import com.example.saba.datos.DetCompra;
import com.example.saba.datos.TipoSoya;
import com.example.saba.datos.Producto;
import com.example.saba.datos.Zarander;
import com.example.saba.negocio.CompraBo;
import com.example.saba.negocio.DetCompraBo;
import com.example.saba.negocio.ProductoBo;
import com.example.saba.negocio.TipoSoyaBo;
import com.example.saba.negocio.ZarandearBo;

import java.util.ArrayList;

public class Zarandear extends Fragment {
    private ImageButton buscar, eliminar, agregar, quitar, nuevo, limpiar, guardar;
    private Spinner comboProducto1, comboProducto2, comboCategoria;
    private TextView cantidad, precio;
    private EditText cantTabla, precTabla;
    private TableLayout tabla;

    private ArrayList<String> listaProducto, listaTipo;
    private ArrayList<TipoSoya> ltstipo = null;
    private ArrayList<Producto> ltsProducto = null;
    private ArrayList<Zarander> ltsZarandear = new ArrayList<Zarander>();
    private ArrayList<String[]> fila = new ArrayList<>();
    private String tipo, idProducto, idTipoPro;
    private TextView txtTabla;
    private int contar = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.zarandear, container, false);

        tabla = view.findViewById(R.id.tablaProducto);
        comboProducto1 = view.findViewById(R.id.producto);
        comboProducto2 = view.findViewById(R.id.txt_ProdZar);
        comboCategoria = view.findViewById(R.id.txt_ProdCateg);
        cantidad = view.findViewById(R.id.prodCant);
        precio = view.findViewById(R.id.prodPrecio);
        cantTabla = view.findViewById(R.id.txt_ProdCantidad);
        precTabla = view.findViewById(R.id.txt_ProdPrecio);

        buscar = view.findViewById(R.id.prodBusca);
        eliminar = view.findViewById(R.id.comEliminar);
        agregar = view.findViewById(R.id.prodAgregar);
        quitar = view.findViewById(R.id.pocEliminarTabla);
        nuevo = view.findViewById(R.id.pocNuevo);
        limpiar = view.findViewById(R.id.pocLimpiar);
        guardar = view.findViewById(R.id.pocGuardar);

        activar(true);

        //Llenamos los combos
        obtenerLista();
        comboProducto1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idProducto = String.valueOf(ltsProducto.get(i - 1).getId_Product());
                } else{
                    idProducto = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        comboProducto2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idProducto = String.valueOf(ltsProducto.get(i - 1).getId_Product());
                    cantidad.setText(String.valueOf(ltsProducto.get(i - 1).getStock()));
                    precio.setText(String.valueOf(ltsProducto.get(i - 1).getPrecio()));
                } else{
                    idProducto = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        comboCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idTipoPro = String.valueOf(ltstipo.get(i - 1).getIdTipo());
                    tipo = ltstipo.get(i - 1).getNombre();
                } else{
                    idTipoPro = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Llenamos la tabla
        agregarHeadres();

        buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (idProducto.equals("")){
                    Toast.makeText(getContext(), "Selecione un producto.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        ltsZarandear = ZarandearBo.lista_BuscaZarandeo(Integer.parseInt(idProducto));
                        if (ltsZarandear.size() != 0){
                            activar(false);
                            comboProducto2.setEnabled(false);
                            comboProducto1.setSelection(0);
                            comboProducto2.setSelection(Integer.parseInt(idProducto));
                            cantidad.setText("0");
                            llenarTabla(ltsZarandear);
                            contar = 0;
                        } else {
                            Toast.makeText(getContext(), "No hay Productos zarandeados.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (idProducto.equals("")){
                    Toast.makeText(getContext(), "Selecione un producto.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        ltsZarandear = ZarandearBo.lista_BuscaZarandeo(Integer.parseInt(idProducto));
                        if (ltsZarandear.size() != 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Desea eliminar los productos zarandeados de la soya "
                                    + ltsZarandear.get(0).getIdProd().getIdCategoria().getNombre() + ". ");
                            builder.setTitle("Eliminar Producto Zarandeado");
                            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        if (ZarandearBo.eliminarZarandear(ltsZarandear.get(0))){
                                            Toast.makeText(getContext(), "Eliminación Correcta.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "No se pudo eliminar.", Toast.LENGTH_SHORT).show();
                                        }
                                        int suma_cantidad = 0;

                                        //Editamos Producto
                                        for (Zarander z: ltsZarandear){
                                            suma_cantidad = suma_cantidad + z.getStock();
                                        }
                                        ProductoBo.agregarZarandeo(ltsZarandear.get(0).getIdProd(), suma_cantidad);
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
                            comboProducto1.setSelection(0);
                            contar = 0;
                            ltsZarandear = new ArrayList<Zarander>();
                        } else {
                            Toast.makeText(getContext(), "No hay Productos zarandeados.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validadAgregar()){
                    String[] cuerpo = {tipo, cantTabla.getText().toString().trim(), precTabla.getText().toString().trim(), idProducto, idTipoPro};
                    if (buscarTabla()){
                        contar = contar + Integer.parseInt(cantTabla.getText().toString());
                        if (contar <= Integer.parseInt(cantidad.getText().toString())){
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
                            limpiarListado();
                        } else {
                            Toast.makeText(getContext(), "Las quitales ingresados no coinciden.", Toast.LENGTH_SHORT).show();
                            contar = contar - Integer.parseInt(cantTabla.getText().toString());
                        }
                    } else {
                        Toast.makeText(getContext(), "El Producto ya se encuentra en operación.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos del Listado Producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quitar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tabla.removeAllViews();
                agregarHeadres();
                if (ltsZarandear.size() != 0){
                    try {
                        if (ZarandearBo.eliminarZarandear(ltsZarandear.get(0))){
                            Toast.makeText(getContext(), "Eliminación Correcta.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "No se pudo eliminar.", Toast.LENGTH_SHORT).show();
                        }
                        int suma_cantidad = 0;

                        //Editamos Producto
                        for (Zarander z: ltsZarandear){
                            suma_cantidad = suma_cantidad + z.getStock();
                        }
                        ProductoBo.agregarZarandeo(ltsZarandear.get(0).getIdProd(), suma_cantidad);
                        cantidad.setText(String.valueOf(suma_cantidad));
                        precio.setText(String.valueOf(ltsZarandear.get(0).getIdProd().getPrecio()));
                        comboProducto2.setEnabled(true);
                        contar = 0;
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                fila = new ArrayList<>();
            }
        });

        nuevo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                limmpiar();
                activar(false);
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                limmpiar();
                activar(true);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validar()){
                    try {
                        Producto objPro = new Producto();
                        for (int i = 0; i < fila.size(); i++){
                            String[] insert = fila.get(i);

                            objPro.setId_Product(Integer.parseInt(insert[3]));

                            TipoSoya objTP = new TipoSoya();
                            objTP.setIdTipo(Integer.parseInt(insert[4]));

                            Zarander objZ = new Zarander(0, Double.parseDouble(insert[2]),
                                    Integer.parseInt(insert[1]), objTP, objPro, 0);
                            ZarandearBo.modificarZarandear(objZ);
                        }
                        ProductoBo.agregarZarandeo(objPro, 0);
                        limmpiar();
                        activar(true);
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Complete todos los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void llenarTabla(ArrayList<Zarander> ltsZarandear) {
        precio.setText(String.valueOf(ltsZarandear.get(0).getPrecio()));
        for (Zarander Z: ltsZarandear){
            String[] cuerpo = {Z.getIdTipo().getNombre(), String.valueOf(Z.getStock()), String.valueOf(Z.getPrecio()),
                    String.valueOf(Z.getIdProd().getId_Product()), String.valueOf(Z.getIdTipo().getIdTipo())};
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
            limpiarListado();
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

            //Para tipo
            listaTipo = new ArrayList<String>();
            ltstipo = new ArrayList();
            ltstipo = TipoSoyaBo.lista_tioSoya();
            listaTipo.add("-Selecione-");
            for (int i = 0; i < ltstipo.size(); i++){
                listaTipo.add(ltstipo.get(i).getNombre());
            }

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProducto);
        comboProducto1.setAdapter(adatarD);

        ArrayAdapter<CharSequence> adatarDet = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProducto);
        comboProducto2.setAdapter(adatarD);

        ArrayAdapter<CharSequence> adatarDe = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaTipo);
        comboCategoria.setAdapter(adatarDe);
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

    private TableRow.LayoutParams margen(){
        TableRow.LayoutParams param = new TableRow.LayoutParams();
        param.setMargins(1, 1, 1, 1);
        param.weight = 1;
        return param;
    }

    private boolean validadAgregar(){
        boolean estado = true;
        if (cantTabla.getText().toString().trim().equals("") || precTabla.getText().toString().trim().equals("") || tipo.equals("")){
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
        comboCategoria.setSelection(0);
        cantTabla.setText("");
        precTabla.setText("");
        tipo = "";
    }

    private void activar(boolean estado){
        comboProducto1.setEnabled(estado);
        buscar.setEnabled(estado);
        eliminar.setEnabled(estado);
        nuevo.setEnabled(estado);

        comboProducto2.setEnabled(!estado);
        comboCategoria.setEnabled(!estado);
        cantTabla.setEnabled(!estado);
        precTabla.setEnabled(!estado);
        agregar.setEnabled(!estado);
        quitar.setEnabled(!estado);
        limpiar.setEnabled(!estado);
        guardar.setEnabled(!estado);
    }

    private void limmpiar(){
        comboProducto1.setSelection(0);
        comboProducto2.setSelection(0);
        precio.setText("Precio C/U");
        cantidad.setText("Cantidad (q)");
        comboCategoria.setSelection(0);

        tabla.removeAllViews();
        agregarHeadres();
        fila = new ArrayList<>();

        ltsZarandear = new ArrayList<Zarander>();
        tipo = "";
        idProducto = "";
        idTipoPro = "";
        contar = 0;
    }

    private boolean validar(){
        boolean estado = true;
        if (idProducto.equals("") || fila.size() == 0){
            estado = false;
        }
        return estado;
    }
}