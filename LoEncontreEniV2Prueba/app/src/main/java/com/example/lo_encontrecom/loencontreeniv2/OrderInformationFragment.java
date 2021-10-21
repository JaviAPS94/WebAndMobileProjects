package com.example.lo_encontrecom.loencontreeniv2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.entity.Client;
import com.example.lo_encontrecom.loencontreeniv2.entity.Order;
import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OrderInformationFragment extends Fragment {

    private String resultado = "";
    TextView txtSelectedCylinder, txtAddressActual, txtAddressActual1;
    ImageView icon_cylinder;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String idevaluategasservice, idgascylindertype,
            xgascoordinate, ygascoordinate,
            idreceptioncentergas, ordergascode,
            gasorderdate, statusgasorder;
    Globals globals = new Globals();
    int numCylinder = 0;

    Button btnChange;

    double longitude, latitude;

    public OrderInformationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_order_information, container, false);

        txtSelectedCylinder = v.findViewById(R.id.txtSelectedCylinder);
        icon_cylinder = v.findViewById(R.id.icon_cylinder);
        ordergascode = "mSS";
        xgascoordinate = "-3.234234";
        ygascoordinate = "-234234.0";
        gasorderdate = "444644564";
        statusgasorder = "valid";


        //Get the widgets reference from XML layout
        final TextView txtNumberCylinderSelected = (TextView) v.findViewById(R.id.txtNumberCylinderSelected);
        NumberPicker txtNumberCylinder = (NumberPicker) v.findViewById(R.id.txtNumberCylinder);

        //Set TextView text color
        txtNumberCylinderSelected.setTextColor(Color.parseColor("#1a0dab"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        txtNumberCylinder.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        txtNumberCylinder.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        txtNumberCylinder.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        txtNumberCylinder.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                //txtNumberCylinderSelected.setText(""+newVal);
                numCylinder = newVal;


            }
        });

        Intent bringSelectedCylinderType = getActivity().getIntent();
        Bundle extras = bringSelectedCylinderType.getExtras();

        String selectedCylinder = extras.getString("selectedCylinderName");
        String selectedCylinderImage = extras.getString("selectedCylinderImage");
        final int idCylinder = extras.getInt("idCylinder");

        globals.setIdClient(extras.getInt("idgascustomer"));

        txtSelectedCylinder.setText(selectedCylinder);
        icon_cylinder.setImageResource(this.getResources().getIdentifier(selectedCylinderImage, "drawable", this.getActivity().getPackageName()));
        Log.i("TAGgg", "onCreate: " + selectedCylinderImage);



        /*btnAccept = v.findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.mjsTituloPedido);
                builder.setMessage(R.string.mjsBodyPedido);
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Order order = new Order();
                        order.setIdgascylindertype(idCylinder);
                        Log.i("num cilinder", "onValueChange: " + numCylinder);
                        order.setQuantitygasorder(Integer.parseInt(String.valueOf(numCylinder)));
                        order.setOrdergascode(ordergascode);
                        order.setStatusgasorder(statusgasorder);
                        order.setXgascoordinate(Float.parseFloat(xgascoordinate));
                        order.setYgascoordinate(Float.parseFloat(ygascoordinate));
                        order.setGasorderdate(gasorderdate);

                        RegisterOrder register = new RegisterOrder();

                        register.execute(order);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contenedor, new EstadoPedidoFragment()).addToBackStack(null).commit();


                        Toast.makeText(getActivity(), R.string.mjsExitoPedido, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(R.string.btnNo, null);
                Dialog dialog = builder.create();
                dialog.show();


            }
        });*/

        txtAddressActual1 = v.findViewById(R.id.txtAddressActual1);
        txtAddressActual = v.findViewById(R.id.txtAddressActual);

        longitude = 0;
        latitude = 0;

        btnChange = v.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goMaps = new Intent(getActivity(), MapsActivity.class);
                goMaps.putExtra("idCylinder",idCylinder);
                goMaps.putExtra("numberCylinder",numCylinder);
                goMaps.putExtra("idgascustomer", globals.getIdClient());
                startActivity(goMaps);

            }
        });


        return v;
    }


    private class RegisterOrder extends AsyncTask<Order, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Order... orders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "gasOrder");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", "Basic dXNlcjp1c2Vy");

            try {
                Order order = orders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("idgascylindertype", String.valueOf(order.getIdgascylindertype()));
                //values.put("idevaluategasservice", String.valueOf(order.getIdevaluategasservice()));
                // values.put("idreceptioncentergas", String.valueOf(order.getIdreceptioncentergas()));
                values.put("quantitygasorder", String.valueOf(order.getQuantitygasorder()));
                values.put("ordergascode", order.getOrdergascode());
                values.put("statusgasorder", order.getStatusgasorder());
                values.put("xgascoordinate", String.valueOf(order.getXgascoordinate()));
                values.put("ygascoordinate", String.valueOf(order.getYgascoordinate()));
                values.put("gasorderdate", order.getGasorderdate().toString());
              /*  try {
                    Date date = formato.parse(order.getGasorderdate().toString());
                    values.put("gasorderdate", String.valueOf(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/


                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString());
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
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getActivity(), 	"El resultado es: "+resultado, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


}
