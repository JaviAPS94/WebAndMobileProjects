package com.example.lo_encontrecom.appeniclients;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectCylinderActivity extends AppCompatActivity {

    /*Atributos*/
    ImageButton btnDomestic,btnMontaCarga,btnIndustrial15,btnIndustrial45;
    Globals globals = new Globals();
    int typeService = 0;
    private String resultado = "";
    private Boolean existeToken=false;

    private BroadcastReceiver broadcastReceiver;

    String token;
    static String tokenSer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cylinder);

        SharedPreferences shared= getSharedPreferences("Datos",MODE_PRIVATE);

        btnDomestic = findViewById(R.id.btnDomestic);
        btnMontaCarga = findViewById(R.id.btnMontaCarga);
        btnIndustrial15=findViewById(R.id.btnIndustrial15);
        btnIndustrial45=findViewById(R.id.btnIndustrial45);

        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

        // String selectedCylinder = extras.getString("selectedCylinderName");
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

        Log.e("mjs selec ID", String.valueOf(shared.getInt("idgascustomer",0)));

        Log.e("mjs selec", shared.getString("eni","") );

        tokenSer=globals.getTokengascustomer();

        //Log.e("Token clie", globals.getTokengascustomer() );



       /* globals.setIdClient(extras.getInt("idgascustomer"));
        globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
        globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
        globals.setGascustomercedruc(extras.getString("gascustomercedruc"));
        globals.setGascustomeremail(extras.getString("gascustomeremail"));
        globals.setGascustomerphone(extras.getString("gascustomerphone"));
        globals.setGascustomerpassword(extras.getString("gascustomerpassword"));
        globals.setGascustomerstatus(extras.getBoolean("gascustomerstatus"));
        globals.setTokengascustomer(extras.getString("tokengascustomer"));
        typeService = extras.getInt("typeService");  ddsdsds*/


        /*Para obtener el token*/
        //textView = (TextView) findViewById(R.id.textViewToken);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                token=SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken();
                //textView.setText(SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken());
            }
        };


        if (SharedPrefManager.getInstance(this)!=null) {
            this.token=SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken();
            //textView.setText(SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken());
        }
        //Log.d("myfcmtokenshared selec", SharedPrefManager.getInstance(this).getToken());

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        Client client = new Client();
        client.setTokengascustomer(token);

        UpdateToken updateToken = new UpdateToken();
        updateToken.execute(client);


        btnDomestic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent goOrderInformation = new Intent(SelectCylinderActivity.this, MenuGasActivity.class);
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnDomestic.getTag());

                dataClient(goOrderInformation);



                goOrderInformation.putExtra("idCylinder",1);

                startActivity(goOrderInformation);

            }
        });

        btnMontaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderActivity.this, MenuGasActivity.class);
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnMontaCarga.getTag());
                dataClient(goOrderInformation);
                goOrderInformation.putExtra("idCylinder",2);
                startActivity(goOrderInformation);
            }
        });

        btnIndustrial15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderActivity.this, MenuGasActivity.class);
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnIndustrial15.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",3);
                startActivity(goOrderInformation);
            }
        });

        btnIndustrial45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderActivity.this, MenuGasActivity.class);
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnIndustrial45.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",4);
                startActivity(goOrderInformation);
            }
        });
    }

    public void dataClient(Intent goOrderInformation){

        goOrderInformation.putExtra("idgascustomer",globals.getIdClient());
        goOrderInformation.putExtra("gascustomerfirstname",globals.getGascustomerfirstname());
        goOrderInformation.putExtra("gascustomerlastname",globals.getGascustomerlastname());
        goOrderInformation.putExtra("gascustomercedruc",globals.getGascustomercedruc());
        goOrderInformation.putExtra("gascustomeremail",globals.getGascustomeremail());
        goOrderInformation.putExtra("gascustomerphone",globals.getGascustomerphone());
        goOrderInformation.putExtra("gascustomerpassword",globals.getGascustomerpassword());
        goOrderInformation.putExtra("gascustomerstatus",globals.isGascustomerstatus());
        goOrderInformation.putExtra("typeService",typeService);
        goOrderInformation.putExtra("tokengascustomer",globals.getTokengascustomer());
    }


    private class UpdateToken extends AsyncTask<Client, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Client... clients) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasCustomer/updateToken/" + globals.getIdClient());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                Client client = clients[0];
                if (!token.equalsIgnoreCase(tokenSer)) {

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("tokengascustomer", client.getTokengascustomer());

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
                Toast.makeText(getApplicationContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
                if (existeToken) {
                    Log.e("Token", "Token actualizado");
                    //Toast.makeText(getApplicationContext(), "Token no actualizado", Toast.LENGTH_LONG).show();

                }else {
                    Log.e("Token", "Token no actualizado");
                    //Toast.makeText(getApplicationContext(), "Token actualizado", Toast.LENGTH_LONG).show();
                }

            }

        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
