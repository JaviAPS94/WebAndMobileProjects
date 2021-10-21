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

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuGasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*Atributos*/
    private String resultado = "";
    Globals globals = new Globals();
    int typeService = 0;
    int idCylinder=0;
    int numberCylinder;
    Boolean orderCorrect = false;
    Boolean lastOrder=false;
    Bundle bundle = new Bundle();
    String selectedCylinderImage;

    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

        idCylinder = extras.getInt("idCylinder");
        numberCylinder = extras.getInt("numberCylinder");

        selectedCylinderImage = extras.getString("selectedCylinderImage");

        // String selectedCylinder = extras.getString("selectedCylinderName");
        globals.setIdClient(extras.getInt("idgascustomer"));
        globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
        globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
        globals.setGascustomercedruc(extras.getString("gascustomercedruc"));
        globals.setGascustomeremail(extras.getString("gascustomeremail"));
        globals.setGascustomerphone(extras.getString("gascustomerphone"));
        globals.setGascustomerpassword(extras.getString("gascustomerpassword"));
        globals.setGascustomerstatus(extras.getBoolean("gascustomerstatus"));
        globals.setTokengascustomer(extras.getString("tokengascustomer"));
        typeService = extras.getInt("typeService");

        Log.e("Tipo de Servicio select", String.valueOf(globals.getIdClient()));

       //Para enviar entre fragmentos
        bundle.putInt("idgascustomer", globals.getIdClient());
        bundle.putString("gascustomerfirstname", globals.getGascustomerfirstname());
        bundle.putString("gascustomerlastname", globals.getGascustomerlastname());
        bundle.putString("gascustomercedruc", globals.getGascustomercedruc());
        bundle.putString("gascustomeremail", globals.getGascustomeremail());
        bundle.putString("gascustomerphone", globals.getGascustomerphone());
        bundle.putString("gascustomerpassword", globals.getGascustomerpassword());
        bundle.putBoolean("gascustomerstatus", globals.isGascustomerstatus());
        bundle.putString("tokengascustomer", globals.getTokengascustomer());
        bundle.putInt("typeService", typeService);

        //Para el detalle del pedido
        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderInformationGasFragment orderInformationGasFragment = new OrderInformationGasFragment();
        orderInformationGasFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.contenedor,orderInformationGasFragment).commit();
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
        getMenuInflater().inflate(R.menu.menu_gas, menu);
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

       // Log.e("Item menu id", String.valueOf(typeItemMenu));

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(id==R.id.nav_map){

            Intent goSelecCylinder = new Intent(MenuGasActivity.this,SelectCylinderActivity.class);
            goSelecCylinder.putExtra("idCylinder",idCylinder);
            goSelecCylinder.putExtra("numberCylinder",numberCylinder);
            goSelecCylinder.putExtra("selectedCylinderImage",selectedCylinderImage);

            goSelecCylinder.putExtra("idgascustomer",globals.getIdClient());
            goSelecCylinder.putExtra("gascustomerfirstname",globals.getGascustomerfirstname());
            goSelecCylinder.putExtra("gascustomerlastname",globals.getGascustomerlastname());
            goSelecCylinder.putExtra("gascustomercedruc",globals.getGascustomercedruc());
            goSelecCylinder.putExtra("gascustomeremail",globals.getGascustomeremail());
            goSelecCylinder.putExtra("gascustomerphone",globals.getGascustomerphone());
            goSelecCylinder.putExtra("gascustomerpassword",globals.getGascustomerpassword());
            goSelecCylinder.putExtra("gascustomerstatus",globals.isGascustomerstatus());
            goSelecCylinder.putExtra("tokengascustomer",globals.getTokengascustomer());
            goSelecCylinder.putExtra("typeService",typeService);

            startActivity(goSelecCylinder);

        }else if (id == R.id.nav_status_order) {
            lastOrder=false;
            LastOrder lastOrder = new LastOrder();
            lastOrder.execute();


        } else if (id == R.id.nav_quality_service) {
            lastOrder=true;
            /*Para obtener el id de la ultima orden*/
            LastOrder lastOrder = new LastOrder();
            lastOrder.execute();


        } else if (id == R.id.nav_settings) {

            SettingsClientGasFragment settingsClientGasFragment = new SettingsClientGasFragment();
            settingsClientGasFragment.setArguments(bundle);

            Log.e("configuracion", String.valueOf(globals.getIdClient()));

            fragmentManager.beginTransaction().replace(R.id.contenedor, settingsClientGasFragment).addToBackStack(null).commit();


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

            builder.setNegativeButton(R.string.btnNo,null);

           /* builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new OrderInformationGasFragment()).addToBackStack(null).commit();


                }
            });*/
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

                    Intent goMain= new Intent(MenuGasActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(goMain);
                    Toast.makeText(MenuGasActivity.this,R.string.mjsExitoLogout, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new OrderInformationGasFragment()).addToBackStack(null).commit();

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
                Toast.makeText(MenuGasActivity.this,R.string.mjsExito, Toast.LENGTH_SHORT).show();

                Intent goMain= new Intent(MenuGasActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
                finish();

            }

        }

    }


    private class LastOrder extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(MenuGasActivity.this);
            progressDialog.setTitle("Estado del Pedido");
            progressDialog.setMessage("Cargando estado del pedido");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... datos) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasOrder/idmax?idgascustomer="+globals.getIdClient());

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                Thread.sleep(1000);
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    orderCorrect=true;

                   // Log.i("TAG", "doInBackground: holaaa");
                    Log.e("Order",  resultado);


                }else {
                    Log.e("No hay registros", "No tiene registros");
                    orderCorrect=false;
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
                progressDialog.dismiss();
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(orderCorrect){
                    try {

                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado=resultado.replaceAll(" ","");
                        Log.i("Order lim", resultado);

                        JSONObject jsonObj = new JSONObject(resultado);

                        globals.setIdgasorder(jsonObj.getInt("order"));

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        Bundle bundle = new Bundle();
                        bundle.putInt("idorder", globals.getIdgasorder());


                        if (lastOrder){

                            Log.i("Order dato", String.valueOf(globals.getIdgasorder()));
                            QualityServiceGasFragment qualityServiceGasFragment = new QualityServiceGasFragment();
                            qualityServiceGasFragment.setArguments(bundle);

                            fragmentManager.beginTransaction().replace(R.id.contenedor, qualityServiceGasFragment).addToBackStack(null).commit();
                        }else {
                            OrderStatusGasFragment orderStatusGasFragment = new OrderStatusGasFragment();
                            orderStatusGasFragment.setArguments(bundle);

                            fragmentManager.beginTransaction().replace(R.id.contenedor, orderStatusGasFragment).addToBackStack(null).commit();
                        }

                    }catch (Exception e){

                        Toast.makeText(getApplicationContext(), "Usted no ha realizado ningún pedido", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),R.string.mjsNoExistingUser, Toast.LENGTH_LONG).show();
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
