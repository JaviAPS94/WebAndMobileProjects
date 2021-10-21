package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.entity.Client;
import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login_Activity extends AppCompatActivity {

    private String resultado = "";
    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin;
    Globals globals = new Globals();
    Boolean userCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
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

    public void signIn(View v) {
        Intent goSignIn = new Intent(this, SignInActivity.class);
        startActivity(goSignIn);
    }

    public void selectCylinderType(View view) {
        Intent goSelectCylinderType = new Intent(this, SelectCylinderTypeActivity.class);
        startActivity(goSelectCylinderType);
    }

    private class LoginUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            String gascustomercedruc = datos[0];
            String gascustomerpassword = datos[1];

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasCustomer?gascustomercedruc=" + gascustomercedruc + "&gascustomerpassword=" + gascustomerpassword + "");

            get.setHeader("Authorization", "Basic dXNlcjp1c2Vy");

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity());

              //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    userCorrect=true;

                    Log.i("TAG", "doInBackground: holaaa");
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

                    try {
                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado=resultado.replaceAll(" ","");
                        Log.i("AG", "doInBackground: " + resultado);

                        JSONObject jsonObj = new JSONObject(resultado);
                        globals.setIdClient(jsonObj.getInt("idgascustomer"));
                        globals.setGascustomerfirstname(jsonObj.getString("gascustomerfirstname"));
                        globals.setGascustomerlastname(jsonObj.getString("gascustomerlastname"));
                        globals.setGascustomercedruc(jsonObj.getString("gascustomercedruc"));
                        globals.setGascustomeremail(jsonObj.getString("gascustomeremail"));
                        globals.setGascustomerphone(jsonObj.getString("gascustomerphone"));
                        globals.setGascustomerpassword(jsonObj.getString("gascustomerpassword"));

                        Intent goSelectCylinderType = new Intent(Login_Activity.this, SelectCylinderTypeActivity.class);
                        goSelectCylinderType.putExtra("idgascustomer",globals.getIdClient());
                        goSelectCylinderType.putExtra("gascustomerfirstname",globals.getGascustomerfirstname());
                        goSelectCylinderType.putExtra("gascustomerlastname",globals.getGascustomerlastname());
                        goSelectCylinderType.putExtra("gascustomercedruc",globals.getGascustomercedruc());
                        goSelectCylinderType.putExtra("gascustomeremail",globals.getGascustomeremail());
                        goSelectCylinderType.putExtra("gascustomerphone",globals.getGascustomerphone());
                        goSelectCylinderType.putExtra("gascustomerpassword",globals.getGascustomerpassword());

                        startActivity(goSelectCylinderType);
                    }catch (Exception e){

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

}
