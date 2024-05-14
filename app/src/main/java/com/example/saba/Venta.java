package com.example.saba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saba.datos.Clientes;
import com.example.saba.datos.Compras;
import com.example.saba.datos.DetCompra;
import com.example.saba.datos.DetVenta;
import com.example.saba.datos.DetVentaDao;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.Ventas;
import com.example.saba.datos.Zarander;
import com.example.saba.negocio.CajaBo;
import com.example.saba.negocio.ClienteBo;
import com.example.saba.negocio.CompraBo;
import com.example.saba.negocio.DetCompraBo;
import com.example.saba.negocio.DetVentaBo;
import com.example.saba.negocio.ProductoBo;
import com.example.saba.negocio.VentaBo;
import com.example.saba.negocio.ZarandearBo;

import java.util.ArrayList;
import java.util.Calendar;

public class Venta extends Fragment {
    private ImageButton btnBuscar, btnEliminar, btnFecha, btnAgregar, btnQuitar, btnNuevo, btnCancelar, btnGuardar;
    private EditText ticketBusca, fecha, stock, precio, cantidad, descuento, subtotal, total, igv;
    private Spinner comboCliente, comboProduc;
    private TextView ticket;
    private TableLayout tabla;

    private ArrayList<String> listaCliente, listaProducto;
    private ArrayList<Clientes> ltsCliente = null;
    private ArrayList<Zarander> ltsProducto = null;
    private int idCliente;
    private String buscarTabla, idZarandeo, auxFecha;
    private TextView txtTabla;
    private ArrayList<String[]> fila = new ArrayList<>();
    private Clientes objC = new Clientes();
    private Empleado objE = new Empleado();
    private Ventas objV = null;
    private boolean clic = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.venta, container, false);

        ticketBusca = (EditText) view.findViewById(R.id.txt_ventTickBusca);
        btnBuscar = (ImageButton) view.findViewById(R.id.ventBusca);
        btnEliminar = (ImageButton) view.findViewById(R.id.ventEliminar);
        ticket = (TextView) view.findViewById(R.id.txt_ventTicket);
        comboCliente = (Spinner) view.findViewById(R.id.txt_ventCliente);
        fecha = (EditText) view.findViewById(R.id.txt_ventFecha);
        btnFecha = (ImageButton) view.findViewById(R.id.fechaVenta);
        comboProduc = (Spinner) view.findViewById(R.id.txt_producVenta);
        stock = (EditText) view.findViewById(R.id.txt_cantProdVent);
        precio = (EditText) view.findViewById(R.id.txt_precioProdVent);
        cantidad = (EditText)  view.findViewById(R.id.txt_cantVenta);
        descuento = (EditText) view.findViewById(R.id.txt_precioVenta);
        btnAgregar = (ImageButton) view.findViewById(R.id.agregarVenta);
        btnQuitar = (ImageButton) view.findViewById(R.id.quitarTablaVenta);
        tabla = (TableLayout) view.findViewById(R.id.tablaVenta);
        subtotal = (EditText) view.findViewById(R.id.txt_subTotalVenta);
        igv = (EditText) view.findViewById(R.id.txt_igvVenta);
        total = (EditText) view.findViewById(R.id.txt_totalVenta);
        btnNuevo = (ImageButton) view.findViewById(R.id.nuevaVenta);
        btnCancelar = (ImageButton) view.findViewById(R.id.cancelarVenta);
        btnGuardar = (ImageButton) view.findViewById(R.id.guardarVenta);

        stock.setEnabled(false);
        precio.setEnabled(false);
        fecha.setEnabled(false);
        subtotal.setEnabled(false);
        igv.setEnabled(false);
        total.setEnabled(false);
        activar(true);

        //Llenamos los combos
        obtenerLista();
        comboCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idCliente = ltsCliente.get(i - 1).getIdCliente();
                } else{
                    idCliente = 0;
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
                    buscarTabla = ltsProducto.get(i - 1).getIdProd().getIdCategoria().getNombre() + " - " + ltsProducto.get(i - 1).getIdTipo().getNombre();
                    idZarandeo = String.valueOf(ltsProducto.get(i - 1).getIdZarandear());
                    stock.setText(String.valueOf(ltsProducto.get(i - 1).getStock()));
                    precio.setText(String.valueOf(ltsProducto.get(i - 1).getPrecio()));
                } else{
                    buscarTabla = "";
                    idZarandeo = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Para Fecha
        btnFecha.setOnClickListener(new View.OnClickListener(){
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

        //Para ticket
        generarTicket();

        //Llenamos la tabla
        agregarHeadres();
        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validadAgregar()){
                    if (buscarTabla()){
                        if (Integer.parseInt(cantidad.getText().toString()) > Integer.parseInt(stock.getText().toString().trim())){
                            Toast.makeText(getContext(), "Sotck no disponible.", Toast.LENGTH_SHORT).show();
                        } else {
                            double pago = Double.parseDouble(cantidad.getText().toString().trim()) * Double.parseDouble(precio.getText().toString().trim());
                            pago = pago - (pago * (Integer.parseInt(descuento.getText().toString().trim()) * 0.01));
                            pago = redondear(pago);
                            String[] cuerpo = {buscarTabla, cantidad.getText().toString().trim(), precio.getText().toString().trim(),
                                    descuento.getText().toString().trim(), String.valueOf(pago), idZarandeo};
                            fila.add(cuerpo);
                            TableRow row = new TableRow(getContext());
                            for (int i = 0; i < 5; i++){
                                txtTabla = new TextView(getContext());
                                txtTabla.setGravity(Gravity.CENTER);
                                txtTabla.setTextSize(20);
                                txtTabla.setBackgroundColor(Color.WHITE);
                                txtTabla.setText(cuerpo[i]);
                                row.addView(txtTabla, margen());
                            }
                            tabla.addView(row);
                            limpiarListado();
                            calculaTotal();
                        }
                    } else {
                        Toast.makeText(getContext(), "El Producto ya se encuentra en operación.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ingrese los campos del Listado Producto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabla.removeAllViews();
                agregarHeadres();
                fila = new ArrayList<>();
                subtotal.setText("0.00");
                total.setText("0.00");
                if (clic == true){
                    try {
                        DetVentaBo.eliminarDetVentaa(objV.getId_Venta());
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                activar(false);
                generarTicket();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                activar(true);
                generarTicket();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketBusca.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Ingrese un ticket.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        objV = VentaBo.buscarVenta(Integer.parseInt(ticketBusca.getText().toString().trim()));
                        if (objV != null && CajaBo.buscarVentaCaja(Integer.parseInt(ticketBusca.getText().toString().trim()))){
                            activar(false);
                            ticketBusca.setText("");
                            llenarFormulario(objV);
                            clic = true;
                        } else {
                            Toast.makeText(getContext(), "No se encontro la Venta o la Venta ya fue cancelada.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketBusca.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Ingrese un ticket.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        objV = VentaBo.buscarVenta(Integer.parseInt(ticketBusca.getText().toString().trim()));
                        if (objV != null && CajaBo.buscarVentaCaja(Integer.parseInt(ticketBusca.getText().toString().trim()))){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Desea eliminar la Venta " + objV.getId_Venta() +
                                    " por el Cliente " + objV.getIdCliente().getRazonSocial());
                            builder.setTitle("Eliminar Venta");
                            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        if (VentaBo.eliminarVenta(objV)){
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
                            ticketBusca.setText("");
                        } else {
                            Toast.makeText(getContext(), "No se encontro la Venta o la Venta ya fue cancelada.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()){
                    cargarObject();
                    Ventas obj = new Ventas(0, auxFecha, Double.parseDouble(total.getText().toString()),
                            objC, objE, 0);
                    try {
                        if (objV == null){
                            if (VentaBo.grabarVenta(obj)){
                                Toast.makeText(getContext(), "Venta: Registro Correcto.", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getContext(), "Venta: No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                            //Registramos el Datalle Compra
                            for (int i = 0; i < fila.size(); i++){
                                String[] insert = fila.get(i);

                                Zarander objZ = new Zarander();
                                objZ.setIdZarandear(Integer.parseInt(insert[5]));

                                DetVenta det = new DetVenta(0, Integer.parseInt(insert[1]), Double.parseDouble(insert[2]),
                                        Integer.parseInt(insert[3]), objZ, null);
                                DetVentaBo.grabarDetVentaa(det);
                            }
                        } else {
                            obj.setId_Venta(objV.getId_Venta());
                            if (VentaBo.modificarVenta(obj)){
                                Toast.makeText(getContext(), "Venta: Registro Correcto.", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getContext(), "Venta: No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                            //Registramos el Dettale Venta
                            DetVentaBo.eliminarDetVentaa(obj.getId_Venta());
                            for (int i = 0; i < fila.size(); i++){
                                String[] insert = fila.get(i);

                                Zarander objZ = new Zarander();
                                objZ.setIdZarandear(Integer.parseInt(insert[5]));

                                DetVenta det = new DetVenta(0, Integer.parseInt(insert[1]), Double.parseDouble(insert[2]),
                                        Integer.parseInt(insert[3]), objZ, null);
                                DetVentaBo.grabarDetVentaa(det);
                            }
                        }
                        limpiar();
                        activar(true);
                        generarTicket();
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

    private void generarTicket() {
        int tick = 0;
        try {
            tick = VentaBo.numTicket();
        } catch (Exception e) {
        }

        if (tick == 0) {
            ticket.setText("1");
        } else {
            tick = tick + 1;
            ticket.setText(String.valueOf(tick));
        }
    }

    private void obtenerLista() {
        try {
            // Para Producto
            listaProducto = new ArrayList<String>();
            ltsProducto = new ArrayList();
            ltsProducto = ZarandearBo.lista_ZarandeoCombo();
            listaProducto.add("-Seleccione-");
            for (int i = 0; i < ltsProducto.size(); i++){
                listaProducto.add(ltsProducto.get(i).getIdProd().getIdCategoria().getNombre() + " - " + ltsProducto.get(i).getIdTipo().getNombre());
            }

            //Para Proveedor
            listaCliente = new ArrayList<String>();
            ltsCliente = new ArrayList();
            ltsCliente = ClienteBo.listaCliente();
            listaCliente.add("-Selecione-");
            for (int i = 0; i < ltsCliente.size(); i++){
                listaCliente.add(ltsCliente.get(i).getRazonSocial());
            }

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }

    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaProducto);
        comboProduc.setAdapter(adatarD);

        ArrayAdapter<CharSequence> adatarDe = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaCliente);
        comboCliente.setAdapter(adatarDe);
    }

    private void agregarHeadres(){
        String[] encabezado = {"Soya", "Cant.", "Precio", "Descuento", "SubTotal"};
        TableRow row = new TableRow(getContext());
        for (int i = 0; i < 5; i++){
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

    private  void limpiarListado(){
        comboProduc.setSelection(0);
        stock.setText("");
        precio.setText("");
        cantidad.setText("");
        descuento.setText("");
        buscarTabla = "";
    }

    private boolean validadAgregar(){
        boolean estado = true;
        if (cantidad.getText().toString().trim().equals("") || descuento.getText().toString().trim().equals("") || buscarTabla.equals("")){
            estado = false;
        }
        return estado;
    }

    private boolean buscarTabla(){
        boolean estado = true;
        if (fila.size() != 0){
            for (int i = 0; i < fila.size(); i++){
                String[] busca = fila.get(i);
                if (busca[0].equals(buscarTabla)){
                    estado = false;
                }
            }
        }
        return estado;
    }

    private void cargarObject(){
        objC.setIdCliente(idCliente);

        Bundle recibe = getActivity().getIntent().getExtras();
        int idE = Integer.parseInt(recibe.getString("idEmp"));
        objE.setIdEmpleado(idE);
    }

    private boolean validar(){
        boolean estado = true;
        if (idCliente == 0 || fecha.getText().toString().trim().equals("")
                || fila.size() == 0){
            estado = false;
        }
        return  estado;
    }

    private void limpiar(){
        comboCliente.setSelection(0);
        fecha.setText("");
        comboProduc.setSelection(0);
        cantidad.setText("");
        stock.setText("");
        precio.setText("");
        descuento.setText("");
        subtotal.setText("");
        total.setText("");

        tabla.removeAllViews();
        agregarHeadres();
        fila = new ArrayList<>();

        idCliente = 0;
        buscarTabla = "";
        idZarandeo = "";
        objV = null;
        clic = false;
    }

    private void activar(boolean estado){
        ticketBusca.setEnabled(estado);
        btnBuscar.setEnabled(estado);
        btnEliminar.setEnabled(estado);
        btnNuevo.setEnabled(estado);

        comboCliente.setEnabled(!estado);
        btnFecha.setEnabled(!estado);
        comboProduc.setEnabled(!estado);
        cantidad.setEnabled(!estado);
        descuento.setEnabled(!estado);
        btnAgregar.setEnabled(!estado);
        btnQuitar.setEnabled(!estado);
        btnCancelar.setEnabled(!estado);
        btnGuardar.setEnabled(!estado);
    }

    private double redondear(double num) {
        return Math.rint(num * 100) / 100;
    }

    private void calculaTotal() {
        double S = 0;
        for (int i = 0; i < fila.size(); i++) {
            String[] busca = fila.get(i);
            S = S + Double.parseDouble(busca[4]);
        }
        S = redondear(S);
        subtotal.setText(String.valueOf(S));

        total.setText(String.valueOf(S));
    }

    private void llenarFormulario(Ventas obj) {
        ticket.setText(String.valueOf(obj.getId_Venta()));
        comboCliente.setSelection(obj.getIdCliente().getIdCliente());
        fecha.setText(obj.getFecha());
        subtotal.setText(String.valueOf(obj.getPagar()));
        total.setText(String.valueOf(obj.getPagar()));
        auxFecha = obj.getFecha();
        try {
            ArrayList<DetVenta> det = DetVentaBo.lista_DetVenta(objV.getId_Venta());
            for (DetVenta DC: det){
                String soya = DC.getIdProdZar().getIdProd().getIdCategoria().getNombre() + " - " + DC.getIdProdZar().getIdTipo().getNombre();
                double sub = DC.getIdProdZar().getPrecio() * DC.getCantidad();
                sub = sub - (sub * (DC.getDescuento() * 0.01));
                sub = redondear(sub);
                String[] cuerpo = {soya, String.valueOf(DC.getCantidad()),
                        String.valueOf(DC.getIdProdZar().getPrecio()), String.valueOf(DC.getDescuento()),
                        String.valueOf(sub),String.valueOf(DC.getIdProdZar().getIdZarandear())};
                fila.add(cuerpo);
                TableRow row = new TableRow(getContext());
                for (int i = 0; i < 5; i++){
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
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}