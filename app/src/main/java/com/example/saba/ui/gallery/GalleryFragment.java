package com.example.saba.ui.gallery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saba.databinding.FragmentGalleryBinding;
import com.example.saba.datos.Clientes;
import com.example.saba.datos.Proveedores;
import com.example.saba.negocio.ClienteBo;
import com.example.saba.negocio.ProveedorBo;
import com.example.saba.negocio.ReporteBo;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.example.saba.R;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import harmony.java.awt.Color;

public class GalleryFragment extends Fragment {
    private EditText fecha1, anio1, fecha2, anio2, fecha3, fecha4, fecha5, anio3;
    private Spinner cliente1, calidad1, mes1, proveedor1, mes2, categoria1, cliente2, mes3;
    private ImageButton btnFecha1, btnFecha2, btnFecha3, btnFecha4, btnFecha5;
    private Button reporte1, reporte2, reporte3, reporte4, reporte5, reporte6, reporte7, reporte8, reporte9;

    private String directorio = "MisPDFs", clientes = "", auxFecha = "", auxFecha1 = "";
    private String cal1 = "", nombreP = "", apelP = "", cate = "";
    private ArrayList<String> listaCliente, listaCalidad, listaMes, listaProveedor, listaCategoria;
    private ArrayList<Clientes> ltsCliente = null;
    private ArrayList<Proveedores> ltsProveedor = null;
    private int messsss = 0;

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        //Permisos
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    1000);
        }

        fecha1 = (EditText) root.findViewById(R.id.txt_fechaAño1);
        cliente1 = (Spinner) root.findViewById(R.id.txt_ClieAño);
        btnFecha1 = (ImageButton) root.findViewById(R.id.btnFecha1);
        reporte1 = (Button) root.findViewById(R.id.btnReporte1);
        calidad1 = (Spinner) root.findViewById(R.id.calidad1);
        reporte2 = (Button) root.findViewById(R.id.btnReporte2);
        mes1 = (Spinner) root.findViewById(R.id.txt_mes1);
        anio1 = (EditText) root.findViewById(R.id.txt_anio1);
        reporte3 = (Button) root.findViewById(R.id.btnReporte3);
        proveedor1 = (Spinner) root.findViewById(R.id.txt_ProvAño);
        fecha2 = (EditText) root.findViewById(R.id.txt_fechaCompraAño);
        btnFecha2 = (ImageButton) root.findViewById(R.id.btnFecha2);
        reporte4 = (Button) root.findViewById(R.id.brnReporte4);
        anio2 = (EditText) root.findViewById(R.id.txt_AnioCompra);
        mes2 = (Spinner) root.findViewById(R.id.mesCompra);
        reporte5 = (Button) root.findViewById(R.id.reporte5);
        categoria1 = (Spinner) root.findViewById(R.id.txt_Categoria);
        reporte6 = (Button) root.findViewById(R.id.reporte6);
        cliente2 = (Spinner) root.findViewById(R.id.txt_cliereport) ;
        fecha3 = (EditText) root.findViewById(R.id.txt_Fecha4);
        btnFecha3 = (ImageButton) root.findViewById(R.id.btn_fecha5);
        reporte7 = (Button) root.findViewById(R.id.reporte7);
        fecha4 = (EditText) root.findViewById(R.id.txt_FechaAño4);
        btnFecha4 = (ImageButton) root.findViewById(R.id.btnFecha8);
        fecha5 = (EditText) root.findViewById(R.id.txt_FechaAño5);
        btnFecha5 = (ImageButton) root.findViewById(R.id.btnFecha9);
        reporte8 = (Button) root.findViewById(R.id.reporte8);
        anio3 = (EditText) root.findViewById(R.id.txt_anio10);
        mes3 = (Spinner) root.findViewById(R.id.txt_Mes4);
        reporte9 = (Button) root.findViewById(R.id.reporte9);

        fecha1.setEnabled(false);
        fecha2.setEnabled(false);
        fecha3.setEnabled(false);
        fecha4.setEnabled(false);
        fecha5.setEnabled(false);

        //Llenamos los combos
        obtenerLista();
        cliente1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    clientes = ltsCliente.get(i - 1).getRazonSocial();
                } else{
                    clientes = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cliente2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    clientes = ltsCliente.get(i - 1).getRazonSocial();
                } else{
                    clientes = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calidad1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    cal1 = listaCalidad.get(i).toString();
                } else{
                    cal1 = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mes1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    messsss = i;
                } else{
                    messsss = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        proveedor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    nombreP = ltsProveedor.get(i - 1).getNombre();
                    apelP = ltsProveedor.get(i - 1).getApellido();
                } else{
                    nombreP = "";
                    apelP = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mes2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    messsss = i;
                } else{
                    messsss = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categoria1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    cate = listaCategoria.get(i).toString();
                } else{
                    cate = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mes3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    messsss = i;
                } else{
                    messsss = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Para Fecha
        btnFecha1.setOnClickListener(new View.OnClickListener(){
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
                        fecha1.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });
        btnFecha2.setOnClickListener(new View.OnClickListener(){
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
                        fecha2.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });
        btnFecha3.setOnClickListener(new View.OnClickListener(){
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
                        fecha3.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });
        btnFecha4.setOnClickListener(new View.OnClickListener(){
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
                        fecha4.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });
        btnFecha5.setOnClickListener(new View.OnClickListener(){
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
                        auxFecha1 = year + "-" + (month + 1) + "-" + day;
                        fecha5.setText(date);
                    }
                }, anio, mes, dia);
                calendar.show();
            }
        });

        //Generar reportes
        reporte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarReporte1()){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_ClienteAño(auxFecha, clientes);
                        String[] header = {"Cliente", "Empleado", "Pago S/."};
                        crearPDF("Cliente - Año.pdf", "Reporte Clientes - Año \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reporte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cal1.equals("")){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_CalidadVenta(cal1);
                        String[] header = {"Cliente", "Producto", "Calidad", "Categoria", "Cant.", "Total S/."};
                        crearPDF("Calidad.pdf", "Reporte de Ventas por la Calidad Soya \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reporte3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!anio1.getText().toString().trim().equals("") && messsss > 0){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_VentaMesAño(Integer.parseInt(anio1.getText().toString().trim()),
                                messsss);
                        String[] header = {"Cliente", "Categoria", "Fecha", "Cantidad"};
                        crearPDF("Reporte de Ventas por Año y Mes.pdf", "Reporte de Ventas por Año y Mes \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reporte4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreP.equals("") && !auxFecha.equals("")){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.ProveedorAño(nombreP, apelP, auxFecha);
                        String[] header = {"Nomb.Proveedor", "Apel.Proveedor", "Empleado", "Pago"};
                        crearPDF("Reporte Compras Proveedor - Fecha.pdf", "Reporte Compras Proveedor - Fecha \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reporte5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!anio2.getText().toString().trim().equals("") && messsss > 0){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_CompraMesAño(Integer.parseInt(anio2.getText().toString().trim()),
                                messsss);
                        String[] header = {"Apel.Proveedor", "Categoria", "Fecha", "Cantidad"};
                        crearPDF("Reporte de Compras por Año y Mes.pdf", "Reporte de Compras por Año y Mes \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reporte6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cate.equals("")){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_CategoriaCompra(cate);
                        String[] header = {"Apel.Proveedor", "Producto", "Categoria", "Cant.", "Total S/."};
                        crearPDF("Reporte de Compras por la Categoria.pdf", "Reporte de Compras por la Categoria \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reporte7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clientes.equals("") && !auxFecha.equals("")){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_CajaAñoa(auxFecha, clientes);
                        String[] header = {"Cliente", "Cajero", "Pago S/."};
                        crearPDF("Reporte de Ingresor por Cliente - Fecha.pdf", "Reporte de Ingresor por Cliente - Fecha \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reporte8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fecha4.getText().toString().equals("") && !fecha5.getText().toString().equals("")){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_Rotacion(auxFecha, auxFecha1);
                        String[] header = {"Producto", "Calidad", "Categoria", "Cant.", "Total S/."};
                        crearPDF("Reporte de Rotación de Productos por fecha.pdf", "Reporte de Rotación de Productos por fecha \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reporte9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!anio3.getText().toString().trim().equals("") && messsss > 0){
                    try {
                        ArrayList<String[]> lista =  ReporteBo.lista_CajaaMesAño(Integer.parseInt(anio3.getText().toString().trim()),
                                messsss);
                        String[] header = {"Cliente", "Categoria", "Cantidad"};
                        crearPDF("Reporte de Caja por Año y Mes.pdf", "Reporte de Caja por Año y Mes \n\n", header, lista);
                        Toast.makeText(getContext(), "Se creo el PDF", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void crearPDF(String documento, String title, String[] header, ArrayList<String[]> cuerpo){
        Document document = new Document();
        try {
            File file = crearFichero(documento);
            FileOutputStream fichero = new FileOutputStream(file.getAbsoluteFile());

            PdfWriter writer =  PdfWriter.getInstance(document, fichero);

            document.open();

            Paragraph parrafo = new Paragraph(title, new Font(Font.TIMES_ROMAN, 20, Font.BOLD));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(parrafo);

            //Insertamos Tabla
            PdfPTable tabla = new PdfPTable(header.length);
            int indexC = 0;
            while (indexC < header.length){
                PdfPCell  celda = new PdfPCell(new Phrase(header[indexC++]));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(Color.GREEN);
                tabla.addCell(celda);
            }
            for (int i = 0; i < cuerpo.size(); i++){
                String[] row = cuerpo.get(i);
                for (int f = 0; f < header.length; f++){
                    PdfPCell  celda = new PdfPCell(new Phrase(row[f]));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(celda);
                }
            }
            document.add(tabla);


        } catch (DocumentException e){
        } catch (IOException e){
        } finally {
            document.close();
        }
    }
    private File crearFichero(String nombre){
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null){
            fichero = new File(ruta, nombre);
        }
        return  fichero;
    }
    private File getRuta(){
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), directorio);
            if (ruta != null){
                if (!ruta.mkdir()){
                    if (!ruta.exists()){
                        return null;
                    }
                }
            }
        }
        return ruta;
    }

    private boolean validarReporte1(){
        boolean estado = true;
        if (clientes.equals("") || fecha1.getText().toString().equals("")){
            estado = false;
        }
        return estado;
    }

    private void obtenerLista() {
        try {
            //Para Cliente
            listaCliente = new ArrayList<String>();
            ltsCliente = new ArrayList();
            ltsCliente = ClienteBo.listaCliente();
            listaCliente.add("-Selecione-");
            for (int i = 0; i < ltsCliente.size(); i++){
                listaCliente.add(ltsCliente.get(i).getRazonSocial());
            }

            //Para Calidad
            listaCalidad = new ArrayList<String>();
            listaCalidad.add("-Seleccione-"); listaCalidad.add("Primera"); listaCalidad.add("Segunda");

            //Para Mes
            listaMes = new ArrayList<String>();
            listaMes.add("-Seleccione-"); listaMes.add("Enero"); listaMes.add("Febrero"); listaMes.add("Marzo");
            listaMes.add("Abril"); listaMes.add("Mayo"); listaMes.add("Junio"); listaMes.add("Julio");
            listaMes.add("Agosto"); listaMes.add("Septiembre"); listaMes.add("Octubre");  listaMes.add("Noviembre");
            listaMes.add("Diciembre");

            //Para Proveedor
            listaProveedor = new ArrayList<String>();
            ltsProveedor = new ArrayList();
            ltsProveedor = ProveedorBo.lista_Proveedores();
            listaProveedor.add("-Selecione-");
            for (int i = 0; i < ltsProveedor.size(); i++){
                listaProveedor.add(ltsProveedor.get(i).getNombre());
            }

            //Para Categoria
            listaCategoria = new ArrayList<String>();
            listaCategoria.add("-Seleccione-"); listaCategoria.add("Amarilla"); listaCategoria.add("Blanca");
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarDe = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaCliente);
        cliente1.setAdapter(adatarDe);

        ArrayAdapter<CharSequence> adatar = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaCalidad);
        calidad1.setAdapter(adatar);

        ArrayAdapter<CharSequence> mm = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaMes);
        mes1.setAdapter(mm);

        ArrayAdapter<CharSequence> pro = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProveedor);
        proveedor1.setAdapter(pro);

        mes2.setAdapter(mm);

        ArrayAdapter<CharSequence> cat = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaCategoria);
        categoria1.setAdapter(cat);

        cliente2.setAdapter(adatarDe);
        mes3.setAdapter(mm);
    }

    private void limpiar(){
        cliente1.setSelection(0);
        fecha1.setText("");
        calidad1.setSelection(0);
        anio1.setText("");
        mes1.setSelection(0);
        proveedor1.setSelection(0);
        fecha2.setText("");
        categoria1.setSelection(0);
        fecha4.setText("");
        fecha5.setText("");
        anio3.setText("");
        mes3.setSelection(0);

        clientes = "";
        auxFecha = "";
        auxFecha1 = "";
        cal1 = "";
        messsss = 0;
        nombreP = "";
        apelP = "";
        cate = "";
    }
}