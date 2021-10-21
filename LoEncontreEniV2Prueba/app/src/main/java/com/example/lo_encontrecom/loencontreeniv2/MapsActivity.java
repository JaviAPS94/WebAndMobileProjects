package com.example.lo_encontrecom.loencontreeniv2;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.entity.Order;
import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    double latitude, longitude;
    double end_latitude, end_longitude;
    TextView txtAddressActual;
    Button btnConfirmOrder;
    Geocoder geocoder;

    String idevaluategasservice, idgascylindertype,
            xgascoordinate, ygascoordinate,
            idreceptioncentergas, ordergascode,
            gasorderdate, statusgasorder;
    private String resultado = "";
    EditText txtReferencia;
    Globals globals = new Globals();
    int idClient;
    String notification="";

    ArrayList<Float> menorDistance = new ArrayList<Float>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtAddressActual=findViewById(R.id.txtAddressActual);
        txtReferencia=findViewById(R.id.txtReferencia);
        btnConfirmOrder=findViewById(R.id.btnConfirmOrder);

        this.longitude=0;
        this.latitude=0;

        ordergascode = "mSS";
        xgascoordinate = "-3.234234";
        ygascoordinate = "-234234.0";
        gasorderdate = "444644564";
        statusgasorder = "valid";

        Intent bringDateOrder = getIntent();
        Bundle extras = bringDateOrder.getExtras();

        final int idCylinder = extras.getInt("idCylinder");
        final int numberCylinder=extras.getInt("numberCylinder");

        idClient = extras.getInt("idgascustomer");
        Log.e("idclie oooo", "onCreate: "+idClient);
       // Log.e("idclie iiii", "onCreate: "+globals.getIdCliente());

        notification="{ \"notification\": \n" +
                "{\n" +
                "    \"title\": \"ENI GLP ENVASADO\",\n" +
                "    \"body\": \"Tiene una solicitud de GLP envasado\",\n" +
                "    \"sound\": \"default\",\n" +
                "    \"color\": \"#FFD700\"\n" +
                "  },\n" +
                "  \"to\" : \"fkJDXKB3ayw:APA91bHfj1g4ui3i9PlPHL-h-hQ-iG5kDp6JbHdz3bIZzCNCV7Yt1T4oGHPrH_h4RAW3QchRvMNykgUZMLTib18q_QypoMb7JjpR_0M_qAFUvAOHZSzpXc4yV12vDoYI4LbwZAaVDVNv\"\n" +
                "}";


        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle(R.string.mjsTituloPedido);
                builder.setMessage(R.string.mjsBodyPedido);
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {




                        Order order = new Order();
                        order.setIdgascylindertype(idCylinder);
                        Log.e("idclie", "onCreate: "+idClient );
                        order.setIdgascustomer(idClient);
                        Log.i("num cilinder", "onValueChange: " + numberCylinder);
                        order.setQuantitygasorder(Integer.parseInt(String.valueOf(numberCylinder)));
                        order.setOrdergascode(ordergascode);
                        order.setStatusgasorder(statusgasorder);
                        order.setXgascoordinate((float) latitude);
                        order.setYgascoordinate((float) longitude);
                        order.setGasorderdate(gasorderdate);
                        order.setReferenceorder(txtReferencia.getText().toString());

                        RegisterOrder register = new RegisterOrder();

                        register.execute(order);



                        Notification notification = new Notification();
                        notification.execute();

                        setContentView(R.layout.activity_menu);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contenedor, new EstadoPedidoFragment()).commit();

                        Toast.makeText(getApplicationContext(), R.string.mjsExitoPedido, Toast.LENGTH_SHORT).show();


                        MenorDistancia distancia = new MenorDistancia();
                        distancia.execute();

                    }
                });

                builder.setNegativeButton(R.string.btnNo, null);
                Dialog dialog = builder.create();
                dialog.show();


            }
        });





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                        if (client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else //permission is denied
                {
                    Toast.makeText(this, "Permission Denied!!", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);


            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                latitude=latLng.latitude;
                longitude=latLng.longitude;


                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                Log.e("actual ", "onLocationChanged: "+markerOptions.getPosition() );
                List<Address> addresses;

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    //String knownName = addresses.get(0).getFeatureName();
                    String knownName = addresses.get(0).getAddressLine(1);

                    txtAddressActual.setText(address);
                    Log.e("final pls", address+" "+city+" "+state+" "+country+" "+postalCode+" "+knownName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)

        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.


    }



    protected synchronized void buildGoogleApiClient () {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if (currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        this.latitude=location.getLongitude();
        this.longitude=location.getLatitude();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(15));

        
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.getPosition();

        Log.e("cordenas ", "onLocationChanged: "+markerOptions.getPosition() );

        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            txtAddressActual.setText(address);
            Log.e("txt", txtAddressActual.getText().toString());


            Log.e("Nombre Actual", address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentLocationMarker= mMap.addMarker(markerOptions);


        if (client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }



    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        end_latitude = marker.getPosition().latitude;
        end_longitude = marker.getPosition().longitude;

        Log.e("fin cordenates ", "onLocationChanged: "+end_latitude+end_longitude);
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(end_latitude, end_longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            Log.e("Nombres", address);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                values.put("idgascustomer",String.valueOf(order.getIdgascustomer()));
                //values.put("idevaluategasservice", String.valueOf(order.getIdevaluategasservice()));
                // values.put("idreceptioncentergas", String.valueOf(order.getIdreceptioncentergas()));
                values.put("quantitygasorder", String.valueOf(order.getQuantitygasorder()));
                values.put("ordergascode", order.getOrdergascode());
                values.put("statusgasorder", order.getStatusgasorder());
                values.put("xgascoordinate", String.valueOf(order.getXgascoordinate()));
                values.put("ygascoordinate", String.valueOf(order.getYgascoordinate()));
                values.put("gasorderdate", order.getGasorderdate().toString());
                values.put("referenceorder",order.getReferenceorder().toString());


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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getActivity(), 	"El resultado es: "+resultado, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


    private class Notification extends AsyncTask<Order, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Order... orders) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost( "https://fcm.googleapis.com/fcm/send");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", "key=AIzaSyBQ7iQWX8Hz5I_bC7_H7K64AAbBBM_ffcw");

            try {

                JSONObject jsonObject = new JSONObject(notification);
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(getActivity(), 	"El resultado es: "+resultado, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


    private class MenorDistancia extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            String encodedurl="|";
            String simpleUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+longitude+","+latitude+"&destinations=-0.248023,-78.508017%7C-0.241007,-78.517603%7C-0.220390,-78.507117&key=AIzaSyD4PajKaLr5GhVtSk4gGkKXp70Y9OmwY8o";
            /*try {
                encodedurl = URLEncoder.encode(simpleUrl,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(simpleUrl);



            try {
                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(get);
                resultado = EntityUtils.toString(resp.getEntity());

                Log.e("niña", "doInBackground: "+resultado);

                if (resultado != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(resultado);

                        // Getting JSON Array node
                        JSONArray rows = jsonObj.getJSONArray("rows");
                        JSONObject goElements = rows.getJSONObject(0);

                        JSONArray elements = goElements.getJSONArray("elements");

                        for (int i=0; i<elements.length();i++){

                            JSONObject goDistance = elements.getJSONObject(i);

                            JSONObject distance = goDistance.getJSONObject("distance");
                            String text = distance.getString("text");

                            text = text.replaceAll(" km", "");
                            menorDistance.add(Float.valueOf(text));


                        }
                        for (int i=0; i<menorDistance.size();i++){
                            Log.e("Arreglo ", String.valueOf(menorDistance.get(i)));
                        }

                        Collections.sort(menorDistance);
                        Log.e("Menor", String.valueOf(menorDistance.get(0)));



                    } catch (JSONException e) {
                        Log.e("Json parsing error: ",e.getMessage());
                    }

                } else {
                    Log.e("Server","Couldn't get json from server.");

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
                Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();

            }

        }

    }


}