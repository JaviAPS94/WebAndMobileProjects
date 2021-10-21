package com.loencontre.javi.myfcmexample;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.loencontre.javi.myfcmexample.entity.Order;
import com.loencontre.javi.myfcmexample.entity.ReceptionCenterGas;
import com.loencontre.javi.myfcmexample.globals.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ListOrderActivity extends AppCompatActivity {

    private ListView listOrder;
    private TextView json;
    Order[] orders;
    Globals globals = new Globals();
    private String resultado = "";
    String returned = "";
    String token="";
    Boolean existeToken = false;
    private BroadcastReceiver broadcastReceiver;


    ArrayList<HashMap<String, String>> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        SharedPreferences shared = getSharedPreferences("Datos", MODE_PRIVATE);
        // json = findViewById(R.id.json);
        final Context context = this;
        orderList = new ArrayList<>();
        listOrder = findViewById(R.id.listOrder);

        //Orders orders = new Orders();
        //orders.execute();


        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

        // String selectedCylinder = extras.getString("selectedCylinderName");
        //Log.e("valor shared", "onItemClick: "+ );
        globals.setIdreceptioncentergas(shared.getInt("idreceptioncentergas", 0));

        globals.setTokenreceptioncentergas(shared.getString("tokenreceptioncentergas", ""));


        Log.e("ID CA", String.valueOf(globals.getIdreceptioncentergas()));

        listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Estructura Adaptador personalizado
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e("Datos orden", String.valueOf(position));
                Log.e("Array", String.valueOf(orderList.get(position)));


                //String valor1= shared.getString("namereceptioncentergas", "No hay dato");


                HashMap<String, String> tmpData = (HashMap<String, String>) orderList.get(position);
                tmpData.get("gascylindername");

                Log.e("Nombre cilindro", tmpData.get("gascylindername"));

                Intent goDatosOrder = new Intent(view.getContext(), OrdersActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                goDatosOrder.putExtra("idgasorder", tmpData.get("idgasorder"));
                goDatosOrder.putExtra("gascustomerfirstname", tmpData.get("gascustomerfirstname"));
                goDatosOrder.putExtra("gascustomerlastname", tmpData.get("gascustomerlastname"));
                goDatosOrder.putExtra("gascustomerphone", tmpData.get("gascustomerphone"));
                goDatosOrder.putExtra("gascylindername", tmpData.get("gascylindername"));
                goDatosOrder.putExtra("quantitygasorder", tmpData.get("quantitygasorder"));
                goDatosOrder.putExtra("referenceorder", tmpData.get("referenceorder"));
                goDatosOrder.putExtra("xgascoordinate", tmpData.get("xgascoordinate"));
                goDatosOrder.putExtra("ygascoordinate", tmpData.get("ygascoordinate"));
                goDatosOrder.putExtra("tokengascustomer", tmpData.get("tokengascustomer"));

                Log.e("x cilindro", tmpData.get("tokengascustomer"));

                goDatosOrder.putExtra("tokenreceptioncentergas", globals.getTokenreceptioncentergas());
                goDatosOrder.putExtra("idreceptioncentergas", globals.getIdreceptioncentergas());
                startActivity(goDatosOrder);
                //finish();

            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                token=(SharedPrefManager.getInstance(ListOrderActivity.this).getToken());
            }
        };

        if (SharedPrefManager.getInstance(this)!=null) {
            this.token = (SharedPrefManager.getInstance(this).getToken());
        }
        //textView.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
        Log.d("myfcmtokenshared", this.token);


        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        ReceptionCenterGas receptionCenterGas = new ReceptionCenterGas();
        receptionCenterGas.setTokenreceptioncentergas(token);

        UpdateToken token = new UpdateToken();
        token.execute(receptionCenterGas);

    }

    @Override
    protected void onResume() {
        super.onResume();
        orderList.clear();
        Orders orders = new Orders();
        orders.execute();
    }

    @Override
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setTitle(R.string.mjsTituloLogout);
            builder.setMessage(R.string.mjsBodyLogout);
            builder.setNegativeButton(R.string.btnNo, null);
            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPref = getSharedPreferences("Datos", 0);

                    sharedPref.edit().clear().commit();

                    Intent goMain = new Intent(ListOrderActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(goMain);
                    Toast.makeText(ListOrderActivity.this, R.string.mjsExitoLogout, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


            Dialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Orders extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... v) {

            String pattern1 = "\\},\\s*\\{";
            String pattern2 = "(\\[)\\s*\\[";
            String pattern3 = "(\\])\\s*\\]";
            String pattern4 = "\\],\\s*\\[";
            String pattern5 = "\"idgascustomer\": *[0-9]+,\\s*(\"gascustomerstatus\")";
            String pattern6 = "\"idgascylindertype\": *[0-9]+,\\s*(\"gascylindername\")";
            String pattern7 = "(\"[A-z]+) +.(\\?\\![\\s])(\\?\\![A-z0-9])";

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasOrder/test?idreceptioncentergas=" + globals.getIdreceptioncentergas());

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {

                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                Log.e("Espacios pri", resultado + " fin");

                returned = resultado;
                returned = returned.replaceAll(pattern1, ",");
                returned = returned.replaceAll(pattern2, "$1");
                returned = returned.replaceAll(pattern3, "$1");
                returned = returned.replaceAll(pattern4, ",");
                returned = returned.replaceAll(pattern5, "$1");
                returned = returned.replaceAll(pattern6, "$1");
                returned = returned.replaceAll(pattern7, "$1");

                Log.e("Espacios", returned + " fin");

                if (returned != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(returned);

                        JSONArray orders = jsonObj.getJSONArray("order");

                        for (int i = 0; i < orders.length(); i++) {
                            JSONObject o = orders.getJSONObject(i);
                            int idgasorder = o.getInt("idgasorder");
                            int idgascylindertype = o.getInt("idgascylindertype");
                            //int idevaluategasservice = o.getInt("idevaluategasservice");
                            //int idreceptioncentergas = o.getInt("idreceptioncentergas");
                            String quantitygasorder = o.getString("quantitygasorder");
                            int idgascustomer = o.getInt("idgascustomer");
                            String ordergascode = o.getString("ordergascode");
                            Double xgascoordinate = o.getDouble("xgascoordinate");
                            Double ygascoordinate = o.getDouble("ygascoordinate");
                            String gasorderdate = o.getString("gasorderdate");
                            String referenceorder = o.getString("referenceorder");
                            String gascustomerfirstname = o.getString("gascustomerfirstname");
                            String gascustomerlastname = o.getString("gascustomerlastname");
                            String gascustomercedruc = o.getString("gascustomercedruc");
                            String gascustomeremail = o.getString("gascustomeremail");
                            String gascustomerphone = o.getString("gascustomerphone");
                            String gascylindername = o.getString("gascylindername");
                            String tokengascustomer = o.getString("tokengascustomer");

                            HashMap<String, String> order = new HashMap<>();

                            order.put("idgasorder", String.valueOf(idgasorder));
                            order.put("quantitygasorder", quantitygasorder);
                            order.put("xgascoordinate", String.valueOf(xgascoordinate));
                            order.put("ygascoordinate", String.valueOf(ygascoordinate));
                            order.put("referenceorder", referenceorder);
                            order.put("gascustomerfirstname", gascustomerfirstname);
                            order.put("gascustomerlastname", gascustomerlastname);
                            order.put("gascustomercedruc", gascustomercedruc);
                            order.put("gascustomeremail", gascustomeremail);
                            order.put("gascustomerphone", gascustomerphone);
                            order.put("gascylindername", gascylindername);
                            order.put("tokengascustomer", tokengascustomer);


                            orderList.add(order);
                            Log.e("Array", String.valueOf(orderList.get(i)));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success == false) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {

                //Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                ListAdapter adapter = new SimpleAdapter(ListOrderActivity.this, orderList, R.layout.list_order, new String[]{"gascustomerfirstname", "gascustomerlastname", "gascylindername"},
                        new int[]{R.id.txtFirstName, R.id.txtLastName, R.id.txtAddres});
                listOrder.setAdapter(adapter);

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }

    private class UpdateToken extends AsyncTask<ReceptionCenterGas, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ReceptionCenterGas... receptionCenterGases) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasReceptionCenter/update/" + globals.getIdreceptioncentergas());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                ReceptionCenterGas receptionCenterGas = receptionCenterGases[0];
                if (!token.equalsIgnoreCase(globals.getTokenreceptioncentergas())) {

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("tokenreceptioncentergas", receptionCenterGas.getTokenreceptioncentergas());

                    JSONObject jsonObject = new JSONObject(values);
                    StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");

                    Log.e("jsonn", String.valueOf(values));
                    put.setEntity(entity);
                    HttpResponse resp = httpClient.execute(put);
                    resultado = EntityUtils.toString(resp.getEntity());
                    existeToken = true;
                }else {
                    Log.e("Error", "datos vacios");
                    existeToken = false;
                }


                return true;

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == false) {
                Toast.makeText(getApplicationContext(), "Error de Servidor", Toast.LENGTH_LONG).show();
            } else {
                if (existeToken) {
                    Log.e("Token", "Token actualizado");
                    //Toast.makeText(getApplicationContext(), "Token no existente", Toast.LENGTH_LONG).show();

                }else {
                    Log.e("Token", "Token no actualizado");
                    //Toast.makeText(getApplicationContext(), "Token existente", Toast.LENGTH_LONG).show();
                }

            }

        }

    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
