package com.loencontre.javi.myfcmexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.loencontre.javi.myfcmexample.globals.Globals;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    String resultado="";
    Globals globals= new Globals();
    Boolean userCorrect=false;

    private EditText txtUser;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = (txtUser.getText().toString());
                String password = (txtPassword.getText().toString());

                LoginUser login = new LoginUser();

                login.execute(user, password);

            }
        });
    }

    private class LoginUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            String namereceptioncentergas = datos[0];
            String passwordreceptioncentergas = datos[1];

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasReceptionCenter?namereceptioncentergas=" + namereceptioncentergas + "&passwordreceptioncentergas=" + passwordreceptioncentergas + "");

            get.setHeader("Authorization", "Basic dXNlcjp1c2Vy");

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity());

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    userCorrect=true;

                    Log.i("TAG", "doInBackground: holsssssaaa");
                    Log.e("TAG", "doInBackground: " + resultado);


                }else {
                    Log.e("Cuenta no registrada", "No tiene una cuenta registrada");
                    userCorrect=false;
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                Log.i("Chao", "onPostExecute: ");
                Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(!resultado.equalsIgnoreCase("[]")){

                    try {
                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado=resultado.replaceAll(" ","");

                        Log.i("AG", "doInBackground: " + resultado);

                        JSONObject jsonObj = new JSONObject(resultado);
                        globals.setIdreceptioncentergas(jsonObj.getInt("idreceptioncentergas"));
                        globals.setNamereceptioncentergas(jsonObj.getString("namereceptioncentergas"));
                        globals.setXreceptioncentercoordinate(jsonObj.getDouble("xreceptioncentercoordinate"));
                        globals.setYreceptioncentercoordinate(jsonObj.getDouble("yreceptioncentercoordinate"));
                        globals.setTokenreceptioncentergas(jsonObj.getString("tokenreceptioncentergas"));
                        globals.setPasswordreceptioncentergas(jsonObj.getString("passwordreceptioncentergas"));

                        Intent goOrder = new Intent(LoginActivity.this, OrdersActivity.class);

                        Log.e("Latitud", "onPostExecute: "+globals.getXreceptioncentercoordinate() );

                        goOrder.putExtra("idreceptioncentergas",globals.getIdreceptioncentergas());
                        goOrder.putExtra("namereceptioncentergas",globals.getNamereceptioncentergas());
                        goOrder.putExtra("xreceptioncentercoordinate",globals.getXreceptioncentercoordinate());
                        goOrder.putExtra("yreceptioncentercoordinate",globals.getYreceptioncentercoordinate());
                        goOrder.putExtra("tokenreceptioncentergas",globals.getTokenreceptioncentergas());
                        goOrder.putExtra("passwordreceptioncentergas",globals.getPasswordreceptioncentergas());

                        startActivity(goOrder);
                    }catch (Exception e){

                    }

                }else {
                    Toast.makeText(getApplicationContext(),"No tienes cuenta", Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
