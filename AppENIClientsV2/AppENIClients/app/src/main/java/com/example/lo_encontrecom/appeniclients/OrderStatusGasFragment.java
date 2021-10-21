package com.example.lo_encontrecom.appeniclients;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

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

public class OrderStatusGasFragment extends Fragment {

    Globals globals = new Globals();
    private String resultado = "";
    Button btnCancelOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_status_gas, container, false);
        btnCancelOrder=v.findViewById(R.id.btnCancelOrder);
        btnCancelOrder.setEnabled(false);

        globals.setIdgasorder(this.getArguments().getInt("idorder"));

        Log.e("last order", String.valueOf(globals.getIdgasorder()));

        SearchOrder searchOrder = new SearchOrder();
        searchOrder.execute();


        return v;
    }

    private class CancelOrder extends AsyncTask<Order, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Order... orders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasOrder/act/" + globals.getIdgasorder());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                Order order = orders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("statusgasorder", String.valueOf(order.getStatusgasorder()));

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
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
                Toast.makeText(getContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),"Su orden ha sido cancelada", Toast.LENGTH_SHORT).show();

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
            HttpGet get = new HttpGet(globals.getUrl_base() + "gasOrder/searchOrder?idgasorder=" + globals.getIdgasorder());

            get.setHeader("content-type", "application/json;charset=UTF-8");
            get.setHeader("Authorization", globals.getAut());

            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);

                Log.e("TAG", "doInBackground: " + resultado);

                resultado = resultado.replaceAll("\\[", "");
                resultado = resultado.replaceAll("\\]", "");
                resultado=resultado.replaceAll("\"","");
                resultado=resultado.replaceAll(" ","");


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

                if(resultado.equalsIgnoreCase("pend")){
                    btnCancelOrder.setEnabled(true);
                    btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Order order = new Order();
                            order.setStatusgasorder("can");

                            CancelOrder cancelOrder = new CancelOrder();
                            cancelOrder.execute(order);
                        }
                    });
                }else {
                    btnCancelOrder.setEnabled(false);
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
