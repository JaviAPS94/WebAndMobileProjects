package com.eni.lo_encontrecom.appenicenterlubricant;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.eni.lo_encontrecom.appenicenterlubricant.entity.NumClientLubricant;
import com.eni.lo_encontrecom.appenicenterlubricant.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SelectTypeActivity extends AppCompatActivity {

    /*Atributos*/
    RadioButton radioButtonApp;
    RadioButton radioButtonOtro;
    Button btnSend;
    Globals globals = new Globals();
    String resultado = "";
    String txtTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        btnSend = findViewById(R.id.btnSend);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        txtTimestamp = dateFormat.format(date);

        radioButtonApp = findViewById(R.id.radioButtonApp);
        radioButtonOtro = findViewById(R.id.radioButtonOtro);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumClientLubricant numClientLubricant = new NumClientLubricant();
                if (radioButtonApp.isChecked()){
                    numClientLubricant.setTipoorderlubricant(true);
                    numClientLubricant.setDateorderlubricant(txtTimestamp);
                    RegisterMetodo registerMetodo = new RegisterMetodo();
                    registerMetodo.execute(numClientLubricant);
                }else if (radioButtonOtro.isChecked()){
                    numClientLubricant.setTipoorderlubricant(false);
                    numClientLubricant.setDateorderlubricant(txtTimestamp);
                    RegisterMetodo registerMetodo = new RegisterMetodo();
                    registerMetodo.execute(numClientLubricant);
                }

            }
        });

    }

    private class RegisterMetodo extends AsyncTask<NumClientLubricant, Void, Boolean> {

        @Override
        protected Boolean doInBackground(NumClientLubricant... numClientLubricants) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "numClient");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            Log.e("Llego", "ff" );

            try {

                NumClientLubricant numClientLubricant = numClientLubricants[0];

                Map<String, String> values = new HashMap<String, String>();
                values.put("tipoorderlubricant", String.valueOf(numClientLubricant.getTipoorderlubricant()));
                values.put("dateorderlubricant", numClientLubricant.getDateorderlubricant());

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString());
                Log.e("jsonn", String.valueOf(values));
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
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
                // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), R.string.mjsRegistroExito, Toast.LENGTH_LONG).show();
                Intent goPanelPrincipal= new Intent(SelectTypeActivity.this, panel_principal.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goPanelPrincipal);
                finish();

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


}
