package com.example.lo_encontrecom.appeniclients;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.example.lo_encontrecom.appeniclients.entity.GranelOrder;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

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
import java.util.Properties;

import android.os.Handler;


public class OrderGranelFragment extends Fragment {


    Button btnSendEmail;
    Globals globals = new Globals();

    String correo;
    String contraseña;
    Button enviar;
    Session session;
    String resultado = "";
    String txtTimestamp;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_granel, container, false);

        SharedPreferences shared = getActivity().getSharedPreferences("Datos", 0);

        globals.setIdgranelcustomer(shared.getInt("idgranelcustomer", 0));
        globals.setGranelcustomerruc(shared.getString("granelcustomerruc", ""));
        globals.setGranelcustomerbusinesname(shared.getString("granelcustomerbusinessname", ""));
        globals.setGranelcustomeremail(shared.getString("granelcustomeremail", ""));
        globals.setGranelcustomerphonenumber(shared.getString("granelcustomerphonenumber", ""));
        globals.setGranelcustomeraddress(shared.getString("granelcustomeraddress", ""));
        globals.setGranelcustomerpassword(shared.getString("granelcustomerpassword", ""));

        correo = "enigranel@gmail.com";
        contraseña = "enigranel2018";

        /*Para obtener la fecha actual*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        txtTimestamp = dateFormat.format(date);

        btnSendEmail = v.findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enviar Correo");
                builder.setMessage("¿Está seguro que desea enviar el correo?");
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setTitle("Envío de solicitud");
                        progressDialog.setMessage("Enviando solicitud ..");
                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                        progressDialog.show();

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Properties properties = new Properties();
                        properties.put("mail.smtp.host", "smtp.googlemail.com");
                        properties.put("mail.smtp.socketFactory.port", "465");
                        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.port", "465");

                        try {
                            session = Session.getDefaultInstance(properties, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(correo, contraseña);
                                }
                            });

                            if (session != null) {

                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {

                                        try {
                                            Thread.sleep(800);
                                            javax.mail.Message message = new MimeMessage(session);
                                            message.setFrom(new InternetAddress(correo));
                                            message.setSubject("Solicitud De Granel");
                                            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("alex.pinaida@hotmail.com,alex_javi_boy94@hotmail.com"));
                                            message.setContent("<h1 style='font-family: Trebuchet MS; text-align: center;'>DATOS DE LA SOLICITUD DE GLP GRANEL</h1>" + "</br>" +
                                                    "<h2 style='font-family: Trebuchet MS;'>Datos del Cliente:</h2>" + "</br>" +
                                                    "<h3 style='font-family: Trebuchet MS;'>RUC:</h3> " + globals.getGranelcustomerruc().toString() + "</br>" +
                                                    "<h3 style='font-family: Trebuchet MS;'>Razón Social:</h3> " + globals.getGranelcustomerbusinesname().toString() + "</br>" +
                                                    "<h3 style='font-family: Trebuchet MS;'>Dirección:</h3> " + globals.getGranelcustomeraddress().toString() + "</br>" +
                                                    "<h3 style='font-family: Trebuchet MS;'>Correo:</h3> " + globals.getGranelcustomeremail().toString() + "</br>" +
                                                    "<h3 style='font-family: Trebuchet MS;'>Celular:</h3> " + globals.getGranelcustomerphonenumber().toString() + "</br>", "text/html; charset=utf-8");

                                            Transport.send(message);

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (AddressException e) {
                                            e.printStackTrace();
                                        } catch (MessagingException e) {
                                            e.printStackTrace();
                                        }

                                        GranelOrder granelOrder = new GranelOrder();
                                        granelOrder.setIdgranelcustomer(globals.getIdgranelcustomer());
                                        granelOrder.setOrdergranelcode("ENI" + globals.getIdgranelcustomer());
                                        granelOrder.setStatusgranelorder("made");
                                        granelOrder.setGranelorderdate(txtTimestamp);

                                        RegisterOrder register = new RegisterOrder();

                                        register.execute(granelOrder);
                                        progressDialog.dismiss();

                                    }
                                }).start();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                builder.setNegativeButton(R.string.btnNo, null);
                Dialog dialog = builder.create();
                dialog.show();


            }
        });

        return v;
    }


    private class RegisterOrder extends AsyncTask<GranelOrder, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(GranelOrder... granelOrders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "granelOrder");
            post.setHeader("content-type", "application/json;charset=UTF-8");
            post.setHeader("Authorization", globals.getAut());

            try {
                GranelOrder granelOrder = granelOrders[0];
                Map<String, String> values = new HashMap<String, String>();

                values.put("idgranelcustomer", String.valueOf(granelOrder.getIdgranelcustomer()));
                //values.put("idreceptioncentergranel", String.valueOf(granelOrder.getIdreceptioncentergranel()));
                values.put("ordergranelcode", String.valueOf(granelOrder.getOrdergranelcode()));
                values.put("statusgranelorder", String.valueOf(granelOrder.getStatusgranelorder()));
                values.put("granelorderdate", granelOrder.getGranelorderdate());
                //values.put("idevaluategranelorderservice", String.valueOf(granelOrder.getIdevaluategranelorderservice()));


                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
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
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getActivity(), 	"El resultado es: "+resultado, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Su solicitud ha sido ha enviada", Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), R.string.lblCancelRequest, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

}
