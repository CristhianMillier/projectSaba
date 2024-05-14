package com.example.saba;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.example.saba.datos.Cajas;
import com.example.saba.datos.CierreCaja;
import com.example.saba.datos.Comprobante;
import com.example.saba.datos.DetCierre;
import com.example.saba.datos.DetVenta;
import com.example.saba.datos.Empleado;
import com.example.saba.datos.Ventas;
import com.example.saba.negocio.CajaBo;
import com.example.saba.negocio.CierreCajaBo;
import com.example.saba.negocio.ComprobanteBo;
import com.example.saba.negocio.DetCierreBo;
import com.example.saba.negocio.DetVentaBo;
import com.example.saba.negocio.VentaBo;
import com.example.saba.negocio.ZarandearBo;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Timestamp;

public class Caja extends Fragment {
    private Button btnAbrir, btnCerrar;
    private EditText ticket, numComp, serieComp, fecha;
    private ImageButton btnBuscar, btnFecha, btnLimpiar, btnGuardar;
    private Spinner comboComp;
    private TextView razonSocial, numDoc, pago;

    private ArrayList<String> listaComprobante;
    private ArrayList<Comprobante> ltsComprobante = null;
    private Ventas objV = null;
    private int idComprobante = 0;
    private String auxFecha = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caja, container, false);

        btnAbrir = (Button) view.findViewById(R.id.btn_AbrirCaja);
        btnCerrar = (Button) view.findViewById(R.id.btn_CierreCaja);
        ticket = (EditText) view.findViewById(R.id.txt_ticket);
        btnBuscar = (ImageButton) view.findViewById(R.id.buscarCaja);
        comboComp = (Spinner) view.findViewById(R.id.txt_Comprobante);
        numComp = (EditText) view.findViewById(R.id.txt_NumComprobante);
        serieComp = (EditText) view.findViewById(R.id.txt_SerieComprobante);
        fecha = (EditText) view.findViewById(R.id.txt_fechaCaja);
        btnFecha = (ImageButton) view.findViewById(R.id.fechaCaja);
        razonSocial = (TextView) view.findViewById(R.id.txt_Razón);
        numDoc = (TextView) view.findViewById(R.id.txt_nroDocCaja);
        pago = (TextView) view.findViewById(R.id.txt_pagoCaja);
        btnLimpiar = (ImageButton) view.findViewById(R.id.limpiarCaja);
        btnGuardar = (ImageButton) view.findViewById(R.id.guardarCaja);

        fecha.setEnabled(false);
        darPermisos();

        //Llenamos el combo
        obtenerLista();
        comboComp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    idComprobante = ltsComprobante.get(i - 1).getIdComprobante();
                } else{
                    idComprobante = 0;
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

        //Eventos
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticket.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Ingrese el Nro. Ticket.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        objV = VentaBo.buscarVenta(Integer.parseInt(ticket.getText().toString().trim()));
                        if (objV != null && CajaBo.buscarVentaCaja(Integer.parseInt(ticket.getText().toString().trim()))){
                            activar(false);
                            cargarFormulario(objV);
                        } else{
                            Toast.makeText(getContext(), "No se encontro la Venta o la Venta ya fue cancelada.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activar(true);
                limpiar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comprobante objCom = new Comprobante();
                objCom.setIdComprobante(idComprobante);

                Bundle recibe = getActivity().getIntent().getExtras();
                int idE = Integer.parseInt(recibe.getString("idEmp"));

                Cajas obj = new Cajas(0, serieComp.getText().toString().trim(), numComp.getText().toString().trim(),
                        auxFecha, Double.parseDouble(pago.getText().toString()), objV, objCom, 0, idE);
                if (idComprobante != 0){
                    if (validarComporbante()){
                        try {
                            if (CajaBo.grabarCaja(obj)){
                                Toast.makeText(getContext(), "Registro exitoso.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "No se pudo registrar.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Ingrese correctamente los campos comprobante.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (fecha.getText().toString().trim().equals("")){
                        Toast.makeText(getContext(), "Seleccione una Fecha.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (CajaBo.grabarCajaSinComp(obj)){
                                Toast.makeText(getContext(), "Registro exitoso.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "No se pudo registrar.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                editarStock();
                limpiar();
                activar(true);
            }
        });

        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                String date = anio + "-" + mes + "-" + dia;

                try {
                    CierreCaja objcierre = new CierreCaja(0, date, null, 0, null);
                    if (CierreCajaBo.grabarCierre(objcierre)){
                        Toast.makeText(getContext(), "Caja abierta exitosamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se puedo abrir la Caja.", Toast.LENGTH_SHORT).show();
                    }
                    activar(true);
                    btnCerrar.setEnabled(true);
                    btnAbrir.setEnabled(false);
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                Timestamp fecha_hora = new Timestamp(cal.getTimeInMillis());
                String fechaSistema = String.valueOf(fecha_hora);
                String anio = fechaSistema.substring(0, 4);
                String mes = fechaSistema.substring(5, 7);
                String dia = fechaSistema.substring(8, 10);
                String date = anio + "-" + mes + "-" + dia;
                int hora = Integer.parseInt(fechaSistema.substring(11, 13));
                if (hora >= 11){
                    try {
                        ArrayList<Cajas> lisCaja = CajaBo.lista_Caja();
                        ArrayList<Cajas> lisCajaCierrre = new ArrayList();
                        for (Cajas C: lisCaja){
                            DetCierre objDC = CierreCajaBo.buscarDetCierre(C.getIdCaja());
                            if (objDC == null){
                                lisCajaCierrre.add(C);
                            }
                        }
                        if (lisCajaCierrre.size() > 0){
                            double pagooo = CierreCajaBo.cierreCaja();
                            int idCierre = CierreCajaBo.buscarCierre();

                            Bundle recibe = getActivity().getIntent().getExtras();
                            int idE = Integer.parseInt(recibe.getString("idEmp"));
                            Empleado objE = new Empleado();
                            objE.setIdEmpleado(idE);

                            CierreCaja objca = new CierreCaja(idCierre, "", date, pagooo, objE);
                            if (CierreCajaBo.modificarCierre(objca)){
                                Toast.makeText(getContext(), "Caja cerrada.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "No se pudo cerrar la Caja.", Toast.LENGTH_SHORT).show();
                            }
                            for (Cajas C: lisCajaCierrre){
                                CierreCajaBo.CajaCerrada(C);
                                DetCierre detCierre = new DetCierre(0, C, idCierre);
                                DetCierreBo.grabarDetCierre(detCierre);
                            }
                            activar(true);
                            btnCerrar.setEnabled(false);
                            btnAbrir.setEnabled(true);
                            ticket.setEnabled(false);
                            btnBuscar.setEnabled(false);
                        } else {
                            Toast.makeText(getContext(), "La caja ya ha sido cerrada.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No se realizar el Cierre de caja ya que aún no son las 8:00 PM.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void obtenerLista() {
        try {
            listaComprobante = new ArrayList<String>();
            ltsComprobante = new ArrayList();
            ltsComprobante= ComprobanteBo.lista_Comprobante();
            listaComprobante.add("-Seleccione-");
            for (int i = 0; i < ltsComprobante.size(); i++){
                listaComprobante.add(ltsComprobante.get(i).getNombre());
            }
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        llenarCombos();
    }
    private void llenarCombos() {
        ArrayAdapter<CharSequence> adatarD = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listaComprobante);
        comboComp.setAdapter(adatarD);
    }

    private void darPermisos() {
        Bundle recibe = getActivity().getIntent().getExtras();
        String cargo = recibe.getString("cargo");
        switch (cargo){
            case "Vendedor":
                desactivar();
                break;
            case "Zarandeador":
                desactivar();
                break;
            default:
                try {
                    if (CajaBo.validarAbrirCaja()){
                        btnCerrar.setEnabled(false);
                        ticket.setEnabled(false);
                        btnBuscar.setEnabled(false);
                        comboComp.setEnabled(false);
                        numComp.setEnabled(false);
                        serieComp.setEnabled(false);
                        btnFecha.setEnabled(false);
                        btnLimpiar.setEnabled(false);
                        btnGuardar.setEnabled(false);
                    } else {
                        btnAbrir.setEnabled(false);
                        activar(true);
                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void activar(boolean estado){
        ticket.setEnabled(estado);
        btnBuscar.setEnabled(estado);

        comboComp.setEnabled(!estado);
        numComp.setEnabled(!estado);
        serieComp.setEnabled(!estado);
        btnFecha.setEnabled(!estado);
        btnLimpiar.setEnabled(!estado);
        btnGuardar.setEnabled(!estado);
    }

    private void desactivar(){
        Toast.makeText(getContext(), "Usted no puede gestionar la Caja.", Toast.LENGTH_SHORT).show();
        btnAbrir.setEnabled(false);
        btnCerrar.setEnabled(false);
        ticket.setEnabled(false);
        btnBuscar.setEnabled(false);
        comboComp.setEnabled(false);
        numComp.setEnabled(false);
        serieComp.setEnabled(false);
        btnFecha.setEnabled(false);
        btnLimpiar.setEnabled(false);
        btnGuardar.setEnabled(false);
    }

    private void limpiar(){
        ticket.setText("");
        comboComp.setSelection(0);
        numComp.setText("");
        serieComp.setText("");
        fecha.setText("");
        razonSocial.setText("");
        numDoc.setText("");
        pago.setText("");

        objV = null;
        idComprobante = 0;
        auxFecha = "";
    }

    private void cargarFormulario(Ventas objV){
        razonSocial.setText(objV.getIdCliente().getRazonSocial());
        numDoc.setText(objV.getIdCliente().getNroDoc());
        pago.setText(String.valueOf(objV.getPagar()));
    }

    private boolean validarGeneral(){
        boolean estado = true;
        if (idComprobante == 0 || numComp.getText().toString().trim().equals("")
                || serieComp.getText().toString().trim().equals("") || fecha.getText().toString().trim().equals("")){
            estado = false;
        }
        return estado;
    }

    private boolean validarComporbante(){
        boolean estado = true;
        if (numComp.getText().toString().trim().length() != 6
                || serieComp.getText().toString().trim().length() != 3){
            estado = false;
        }
        return estado;
    }

    private void editarStock(){
        try {
            ArrayList<DetVenta> detVenta = DetVentaBo.lista_DetVenta(objV.getId_Venta());
            for (DetVenta D: detVenta){
                ZarandearBo.actualizarZarandear(D.getIdProdZar(), -D.getCantidad());
            }
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}