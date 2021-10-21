package com.loencontre.javi.myfcmexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

;import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSigin;
    String resultado="";
    Globals globals= new Globals();
    Boolean userCorrect=false;
    private SharedPreferences sharedPref;

    private EditText txtUser;
    private EditText txtPassword;

    private static final String STRING_PREFERENCES = "vale";
    private static final String PREFERENCECE_BUTTON ="estado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (obtener().equalsIgnoreCase("hola")){
            Intent goListOrder = new Intent(LoginActivity.this, ListOrderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(goListOrder);
            finish();
        }

        //SharedPreferences shared= getSharedPreferences("ArchivoSP", context.MODE_PRIVATE);
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

    // Save state of login
    public void guardar(){
        SharedPreferences shared = getSharedPreferences("Datos", 0);
        SharedPreferences.Editor editor= shared.edit();
        editor.putString("MiDato", "hola");
        editor.commit();
    }
    public String obtener (){
        SharedPreferences shared = getSharedPreferences("Datos", 0);
        String valor= shared.getString("MiDato", "No hay dato");

        Log.e("Obtner",valor );
        return valor;
        //Toast.makeText(getApplicationContext(),"Dato gaurado: "+valor, Toast.LENGTH_SHORT ).show();
    }

    private class LoginUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            String namereceptioncentergas = datos[0];
            String passwordreceptioncentergas = datos[1];

            namereceptioncentergas=namereceptioncentergas.replace(" ","");
            passwordreceptioncentergas=passwordreceptioncentergas.replace(" ","");

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasReceptionCenter?namereceptioncentergas=" + namereceptioncentergas + "&passwordreceptioncentergas=" + passwordreceptioncentergas);

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization",globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

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
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(!resultado.equalsIgnoreCase("[]")){
                    guardar();
                    try {
                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado=resultado.replaceAll(" ","");

                        Log.i("AG", "doInBackground: " + resultado);

                        JSONObject jsonObj = new JSONObject(resultado);
                        globals.setIdreceptioncentergas(jsonObj.getInt("idreceptioncentergas"));
                        globals.setNamereceptioncentergas(jsonObj.getString("namereceptioncentergas"));
                        globals.setXreceptioncentercoordinate(jsonObj.getString("xreceptioncentercoordinate"));
                        globals.setYreceptioncentercoordinate(jsonObj.getString("yreceptioncentercoordinate"));
                        globals.setTokenreceptioncentergas(jsonObj.getString("tokenreceptioncentergas"));
                        globals.setPasswordreceptioncentergas(jsonObj.getString("passwordreceptioncentergas"));

                        SharedPreferences shared = getSharedPreferences("Datos", 0);
                        SharedPreferences.Editor editor= shared.edit();
                        editor.putInt("idreceptioncentergas", globals.getIdreceptioncentergas());
                        editor.putString("namereceptioncentergas", globals.getNamereceptioncentergas());
                        editor.putString("xreceptioncentercoordinate", globals.getXreceptioncentercoordinate());
                        editor.putString("yreceptioncentercoordinate", globals.getYreceptioncentercoordinate());
                        editor.putString("tokenreceptioncentergas", globals.getTokenreceptioncentergas());
                        editor.putString("passwordreceptioncentergas", globals.getPasswordreceptioncentergas());

                        editor.commit();

                        Intent goListOrder = new Intent(LoginActivity.this, ListOrderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


                        /*goListOrder.putExtra("idreceptioncentergas",globals.getIdreceptioncentergas());
                        goListOrder.putExtra("namereceptioncentergas",globals.getNamereceptioncentergas());
                        goListOrder.putExtra("xreceptioncentercoordinate",globals.getXreceptioncentercoordinate());
                        goListOrder.putExtra("yreceptioncentercoordinate",globals.getYreceptioncentercoordinate());
                        goListOrder.putExtra("tokenreceptioncentergas",globals.getTokenreceptioncentergas());
                        goListOrder.putExtra("passwordreceptioncentergas",globals.getPasswordreceptioncentergas());

                        //Toast.makeText(getApplicationContext(),shared.getString("MiDato", ""), Toast.LENGTH_SHORT).show();
                        Log.e("valor", "onPostExecute: "+shared.getInt("idreceptioncentergas", 0));
                        //Toast.makeText(getApplicationContext(),, Toast.LENGTH_SHORT).show();*/


                        startActivity(goListOrder);
                        finish();



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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
