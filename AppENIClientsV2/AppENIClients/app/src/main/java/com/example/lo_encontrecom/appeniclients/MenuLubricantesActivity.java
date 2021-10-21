package com.example.lo_encontrecom.appeniclients;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;
import com.google.android.gms.maps.SupportMapFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuLubricantesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*Atributos*/
    Globals globals = new Globals();
    int typeService = 0;
    private String resultado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lubricantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor_lubricante, new MapsLubricanteFragment()).commit();
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lubricantes, menu);
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences shared= getSharedPreferences("Datos",MODE_PRIVATE);

        globals.setIdClient(shared.getInt("idgascustomer",0));
        globals.setGascustomerfirstname(shared.getString("gascustomerfirstname",""));
        globals.setGascustomerlastname(shared.getString("gascustomerlastname",""));
        globals.setGascustomercedruc(shared.getString("gascustomercedruc",""));
        globals.setGascustomeremail(shared.getString("gascustomeremail",""));
        globals.setGascustomerphone(shared.getString("gascustomerphone",""));
        globals.setGascustomerpassword(shared.getString("gascustomerpassword",""));
        globals.setGascustomerstatus(shared.getBoolean("gascustomerstatus",false));
        globals.setTokengascustomer(shared.getString("tokengascustomer",""));

        typeService = shared.getInt("typeService",0);

        FragmentManager fragmentManager = getSupportFragmentManager();


        Log.e("Tipo de Servicio 2", String.valueOf(typeService));


        Bundle bundle = new Bundle();
        bundle.putInt("idgascustomer", globals.getIdClient());
        bundle.putString("gascustomerfirstname",globals.getGascustomerfirstname());
        bundle.putString("gascustomerlastname",globals.getGascustomerlastname());
        bundle.putString("gascustomercedruc",globals.getGascustomercedruc());
        bundle.putString("gascustomeremail",globals.getGascustomeremail());
        bundle.putString("gascustomerphone",globals.getGascustomerphone());
        bundle.putString("gascustomerpassword",globals.getGascustomerpassword());
        bundle.putBoolean("gascustomerstatus", globals.isGascustomerstatus());
        bundle.putInt("typeService",typeService);

        if (id==R.id.nav_map){

        }else if (id == R.id.nav_settings) {

            SettingsClientGasFragment settingsClientGasFragment = new SettingsClientGasFragment();
            settingsClientGasFragment.setArguments(bundle);

            Log.e("configuracion", String.valueOf(globals.getIdClient()));

            fragmentManager.beginTransaction().replace(R.id.contenedor_lubricante, settingsClientGasFragment).addToBackStack(null).commit();

        } else if (id == R.id.nav_cancel_account) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mjsTitulo);
            builder.setMessage(R.string.mjsBody);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Client client = new Client();
                    client.setGascustomerstatus(false);
                    DarDeBajaCuenta darDeBajaCuenta = new DarDeBajaCuenta();
                    darDeBajaCuenta.execute(client);
                }
            });

            builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor_lubricante, new MapsLubricanteFragment()).addToBackStack(null).commit();


                }
            });
            Dialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.nav_sign_off) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mjsTituloLogout);
            builder.setMessage(R.string.mjsBodyLogout);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SharedPreferences sharedPref = getSharedPreferences("Datos", 0);

                    sharedPref.edit().clear().commit();
                    Intent goMain= new Intent(MenuLubricantesActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(goMain);
                    Toast.makeText(MenuLubricantesActivity.this,R.string.mjsExitoLogout, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor_lubricante, new MapsLubricanteFragment()).addToBackStack(null).commit();

                }
            });
            Dialog dialog = builder.create();
            dialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DarDeBajaCuenta extends AsyncTask<Client, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Client... clients) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasCustomer/" + globals.getIdClient());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                Client client = clients[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("gascustomerstatus", String.valueOf(client.isGascustomerstatus()));

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
                Log.e("jsonn", String.valueOf(values));
                put.setEntity(entity);
                HttpResponse resp = httpClient.execute(put);
                resultado = EntityUtils.toString(resp.getEntity());

                return true;

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == false) {
                Toast.makeText(getApplicationContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MenuLubricantesActivity.this,R.string.mjsExito, Toast.LENGTH_SHORT).show();

                Intent goMain= new Intent(MenuLubricantesActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
                finish();

            }

        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
