package com.example.lo_encontrecom.appeniclients;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuGranelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Globals globals = new Globals();
    private ProgressDialog progressDialog;
    String resultado = "";
    Boolean orderCorrect = false;
    int lastOrder = 0;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_granel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        shared = getSharedPreferences("Datos", MODE_PRIVATE);

        globals.setIdgranelcustomer(shared.getInt("idgranelcustomer", 0));
        globals.setGranelcustomerruc(shared.getString("granelcustomerruc", ""));
        globals.setGranelcustomerbusinesname(shared.getString("granelcustomerbusinessname", ""));
        globals.setGranelcustomeremail(shared.getString("granelcustomeremail", ""));
        globals.setGranelcustomerphonenumber(shared.getString("granelcustomerphonenumber", ""));
        globals.setGranelcustomeraddress(shared.getString("granelcustomeraddress", ""));
        globals.setGranelcustomerpassword(shared.getString("granelcustomerpassword", ""));


        Log.e("Cliente", String.valueOf(globals.getIdgranelcustomer()));


        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderGranelFragment orderGranelFragment = new OrderGranelFragment();
        fragmentManager.beginTransaction().replace(R.id.contenedor_granel, orderGranelFragment).commit();


        //Para el pedido


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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_granel, menu);
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


        FragmentManager fragmentManager = getSupportFragmentManager();


        if (id == R.id.nav_new_request) {
            OrderGranelFragment orderGranelFragment = new OrderGranelFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor_granel, orderGranelFragment).commit();

        } else if (id == R.id.nav_cancel_request) {
            lastOrder=1;
            LastOrder lastOrder = new LastOrder();
            lastOrder.execute();

        } else if (id == R.id.nav_quality_service) {
           lastOrder=2;
            LastOrder lastOrder = new LastOrder();
            lastOrder.execute();

        } else if (id == R.id.nav_sign_off) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mjsTituloLogout);
            builder.setMessage(R.string.mjsBodyLogout);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPref = getSharedPreferences("Datos", 0);

                    sharedPref.edit().clear().commit();

                    Intent goMain = new Intent(MenuGranelActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(goMain);
                    Toast.makeText(MenuGranelActivity.this, R.string.mjsExitoLogout, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor_granel, new OrderGranelFragment()).addToBackStack(null).commit();

                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class LastOrder extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "granelOrder/idmax?idgranelcustomer=" + globals.getIdgranelcustomer());

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    orderCorrect = true;

                    // Log.i("TAG", "doInBackground: holaaa");
                    Log.e("Order", resultado);


                } else {
                    Log.e("No hay registros", "No tiene registros");
                    orderCorrect = false;
                }
                return true;

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }


        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (!success) {
                Log.i("Hola", "onPostExecute: ");
                Toast.makeText(getApplicationContext(), "Error de Servidor", Toast.LENGTH_LONG).show();
            } else {
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if (orderCorrect) {
                    try {

                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado = resultado.replaceAll(" ", "");
                        Log.i("Order lim menu", resultado);

                        JSONObject jsonObj = new JSONObject(resultado);

                        globals.setIdgranelorder(jsonObj.getInt("order"));

                        Bundle bundle = new Bundle();
                        bundle.putInt("idorder", globals.getIdgranelorder());
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        if(lastOrder==1){
                            CancelRequestGranelFragment cancelRequestGranelFragment = new CancelRequestGranelFragment();
                            cancelRequestGranelFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().replace(R.id.contenedor_granel, cancelRequestGranelFragment).addToBackStack(null).commit();
                        }else {
                            QualityServiceGranelFragment qualityServiceGranelFragment = new QualityServiceGranelFragment();
                            qualityServiceGranelFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().replace(R.id.contenedor_granel, qualityServiceGranelFragment).addToBackStack(null).commit();
                        }


                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), "Usted no ha realizado ningún pedido", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No ha realizado un pedido", Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
