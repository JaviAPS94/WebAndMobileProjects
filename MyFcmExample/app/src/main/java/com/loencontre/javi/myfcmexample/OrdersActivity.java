package com.loencontre.javi.myfcmexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.loencontre.javi.myfcmexample.Adaptador.AdaptadorPedido;
import com.loencontre.javi.myfcmexample.entity.Order;
import com.loencontre.javi.myfcmexample.entity.ReceptionCenterGas;
import com.loencontre.javi.myfcmexample.globals.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import java.util.Map;

public class OrdersActivity extends AppCompatActivity {

    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    private ListView listOrder;
    Order[] orders;
    Globals globals = new Globals();
    private String resultado="";
    private String resultado1="";
    private String resultado2="";
    private String resultado3="";
    private String resultado4="";

    int quantitygasorder;
    String gascustomerNombre;
    String gascustomerApellido;
    String gascustomerTelefono;
    String gascustomerTipo;
    String gascustomerReferencia;
    //Double xgascoordinate, ygascoordinate;

    TextView txtNombre,txtNumeroTanques,txtApellido,txtTelefono,txtTipo,txtReferencia;

    String resultadAux="";

    Button btnMapa;

    String token="";
    Boolean existeToken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        txtNombre=findViewById(R.id.txtNombre);
        txtApellido=findViewById(R.id.txtApellido);
        txtTelefono=findViewById(R.id.txtCelular);
        txtTipo=findViewById(R.id.txtTipoCilindro);
        txtReferencia=findViewById(R.id.txtReferencia);
        txtNumeroTanques=findViewById(R.id.txtNumeroTanques);

        quantitygasorder=0;
        gascustomerNombre="";
        gascustomerApellido="";
        gascustomerTelefono="";
        gascustomerTipo="";
        gascustomerReferencia="";

        btnMapa=findViewById(R.id.btnMapa);

