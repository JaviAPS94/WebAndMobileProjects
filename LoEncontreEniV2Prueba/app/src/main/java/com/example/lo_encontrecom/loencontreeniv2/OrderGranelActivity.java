package com.example.lo_encontrecom.loencontreeniv2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

import java.util.Properties;

public class OrderGranelActivity extends AppCompatActivity {

    Button btnSendEmail;
    Globals globals=new Globals();

    String correo;
    String contraseña;
    Button enviar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_granel);

        Intent bringLoginGranel = getIntent();
        Bundle extras=bringLoginGranel.getExtras();

        // String selectedCylinder = extras.getString("selectedCylinderName");

        globals.setIdgranelcustomer(extras.getInt("idgranelcustomer"));
        globals.setGranelcustomerruc(extras.getString("granelcustomerruc"));
        globals.setGranelcustomerbusinesname(extras.getString("granelcustomerbusinessname"));
        globals.setGranelcustomeremail(extras.getString("granelcustomeremail"));
        globals.setGranelcustomerphonenumber(extras.getString("granelcustomerphonenumber"));
        globals.setGranelcustomeraddress(extras.getString("granelcustomeraddress"));
        globals.setGranelcustomerpassword(extras.getString("granelcustomerpassword"));


        correo="enigranel@gmail.com";
        contraseña="enigranel2018";

        btnSendEmail=findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderGranelActivity.this);
                builder.setTitle("Enviar Correo");
                builder.setMessage("¿Está seguro que desea enviar el correo?");
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Properties properties= new Properties();
                        properties.put("mail.smtp.host","smtp.googlemail.com");
                        properties.put("mail.smtp.socketFactory.port","465");
                        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                        properties.put("mail.smtp.auth","true");
                        properties.put("mail.smtp.port","465");

                        try {
                            session=Session.getDefaultInstance(properties, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(correo,contraseña);
                                }
                            });

                            if (session!=null){
                                javax.mail.Message message= new MimeMessage(session);
                                message.setFrom(new InternetAddress(correo));
                                message.setSubject("Solicitud De Granel");
                                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("alex.pinaida@hotmail.com,alex_javi_boy94@hotmail.com"));
                                message.setContent( "<h1 style='font-family: Trebuchet MS; text-align: center;'>DATOS DE LA SOLICITUD DE GLP GRANEL</h1>"+"</br>"+
                                        "<h2 style='font-family: Trebuchet MS;'>Datos del Cliente:</h2>"+"</br>"+
                                        "<h3 style='font-family: Trebuchet MS;'>RUC:</h3> "+globals.getGranelcustomerruc().toString()+"</br>"+
                                        "<h3 style='font-family: Trebuchet MS;'>Razón Social:</h3> "+globals.getGranelcustomerbusinesname().toString()+"</br>"+
                                        "<h3 style='font-family: Trebuchet MS;'>Dirección:</h3> "+globals.getGranelcustomeraddress().toString()+"</br>"+
                                        "<h3 style='font-family: Trebuchet MS;'>Correo:</h3> "+globals.getGranelcustomeremail().toString()+"</br>"+
                                        "<h3 style='font-family: Trebuchet MS;'>Celular:</h3> "+globals.getGranelcustomerphonenumber().toString()+"</br>","text/html; charset=utf-8");

                                Transport.send(message);

                                Toast.makeText(getApplicationContext(),"El correo ha sido ha enviado", Toast.LENGTH_LONG).show();

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                builder.setNegativeButton(R.string.btnNo, null);
                Dialog dialog = builder.create();
                dialog.show();


            }
        });

    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
