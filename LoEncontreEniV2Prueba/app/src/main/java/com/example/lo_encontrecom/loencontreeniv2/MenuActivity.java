package com.example.lo_encontrecom.loencontreeniv2;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Globals globals = new Globals();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Para el detalle del pedido
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new OrderInformationFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_estado_pedido) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new EstadoPedidoFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_cancelar_pedido) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mjsTituloCancelOrder);
            builder.setMessage(R.string.mjsBodyCancelOrder);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent goSelectCylinderType= new Intent(MenuActivity.this, SelectCylinderTypeActivity.class);
                    startActivity(goSelectCylinderType);
                    Toast.makeText(MenuActivity.this,R.string.mjsExitoCancelOrder, Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton(R.string.btnNo, null);
            Dialog dialog = builder.create();
            dialog.show();
        }else if (id==R.id.nav_valorar_servicio){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new QualityServiceFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_configuracion) {


            Intent bringLogin = getIntent();
            Bundle extras = bringLogin.getExtras();

            // String selectedCylinder = extras.getString("selectedCylinderName");
            globals.setIdClient(extras.getInt("idgascustomer"));
            globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
            globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
            globals.setGascustomercedruc(extras.getString("gascustomercedruc"));
            globals.setGascustomeremail(extras.getString("gascustomeremail"));
            globals.setGascustomerphone(extras.getString("gascustomerphone"));
            globals.setGascustomerpassword(extras.getString("gascustomerpassword"));

            Bundle bundle = new Bundle();
            bundle.putInt("idgascustomer", globals.getIdClient());
            bundle.putString("gascustomerfirstname",globals.getGascustomerfirstname());
            bundle.putString("gascustomerlastname",globals.getGascustomerlastname());
            bundle.putString("gascustomercedruc",globals.getGascustomercedruc());
            bundle.putString("gascustomeremail",globals.getGascustomeremail());
            bundle.putString("gascustomerphone",globals.getGascustomerphone());
            bundle.putString("gascustomerpassword",globals.getGascustomerpassword());

            ConfigurationCustomerInformationFragment configurationCustomerInformation= new ConfigurationCustomerInformationFragment();
            configurationCustomerInformation.setArguments(bundle);

            Log.e("configuracion", "onNavigationItemSelected: "+globals.getIdClient());


            fragmentManager.beginTransaction().replace(R.id.contenedor,configurationCustomerInformation).addToBackStack(null).commit();



        } else if (id == R.id.nav_cancelar_cuenta) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mjsTitulo);
            builder.setMessage(R.string.mjsBody);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent goMain= new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(goMain);
                    Toast.makeText(MenuActivity.this,R.string.mjsExito, Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton(R.string.btnNo, null);
            Dialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.nav_salir) {
            Intent goMain= new Intent(MenuActivity.this, MainActivity.class);
            startActivity(goMain);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