        Orders orders = new Orders();
        orders.execute();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                textView.setText(SharedPrefManager.getInstance(OrdersActivity.this).getToken());
            }
        };

        if (SharedPrefManager.getInstance(this)!=null) {
            this.token = (SharedPrefManager.getInstance(this).getToken());
        }
           //textView.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
         Log.d("myfcmtokenshared", this.token);


        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

        // String selectedCylinder = extras.getString("selectedCylinderName");
        globals.setIdreceptioncentergas(extras.getInt("idreceptioncentergas"));
        globals.setNamereceptioncentergas(extras.getString("namereceptioncentergas"));
        globals.setXreceptioncentercoordinate(extras.getDouble("xreceptioncentercoordinate"));
        globals.setYreceptioncentercoordinate(extras.getDouble("yreceptioncentercoordinate"));
        globals.setTokenreceptioncentergas(extras.getString("tokenreceptioncentergas"));
        globals.setPasswordreceptioncentergas(extras.getString("passwordreceptioncentergas"));

        Log.e("Latitud order", "onPostExecute: "+extras.getDouble("xreceptioncentercoordinate"));

        ReceptionCenterGas receptionCenterGas = new ReceptionCenterGas();
        receptionCenterGas.setNamereceptioncentergas(globals.getNamereceptioncentergas().toString());
        receptionCenterGas.setXreceptioncentercoordinate(globals.getXreceptioncentercoordinate());
        receptionCenterGas.setYreceptioncentercoordinate(globals.getYreceptioncentercoordinate());
        receptionCenterGas.setTokenreceptioncentergas(token);
        receptionCenterGas.setPasswordreceptioncentergas(globals.getPasswordreceptioncentergas().toString());

        UpdateToken token = new UpdateToken();
        token.execute(receptionCenterGas);



    }



    private class Orders extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... v) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base()+"gasOrder/find");
            HttpClient httpClient1 = new DefaultHttpClient();
            HttpGet get1 = new HttpGet(globals.getUrl_base()+"gasOrder/search");
            HttpClient httpClient2 = new DefaultHttpClient();
            HttpGet get2 = new HttpGet(globals.getUrl_base()+"gasOrder/searchlastname");
            HttpClient httpClient3 = new DefaultHttpClient();
            HttpGet get3 = new HttpGet(globals.getUrl_base()+"gasOrder/searchphone");
            HttpClient httpClient4 = new DefaultHttpClient();
            HttpGet get4 = new HttpGet(globals.getUrl_base()+"gasOrder/searchnamecylinder");

            get.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            get1.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            get2.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            get3.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            get4.setHeader("Authorization", "Basic dXNlcjp1c2Vy");

            try {


                HttpResponse resp = httpClient.execute(get);

                resultado = EntityUtils.toString(resp.getEntity());

                HttpResponse resp1 = httpClient1.execute(get1);
                resultado1 = EntityUtils.toString(resp1.getEntity());
                HttpResponse resp2 = httpClient1.execute(get2);
                resultado2 = EntityUtils.toString(resp2.getEntity());
                HttpResponse resp3 = httpClient1.execute(get3);
                resultado3 = EntityUtils.toString(resp3.getEntity());
                HttpResponse resp4 = httpClient1.execute(get4);
                resultado4 = EntityUtils.toString(resp4.getEntity());
                Log.e("ver resultado", "doInBackground: "+ resultado);
                Log.e("ver resultado1", "doInBackground: "+ resultado1);
                Log.e("ver resultado1", "doInBackground: "+ resultado2);
                Log.e("ver resultado1", "doInBackground: "+ resultado3);
                Log.e("ver resultado1", "doInBackground: "+ resultado4);
                resultadAux=resultado;
                resultado=resultado.replaceAll("\\[","");
                resultado=resultado.replaceAll("\\]","");
                resultado1=resultado1.replaceAll("\\[","");
                resultado1=resultado1.replaceAll("\\]","");
                resultado1=resultado1.replace(" ","");
                resultado1=resultado1.replaceAll("\"","");

                resultado2=resultado2.replaceAll("\\[","");
                resultado2=resultado2.replaceAll("\\]","");
                resultado2=resultado2.replace(" ","");
                resultado2=resultado2.replaceAll("\"","");

                resultado3=resultado3.replaceAll("\\[","");
                resultado3=resultado3.replaceAll("\\]","");
                resultado3=resultado3.replace(" ","");
                resultado3=resultado3.replaceAll("\"","");

                resultado4=resultado4.replaceAll("\\[","");
                resultado4=resultado4.replaceAll("\\]","");
                resultado4=resultado4.replace(" ","");
                resultado4=resultado4.replaceAll("\"","");

                Log.e("ver resultado", "doInBackground: "+ resultado);
                Log.e("ver resultado", "doInBackground: "+ resultado1);
                Log.e("ver resultado", "doInBackground: "+ resultado2);
                Log.e("ver resultado", "doInBackground: "+ resultado3);
                Log.e("ver resultado", "doInBackground: "+ resultado4);
                JSONObject jsonObj = new JSONObject(resultado);
                //JSONObject gascustomerfirstname  = jsonObj.getJSONObject("gascustomerfirstname");
                quantitygasorder=jsonObj.getInt("quantitygasorder");
                gascustomerReferencia= jsonObj.getString("referenceorder");
                gascustomerNombre=resultado1;
                gascustomerApellido=resultado2;
                gascustomerTelefono=resultado3;
                gascustomerTipo=resultado4;

                Log.e("ver resultado", "doInBackground: "+ quantitygasorder+gascustomerNombre);

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
               // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();

            }

            txtNombre.setText(gascustomerNombre);
            txtNumeroTanques.setText(String.valueOf(quantitygasorder));
            txtReferencia.setText(gascustomerReferencia);
            txtApellido.setText(gascustomerApellido);
            txtTelefono.setText(gascustomerTelefono);
            txtTipo.setText(gascustomerTipo);

            /*Intent goMa=new Intent(OrdersActivity.this, MapsActivity.class);
            goMa.putExtra("latitud",xgascoordinate);
            goMa.putExtra("longitud",ygascoordinate);
            startActivity(goMa);*/


        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

   public void goMapa(View v){
        try {

            resultadAux=resultadAux.replaceAll("\\[","");
            resultadAux=resultadAux.replaceAll("\\]","");

            JSONObject jsonObj = new JSONObject(resultadAux);
            //JSONObject gascustomerfirstname  = jsonObj.getJSONObject("gascustomerfirstname");
            quantitygasorder=jsonObj.getInt("quantitygasorder");
            gascustomerReferencia= jsonObj.getString("referenceorder");
            Double xgascoordinate=jsonObj.getDouble("xgascoordinate");
            Double ygascoordinate=jsonObj.getDouble("ygascoordinate");

            Intent goMa=new Intent(this, MapsActivity.class);
            goMa.putExtra("latitud",xgascoordinate);
            goMa.putExtra("longitud",ygascoordinate);
            startActivity(goMa);

            Log.e("TAG", "doInBackground: " + xgascoordinate);
            Log.e("TAG", "docoordenate: " + ygascoordinate);

        }catch (Exception ex){

        }
    }

    private class UpdateToken extends AsyncTask<ReceptionCenterGas, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ReceptionCenterGas... receptionCenterGases) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasReceptionCenter/update/"+globals.getIdreceptioncentergas());
            put.setHeader("content-type", "application/json");
            put.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            // post.setHeaders("content-type","application/json");


            try {
                ReceptionCenterGas receptionCenterGasesData = receptionCenterGases[0];
                if (!token.equalsIgnoreCase(globals.getTokenreceptioncentergas())) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("namereceptioncentergas", receptionCenterGasesData.getNamereceptioncentergas());
                    values.put("xreceptioncentercoordinate", String.valueOf(receptionCenterGasesData.getXreceptioncentercoordinate()));
                    values.put("yreceptioncentercoordinate", String.valueOf(receptionCenterGasesData.getYreceptioncentercoordinate()));
                    values.put("tokenreceptioncentergas", receptionCenterGasesData.getTokenreceptioncentergas());
                    values.put("passwordreceptioncentergas", receptionCenterGasesData.getPasswordreceptioncentergas());


                    JSONObject jsonObject = new JSONObject(values);
                    StringEntity entity = new StringEntity(jsonObject.toString());

                    Log.e("jsonn", String.valueOf(values));

                    put.setEntity(entity);
                    HttpResponse resp = httpClient.execute(put);
                    resultado = EntityUtils.toString(resp.getEntity());
                    existeToken = true;
                } else {
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                if (existeToken) {
                    Toast.makeText(getApplicationContext(), "Token no existente", Toast.LENGTH_LONG).show();


                }else {
                    Toast.makeText(getApplicationContext(), "Token existente", Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

}
