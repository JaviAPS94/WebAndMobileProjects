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

import com.loencontre.javi.myfcmexample.entity.Order;
import com.loencontre.javi.myfcmexample.entity.ReceptionCenterGas;
import com.loencontre.javi.myfcmexample.globals.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrdersActivity extends AppCompatActivity {

    private TextView textView;


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

    Button btnMapa,btnProcesar;


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



        Intent bringListOrder = getIntent();
        Bundle extras = bringListOrder.getExtras();

        // String selectedCylinder = extras.getString("selectedCylinderName");
        globals.setIdreceptioncentergas(extras.getInt("idreceptioncentergas"));
        globals.setTokenreceptioncentergas(extras.getString("tokenreceptioncentergas"));
        globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
        globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
        globals.setGascustomerphone(extras.getString("gascustomerphone"));
        globals.setGascylindername(extras.getString("gascylindername"));
        globals.setQuantitygasorder(extras.getString("quantitygasorder"));
        globals.setReferenceorder(extras.getString("referenceorder"));
        globals.setXreceptioncentercoordinate(extras.getString("xgascoordinate"));
        globals.setYreceptioncentercoordinate(extras.getString("ygascoordinate"));
        globals.setIdgasorder(Integer.parseInt(extras.getString("idgasorder")));
        globals.setTokengascustomer(extras.getString("tokengascustomer"));

        Log.e("id order", String.valueOf(globals.getIdgasorder()));

        txtNombre.setText(globals.getGascustomerfirstname());
        txtNumeroTanques.setText(String.valueOf(globals.getQuantitygasorder()));
        txtReferencia.setText(globals.getReferenceorder());
        txtApellido.setText(globals.getGascustomerlastname());
        txtTelefono.setText(globals.getGascustomerphone());
        txtTipo.setText(globals.getGascylindername());

        Log.e("token", globals.getTokengascustomer());
        //Log.e("TAG Y", String.valueOf(globals.getYreceptioncentercoordinate()));




        btnMapa=findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap=new Intent(OrdersActivity.this, MapsActivity.class);
                goMap.putExtra("xgascoordinate",globals.getXreceptioncentercoordinate());
                goMap.putExtra("ygascoordinate",globals.getYreceptioncentercoordinate());
                startActivity(goMap);
            }
        });

        btnProcesar=findViewById(R.id.btnProcesar);
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setStatusgasorder("made");

                ProcesarOrder procesarOrder = new ProcesarOrder();
                procesarOrder.execute(order);

                Notification notification = new Notification();
                notification.execute();

                Intent goListOrder = new Intent(OrdersActivity.this, ListOrderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                goListOrder.putExtra("idreceptioncentergas",globals.getIdreceptioncentergas());
                goListOrder.putExtra("tokenreceptioncentergas",globals.getTokenreceptioncentergas());

                startActivity(goListOrder);
                finish();
            }
        });



    }



    private class ProcesarOrder extends AsyncTask<Order, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Order... orders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasOrder/act/" + globals.getIdgasorder());
            put.setHeader("content-type", "application/json");
            put.setHeader("Authorization",globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                Order order = orders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("statusgasorder", String.valueOf(order.getStatusgasorder()));

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString());
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Pedido procesado", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class Notification extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... tokens) {

            String notification = "{ \"notification\": \n" +
                    "{\n" +
                    "    \"title\": \"ENI GLP ENVASADO\",\n" +
                    "    \"body\": \"Su solicitud ha sido procesada\",\n" +
                    "    \"sound\": \"default\",\n" +
                    "    \"color\": \"#FFD700\"\n" +
                    "  },\n" +
                    "  \"to\" : \"" + globals.getTokengascustomer() + "\"\n" +
                    "}";

            //Log.e("token", token);
            Log.e("token", notification);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", "key=AIzaSyDMpB8qOddqDtycgGF6S08FW5-JgOY3BsU");

            try {

                JSONObject jsonObject = new JSONObject(notification);
                StringEntity entity = new StringEntity(jsonObject.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                resultado = EntityUtils.toString(resp.getEntity());

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
                Toast.makeText(getApplicationContext(),"Notificación enviada", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
