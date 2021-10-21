package com.example.lo_encontrecom.appeniclients;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QualityServiceGasFragment extends Fragment {

    /*Atributos*/
    private RatingBar ratingBar;
    Globals globals = new Globals();
    private String resultado = "";
    Button btnSend;
    int rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_quality_service_gas, container, false);

        globals.setIdgasorder(this.getArguments().getInt("idorder"));

        Log.e("last order CALIDAD", String.valueOf(globals.getIdgasorder()));
        btnSend=v.findViewById(R.id.btnSend);

        /*Pata obtener la calificación del servicio*/
        ratingBar = v.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rat, boolean b) {
                rating = (int) rat;
                //Toast.makeText(getContext(), "Rating:"+String.valueOf(rating), Toast.LENGTH_LONG).show();

                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Order order = new Order();
                        order.setIdevaluategasservice(rating);

                        QualityOrder qualityOrder = new QualityOrder();
                        qualityOrder.execute(order);
                    }
                });


            }
        });



        return v;
    }


    private class QualityOrder extends AsyncTask<Order, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Order... orders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasOrder/" + globals.getIdgasorder());
            put.setHeader("content-type", "application/json;charset=UTF-8");
            put.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {
                Order order = orders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("idevaluategasservice", String.valueOf(order.getIdevaluategasservice()));

                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
                Log.e("jsonn", String.valueOf(values));
                put.setEntity(entity);
                HttpResponse resp = httpClient.execute(put);
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
                Toast.makeText(getContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),"Servicio valorado correctamente", Toast.LENGTH_SHORT).show();

                /*Intent goMain= new Intent(MapsGasActivity.this, SelectTypeServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
                finish();*/

            }

        }

    }


}
