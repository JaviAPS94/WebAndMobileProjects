package com.example.lo_encontrecom.appeniclients;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecoverPasswordActivity extends AppCompatActivity {

    /*Atributos*/
    private String resultado = "";
    Globals globals = new Globals();
    EditText txtCedulaRUC;
    Button btnSend;
    String cedulaRUC;
    Boolean userExists;
    Session session;

    String correo;
    String contraseña;

    int typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        txtCedulaRUC=findViewById(R.id.txtCedulaRUC);
        btnSend=findViewById(R.id.btnSend);

        correo="enigranel@gmail.com";
        contraseña="enigranel2018";

        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

        typeService=extras.getInt("TypeService");

        if(typeService==1 || typeService==3){
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cedulaRUC=txtCedulaRUC.getText().toString();

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RecoverPasswordActivity.this,R.style.MyDialogTheme);
                    builder.setTitle("Recuperar contraseña");
                    builder.setMessage("¿Está seguro que desea enviar el correo de recuperación de contraseña?");
                    builder.setNegativeButton(R.string.btnNo, null);
                    builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent bringSelectedTypeService = getIntent();
                            Bundle extras = bringSelectedTypeService.getExtras();
                            int typeService = extras.getInt("TypeService");

                            RecoverPasswordEmail recoverPasswordEmail = new RecoverPasswordEmail();
                            recoverPasswordEmail.execute(cedulaRUC);

                            Toast.makeText(getApplicationContext(), "Su contraseña ha sido enviada a su correo", Toast.LENGTH_LONG).show();
                            Intent goLogin = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                            goLogin.putExtra("TypeService",typeService);

                        }
                    });


                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    private class RecoverPasswordEmail extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {
            String gascustomercedruc = datos[0];
            gascustomercedruc=gascustomercedruc.replaceAll(" ","");

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasCustomer/searchEmail?gascustomercedruc="+gascustomercedruc);

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    userExists=true;

                    // Log.i("TAG", "doInBackground: holaaa");
                    Log.e("Order",  resultado);


                }else {
                    Log.e("No hay registros", "No tiene registros");
                    userExists=false;
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
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(userExists){


                    resultado = resultado.replaceAll("\\[", "");
                    resultado = resultado.replaceAll("\\]", "");
                    resultado=resultado.replaceAll(" ","");
                    resultado=resultado.replaceAll("\"","");
                    Log.i("Password y email", resultado);

                    String [] emailPas = resultado.split(",");

                    Log.e("CORREO", emailPas[0]);


                    StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Properties properties= new Properties();
                    properties.put("mail.smtp.host","smtp.googlemail.com");
                    properties.put("mail.smtp.socketFactory.port","465");
                    properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.port","465");

                    try {
                        session= Session.getDefaultInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(correo,contraseña);
                            }
                        });

                        if (session!=null){
                            javax.mail.Message message= new MimeMessage(session);
                            message.setFrom(new InternetAddress(correo));
                            message.setSubject("Recuperación de contraseña");
                            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(emailPas[0]));
                            message.setContent( "<h1 style='font-family: Trebuchet MS; text-align: center;'>DATOS DE RECUPERACIÓN DE CONTRASEÑA</h1>"+"</br>"+
                                    "<h2 style='font-family: Trebuchet MS;'>Estimado usuario, la contraseña de su cuenta ENI es: </h2>"+"</br>"+
                                    "<h3 style='font-family: Trebuchet MS;'> </h3> "+emailPas[1]+"</br>","text/html; charset=utf-8");

                            Transport.send(message);

                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),R.string.mjsExistingUser, Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


   /* private class RecoverPasswordEmailGranel extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {
            String gascustomercedruc = datos[0];
            gascustomercedruc=gascustomercedruc.replaceAll(" ","");

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasCustomer/searchEmail?gascustomercedruc="+gascustomercedruc);

            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity());

                //  Log.i("Aqui estoy", "onPostExecute: ");

                if (!resultado.equalsIgnoreCase("[]")) {
                    userExists=true;

                    // Log.i("TAG", "doInBackground: holaaa");
                    Log.e("Order",  resultado);


                }else {
                    Log.e("No hay registros", "No tiene registros");
                    userExists=false;
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
                Log.i("Chao", "onPostExecute: ");
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();
                if(userExists){


                    resultado = resultado.replaceAll("\\[", "");
                    resultado = resultado.replaceAll("\\]", "");
                    resultado=resultado.replaceAll(" ","");
                    resultado=resultado.replaceAll("\"","");
                    Log.i("Password y email", resultado);

                    String [] emailPas = resultado.split(",");

                    Log.e("CORREO", emailPas[0]);


                    StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Properties properties= new Properties();
                    properties.put("mail.smtp.host","smtp.googlemail.com");
                    properties.put("mail.smtp.socketFactory.port","465");
                    properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.port","465");

                    try {
                        session= Session.getDefaultInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(correo,contraseña);
                            }
                        });

                        if (session!=null){
                            javax.mail.Message message= new MimeMessage(session);
                            message.setFrom(new InternetAddress(correo));
                            message.setSubject("Solicitud De Granel");
                            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(emailPas[0]));
                            message.setContent( "<h1 style='font-family: Trebuchet MS; text-align: center;'>DATOS DE RECUPERACIÓN DE CONTRASEÑA</h1>"+"</br>"+
                                    "<h2 style='font-family: Trebuchet MS;'>Estimado usuario, la contraseña de su cuenta ENI es: </h2>"+"</br>"+
                                    "<h3 style='font-family: Trebuchet MS;'>Contraseña:</h3> "+emailPas[1]+"</br>","text/html; charset=utf-8");

                            Transport.send(message);

                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),R.string.mjsExistingUser, Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }*/

}
