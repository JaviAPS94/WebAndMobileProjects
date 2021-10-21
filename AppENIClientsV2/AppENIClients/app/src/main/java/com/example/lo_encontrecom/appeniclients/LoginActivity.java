package com.example.lo_encontrecom.appeniclients;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    /*Atributos*/
    Button btnSignIn;
    private String resultado = "";
    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin,btnForgetPassword;
    Globals globals = new Globals();
    Boolean userCorrect = false;
    int typeService = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (obtenerDatos().equalsIgnoreCase("eni")&&typeService==1){
            Intent goSelectCylinder = new Intent(LoginActivity.this, SelectCylinderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goSelectCylinder);
            finish();
        }else if(obtenerDatos().equalsIgnoreCase("eni")&&typeService==3){
            Intent goMenuLubricante = new Intent(LoginActivity.this, MenuLubricantesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goMenuLubricante);
            finish();
        }else if(obtenerDatos().equalsIgnoreCase("eni")&&typeService==2){
            Intent goMenuGranel = new Intent(LoginActivity.this, MenuGranelActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goMenuGranel);
            finish();
        }

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgetPassword=findViewById(R.id.btnForgetPassword);

        Intent bringSelectedTypeService_Or_SignIn = getIntent();
        Bundle extras = bringSelectedTypeService_Or_SignIn.getExtras();

        typeService = extras.getInt("TypeService");

        if (typeService == 1) {
            Log.e("Tipo de Servicio", "Es un cliente de GAS");
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goSignIn = new Intent(LoginActivity.this, SignInGasActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    goSignIn.putExtra("TypeService",typeService);
                    startActivity(goSignIn);
                    //finish();

                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = (txtUser.getText().toString());
                    String password = (txtPassword.getText().toString());
                    LoginUserGas_Lubricante login = new LoginUserGas_Lubricante();
                    login.execute(user, password);

                }
            });

            btnForgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goRecoverPassword=new Intent(LoginActivity.this, RecoverPasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    goRecoverPassword.putExtra("TypeService",typeService);
                    startActivity(goRecoverPassword);
                }
            });

        } else if (typeService == 2) {
            Log.e("Tipo de Servicio", "Es un cliente de Granel");
            txtUser.setInputType(InputType.TYPE_CLASS_TEXT);
            txtUser.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            txtUser.setHint("Código");

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = (txtUser.getText().toString());
                    String password = (txtPassword.getText().toString());
                    LoginUserGranel login = new LoginUserGranel();

                    login.execute(user, password);
                }
            });
            btnSignIn.setVisibility(View.INVISIBLE);
            btnForgetPassword.setVisibility(View.INVISIBLE);


        } else {
            Log.e("Tipo de Servicio", "Es un cliente de Lubricante");
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goSignIn = new Intent(LoginActivity.this, SignInGasActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    goSignIn.putExtra("TypeService",typeService);
                    startActivity(goSignIn);
                    //finish();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = (txtUser.getText().toString());
                    String password = (txtPassword.getText().toString());
                    LoginUserGas_Lubricante login = new LoginUserGas_Lubricante();
                    login.execute(user, password);

                }
            });


            btnForgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goRecoverPassword=new Intent(LoginActivity.this, RecoverPasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    goRecoverPassword.putExtra("TypeService",typeService);
                    startActivity(goRecoverPassword);
                }
            });

        }

    }

    public void guardarDatosInicio(){
        SharedPreferences shared= getSharedPreferences("Datos",MODE_PRIVATE);
        SharedPreferences.Editor editor= shared.edit();
        editor.putString("eni", "eni");
        editor.commit();
    }

    public String obtenerDatos (){
        SharedPreferences shared= getSharedPreferences("Datos",MODE_PRIVATE);
        String valor= shared.getString("eni", "no hay dato");

        Log.e("Obtner",valor );
        return valor;
        //Toast.makeText(getApplicationContext(),"Dato gaurado: "+valor, Toast.LENGTH_SHORT ).show();
    }


    private class LoginUserGas_Lubricante extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            String gascustomercedruc = datos[0];
            String gascustomerpassword = datos[1];

            gascustomercedruc=gascustomercedruc.replaceAll(" ","");
            gascustomerpassword=gascustomerpassword.replaceAll(" ","");

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasCustomer?gascustomercedruc=" + gascustomercedruc + "&gascustomerpassword=" + gascustomerpassword + "&gascustomerstatus=true");

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(),HTTP.UTF_8);

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    userCorrect = true;

                    Log.i("TAG", "doInBackground: holaaa");
                    Log.e("TAG", "doInBackground: " + resultado);


                } else {
                    Log.e("Cuenta no registrada", "No tiene una cuenta registrada");
                    userCorrect = false;
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
                Toast.makeText(getApplicationContext(), "Error de Servidor", Toast.LENGTH_LONG).show();
            } else {
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if (userCorrect) {
                    try {

                        guardarDatosInicio();

                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado = resultado.replaceAll(" ", "");
                        Log.i("AG", "doInBackground: " + resultado);

                        JSONObject jsonObj = new JSONObject(resultado);
                        globals.setIdClient(jsonObj.getInt("idgascustomer"));
                        globals.setGascustomerfirstname(jsonObj.getString("gascustomerfirstname"));
                        globals.setGascustomerlastname(jsonObj.getString("gascustomerlastname"));
                        globals.setGascustomercedruc(jsonObj.getString("gascustomercedruc"));
                        globals.setGascustomeremail(jsonObj.getString("gascustomeremail"));
                        globals.setGascustomerphone(jsonObj.getString("gascustomerphone"));
                        globals.setGascustomerpassword(jsonObj.getString("gascustomerpassword"));
                        globals.setGascustomerstatus(jsonObj.getBoolean("gascustomerstatus"));
                        globals.setTokengascustomer(jsonObj.getString("tokengascustomer"));


                        SharedPreferences shared = getSharedPreferences("Datos", 0);

                        SharedPreferences.Editor editor= shared.edit();
                        editor.putInt("idgascustomer", globals.getIdClient());
                        editor.putString("gascustomerfirstname", globals.getGascustomerfirstname());
                        editor.putString("gascustomerlastname", globals.getGascustomerlastname());
                        editor.putString("gascustomercedruc", globals.getGascustomercedruc());
                        editor.putString("gascustomeremail", globals.getGascustomeremail());
                        editor.putString("gascustomerphone", globals.getGascustomerphone());
                        editor.putString("gascustomerpassword", globals.getGascustomerpassword());
                        editor.putBoolean("gascustomerstatus",globals.isGascustomerstatus());
                        editor.putString("tokengascustomer", globals.getTokengascustomer());

                        if (typeService == 1) {

                             /*Tipo de Servicio*/
                            editor.putInt("typeService", typeService);

                            Log.e("Servicio gas", String.valueOf(typeService));

                            editor.commit();

                            Intent goSelectCylinder = new Intent(LoginActivity.this, SelectCylinderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(goSelectCylinder);

                            finish();

                        } else if(typeService==3){
                            //Para lubricantes Toast.makeText(getApplicationContext(),R.string.mjsNoExistingUser, Toast.LENGTH_LONG).show();

                            /*Tipo de Servicio*/
                            editor.putInt("typeService", typeService);
                            Log.e("Servicio lubricante", String.valueOf(typeService));
                            editor.commit();

                            Intent goMenuLubricante = new Intent(LoginActivity.this, MenuLubricantesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(goMenuLubricante);
                            finish();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Json mal estructurado", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.mjsNoExistingUser, Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    private class LoginUserGranel extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            String granelcustomercode = datos[0];
            String gascustomerpassword = datos[1];

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "granelCustomer?granelcustomercode=" + granelcustomercode + "&granelcustomerpassword=" + gascustomerpassword + "");

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

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
            String pattern1="(\"[A-z]+) +.(\\?\\![\\s])(\\?\\![A-z0-9])";
            if (!success) {
                Log.i("Hola", "onPostExecute: ");
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(userCorrect){

                    guardarDatosInicio();

                    try {
                        resultado = resultado.replaceAll("\\[", "");
                        resultado = resultado.replaceAll("\\]", "");
                        resultado=resultado.replaceAll(pattern1,"$1");
                        Log.i("AG", "doInBackground: " + resultado);

                        JSONObject jsonObj = new JSONObject(resultado);
                        globals.setIdgranelcustomer(jsonObj.getInt("idgranelcustomer"));
                        globals.setGranelcustomerruc(jsonObj.getString("granelcustomerruc"));
                        globals.setGranelcustomerbusinesname(jsonObj.getString("granelcustomerbusinessname"));
                        globals.setGranelcustomeremail(jsonObj.getString("granelcustomeremail"));
                        globals.setGranelcustomerphonenumber(jsonObj.getString("granelcustomerphonenumber"));
                        globals.setGranelcustomeraddress(jsonObj.getString("granelcustomeraddress"));
                        globals.setGranelcustomerpassword(jsonObj.getString("granelcustomerpassword"));

                        SharedPreferences shared = getSharedPreferences("Datos", 0);

                        SharedPreferences.Editor editor= shared.edit();
                        editor.putInt("idgranelcustomer",globals.getIdgranelcustomer());
                        editor.putString("granelcustomerruc",globals.getGranelcustomerruc());
                        editor.putString("granelcustomerbusinessname",globals.getGranelcustomerbusinesname());
                        editor.putString("granelcustomeremail",globals.getGranelcustomeremail());
                        editor.putString("granelcustomerphonenumber",globals.getGranelcustomerphonenumber());
                        editor.putString("granelcustomeraddress",globals.getGranelcustomeraddress());
                        editor.putString("granelcustomerpassword",globals.getGranelcustomerpassword());
                        editor.putInt("typeService", typeService);

                        editor.commit();


                        Intent goMenuGranelActivity = new Intent(LoginActivity.this, MenuGranelActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(goMenuGranelActivity);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Json mal estructurado", Toast.LENGTH_LONG).show();
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
