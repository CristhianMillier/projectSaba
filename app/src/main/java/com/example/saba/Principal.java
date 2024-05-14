package com.example.saba;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saba.databinding.ActivityPrincipalBinding;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipal.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.compra, R.id.cliente, R.id.proveedor, R.id.zarandear, R.id.venta, R.id.caja, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        agregarEmcabezado();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Para cerrar Seccion
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void agregarEmcabezado(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View cebezera = navigationView.getHeaderView(0);
        TextView nombre = cebezera.findViewById(R.id.txt_nombre);
        TextView apellido = cebezera.findViewById(R.id.txt_ape);
        TextView cargo = cebezera.findViewById(R.id.txt_cargoo);

        Bundle recibe = this.getIntent().getExtras();
        if (recibe != null){
            String n = recibe.getString("nombre");
            String a = recibe.getString("apellido");
            String c = recibe.getString("cargo");
            nombre.setText(n);
            apellido.setText(a);
            cargo.setText(c);
        }
    }
}