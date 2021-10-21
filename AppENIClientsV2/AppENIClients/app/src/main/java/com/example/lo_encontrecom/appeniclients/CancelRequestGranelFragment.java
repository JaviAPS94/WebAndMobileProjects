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

import com.example.lo_encontrecom.appeniclients.entity.GranelOrder;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class CancelRequestGranelFragment extends Fragment {

    Button btnCancelRequest;
    Globals globals = new Globals();

    String correo;
    String contraseña;
    Button enviar;
    Session session;
    String resultado = "";
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cancel_request_granel, container, false);


        SharedPreferences shared = getActivity().getSharedPreferences("Datos", 0);

        globals.setIdgranelcustomer(shared.getInt("idgranelcustomer", 0));
        globals.setGranelcustomerruc(shared.getString("granelcustomerruc", ""));
        globals.setGranelcustomerbusinesname(shared.getString("granelcustomerbusinessname", ""));
        globals.setGranelcustomeremail(shared.getString("granelcustomeremail", ""));
        globals.setGranelcustomerphonenumber(shared.getString("granelcustomerphonenumber", ""));
        globals.setGranelcustomeraddress(shared.getString("granelcustomeraddress", ""));
        globals.setGranelcustomerpassword(shared.getString("granelcustomerpassword", ""));


        globals.setIdgranelorder(this.getArguments().getInt("idorder"));


        Log.e("last order granel", String.valueOf(globals.getIdgranelorder()));

        correo = "enigranel@gmail.com";
        contraseña = "enigranel2018";

        btnCancelRequest = v.findViewById(R.id.btnCancelRequest);
        btnCancelRequest.setEnabled(false);

        SearchOrder searchOrder = new SearchOrder();
        searchOrder.execute();


        return v;
    }


    private class CancelOrder extends AsyncTask<GranelOrder, Void, Boolean> {
        @Override
        protected Boolean doInBackground(GranelOrder... granelOrders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "granelOrder/act/" + globals.getIdgranelorder());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                GranelOrder granelOrder = granelOrders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("statusgranelorder", String.valueOf(granelOrder.getStatusgranelorder()));

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
                Log.e("jsonn GRANEL", String.valueOf(values));
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
                Toast.makeText(getContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getContext(), "Solicitud cancelada", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                OrderGranelFragment orderGranelFragment = new OrderGranelFragment();
                fragmentManager.beginTransaction().replace(R.id.contenedor_granel, orderGranelFragment).commit();

                /*Intent goMain= new Intent(MapsGasActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
                finish();*/

            }

        }

    }

    private class SearchOrder extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... datos) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(globals.getUrl_base() + "granelOrder/searchOrder?idgranelorder=" + globals.getIdgranelorder());

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                Log.e("ESTADO order granel", resultado);

                resultado = resultado.replaceAll("\\[", "");
                resultado = resultado.replaceAll("\\]", "");
                resultado = resultado.replaceAll("\"", "");
                resultado = resultado.replaceAll(" ", "");


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
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            } else {

                Log.i("AG", "doInBackground: " + resultado);

                if (resultado.equalsIgnoreCase("made")) {
                    btnCancelRequest.setEnabled(true);
                    btnCancelRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Enviar Correo");
                            builder.setMessage("¿Está seguro que desea enviar el correo?");
                            builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    progressDialog = new ProgressDialog(getContext());
                                    progressDialog.setTitle("Cancelar solicitud");
                                    progressDialog.setMessage("Cancelando solicitud ..");
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
                                                        message.setSubject("Cancelación de solicitud de granel");
                                                        message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("alex.pinaida@hotmail.com,alex_javi_boy94@hotmail.com"));
                                                        message.setContent("<h1 style='font-family: Trebuchet MS; text-align: center;'>DATOS DE LA CANCELACIÓN DEL PEDIDO</h1>" + "</br>" +
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
                                                    granelOrder.setStatusgranelorder("can");

                                                    CancelOrder cancelOrder = new CancelOrder();
                                                    cancelOrder.execute(granelOrder);
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

                } else {
                    btnCancelRequest.setEnabled(false);
                    Toast.makeText(getContext(), "No tiene pepidos por valorar", Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


}
