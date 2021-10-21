package com.example.lo_encontrecom.appeniclients;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.entity.Order;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsGasActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

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

    String  ordergascode,  gasorderdate, statusgasorder;
    private String resultado = "";
    EditText txtReference;
    Globals globals = new Globals();
    int idClient;
    int idCylinder;
    int numberCylinder;
    int idMenorDistancia;
    String selectedCylinderImage;
    String token;


    Map menorDisatance = new HashMap<Integer,Double>();
    Boolean existeToken = false;
    int typeService=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_gas);

        Intent bringDateOrder = getIntent();
        Bundle extras = bringDateOrder.getExtras();

        idCylinder = extras.getInt("idCylinder");
        numberCylinder = extras.getInt("numberCylinder");

        selectedCylinderImage = extras.getString("selectedCylinderImage");

        idClient = extras.getInt("idgascustomer");


        globals.setIdClient(extras.getInt("idgascustomer"));
        globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
        globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
        globals.setGascustomercedruc(extras.getString("gascustomercedruc"));
        globals.setGascustomeremail(extras.getString("gascustomeremail"));
        globals.setGascustomerphone(extras.getString("gascustomerphone"));
        globals.setGascustomerpassword(extras.getString("gascustomerpassword"));
        globals.setGascustomerstatus(extras.getBoolean("gascustomerstatus"));
        globals.setTokengascustomer(extras.getString("tokengascustomer"));
        typeService = extras.getInt("typeService");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtAddressActual = findViewById(R.id.txtAddressActual);
        txtReference = findViewById(R.id.txtReference);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

       // this.longitude = 0;
        //this.latitude = 0;

        ordergascode = "ENI";
         /*Para obtener la fecha actual*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        gasorderdate = dateFormat.format(date);

        statusgasorder = "pend";



        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsGasActivity.this,R.style.MyDialogTheme);
                builder.setTitle(R.string.mjsTituloPedido);
                builder.setMessage(R.string.mjsBodyPedido);
                builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        GetTokenMenorDistancia notificacion = new GetTokenMenorDistancia();
                        notificacion.execute();



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
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else //permission is denied
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }
        } else {
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

                latitude = latLng.longitude;
                longitude = latLng.latitude;

                Log.e("Latx x ", String.valueOf(latitude));
                Log.e("Laty y", String.valueOf(longitude));


                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

               // Log.e("actual ", "onLocationChanged: " + markerOptions.getPosition());
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
                    Log.e("final pls", address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName);
                    MenorDistancia distancia = new MenorDistancia();
                    distancia.execute();

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


    protected synchronized void buildGoogleApiClient() {
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

        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        this.latitude = location.getLongitude();
        this.longitude = location.getLatitude();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(15));


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.getPosition();

        Log.e("cordenas ", "onLocationChanged: " + markerOptions.getPosition());

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

            MenorDistancia distancia = new MenorDistancia();
            distancia.execute();
            Log.e("idMenorDistanciaTest", String.valueOf(idMenorDistancia));


        } catch (IOException e) {
            e.printStackTrace();
        }
        currentLocationMarker = mMap.addMarker(markerOptions);


        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        } else
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

        Log.e("fin cordenates ", "onLocationChanged: " + end_latitude + end_longitude);
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
            post.setHeader("content-type", "application/json;charset=UTF-8");
            post.setHeader("Authorization", globals.getAut());

            try {
                Order order = orders[0];
                Map<String, String> values = new HashMap<String, String>();
                values.put("idgascylindertype", String.valueOf(order.getIdgascylindertype()));
                values.put("idgascustomer", String.valueOf(order.getIdgascustomer()));
                //values.put("idevaluategasservice", String.valueOf(order.getIdevaluategasservice()));
                values.put("idreceptioncentergas", String.valueOf(order.getIdreceptioncentergas()));
                values.put("quantitygasorder", String.valueOf(order.getQuantitygasorder()));
                values.put("ordergascode", order.getOrdergascode());
                values.put("statusgasorder", order.getStatusgasorder());
                values.put("xgascoordinate", String.valueOf(order.getXgascoordinate()));
                values.put("ygascoordinate", String.valueOf(order.getYgascoordinate()));
                values.put("gasorderdate", order.getGasorderdate().toString());
                values.put("referenceorder", order.getReferenceorder().toString());


                JSONObject jsonObject = new JSONObject(values);
                StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
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

            Log.e("Latx dis x ", String.valueOf(latitude));
            Log.e("Laty dis y", String.valueOf(longitude));

            String coordenadas = "-0.194420,-78.488534%7C-0.244029,-78.513614";

            HttpClient httpClient = new DefaultHttpClient();
            String simpleUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + longitude + "," + latitude + "&destinations=" + coordenadas + "&key=AIzaSyAVkq2vAco7hfgnSXSqTb4aeoLoXPlnoaY";
            HttpGet getMap = new HttpGet(simpleUrl);

            Log.e("url dis", simpleUrl );

            try {

                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(getMap);
                resultado = EntityUtils.toString(resp.getEntity());

                Log.e("niña", "doInBackground: " + resultado);

                if (resultado != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(resultado);

                        // Getting JSON Array node
                        JSONArray rows = jsonObj.getJSONArray("rows");
                        JSONObject goElements = rows.getJSONObject(0);

                        JSONArray elements = goElements.getJSONArray("elements");

                        for (int i = 1; i <= elements.length(); i++) {

                            JSONObject goDistance = elements.getJSONObject(i - 1);

                            JSONObject distance = goDistance.getJSONObject("distance");
                            String text = distance.getString("text");

                            text = text.replaceAll(" km", "");

                            Log.e("id", String.valueOf(i));
                            Log.e("dis", text);

                            menorDisatance.put(i, Double.parseDouble(text));
                            //menorDistance.add(Float.valueOf(text));


                        }

                        HashMap mapResultado = new LinkedHashMap();
                        List misMapKeys = new ArrayList(menorDisatance.keySet());
                        List misMapValues = new ArrayList(menorDisatance.values());
                        TreeSet conjuntoOrdenado = new TreeSet(misMapValues);
                        Object[] arrayOrdenado = conjuntoOrdenado.toArray();
                        int size = arrayOrdenado.length;
                        for (int i = 0; i < size; i++) {
                            mapResultado.put(misMapKeys.get(misMapValues.indexOf(arrayOrdenado[i])), arrayOrdenado[i]);
                        }

                        Iterator distancia = mapResultado.values().iterator();
                        Iterator id = mapResultado.keySet().iterator();

                        Log.e("Arreglo id", String.valueOf(arrayOrdenado));

                        //Log.e("Menor id", String.valueOf(id.next()));
                        Log.e("Menor distancia", String.valueOf(distancia.next()));

                        idMenorDistancia = (int) id.next();

                        Log.e("idMenorDistancia", String.valueOf(idMenorDistancia));
                        //Collections.sort(menorDistance);
                        //Log.e("Menor", String.valueOf(menorDistance.get(0)));


                    } catch (JSONException e) {
                        Log.e("Json parsing error: ", e.getMessage());
                    }

                } else {
                    Log.e("Server", "Couldn't get json from server.");

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
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();

            }

        }

    }

    private class Notification extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... tokens) {

            String notification = "{ \"notification\": \n" +
                    "{\n" +
                    "    \"title\": \"ENI GLP ENVASADO\",\n" +
                    "    \"body\": \"Tiene una solicitud de GLP envasado\",\n" +
                    "    \"sound\": \"default\",\n" +
                    "    \"color\": \"#FFD700\"\n" +
                    "  },\n" +
                    "  \"to\" : \"" + token + "\"\n" +
                    "}";

            Log.e("token", token);
            Log.e("token", notification);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
            post.setHeader("content-type", "application/json;charset=UTF-8");
            post.setHeader("Authorization", "key=AIzaSyDMpB8qOddqDtycgGF6S08FW5-JgOY3BsU");

            try {

                JSONObject jsonObject = new JSONObject(notification);
                StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
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


    private class GetTokenMenorDistancia extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            String numRegister = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet getToken = new HttpGet(globals.getUrl_base() + "gasReceptionCenter?idreceptioncentergas=" + idMenorDistancia);

            getToken.setHeader("content-type", "application/json;charset=UTF-8");
            getToken.setHeader("Authorization", globals.getAut());


            try {

                //Log.e("TAG", "doInBackground: "+httpClient.execute(get));
                HttpResponse resp = httpClient.execute(getToken);
                resultado = EntityUtils.toString(resp.getEntity(),HTTP.UTF_8);

                resultado = resultado.replaceAll("\\[", "");
                resultado = resultado.replaceAll("\\]", "");
                resultado = resultado.replaceAll(" ", "");
                resultado = resultado.replaceAll("\\\"", "");


                token = resultado;


                if (resultado != null) {
                    existeToken = true;

                } else {
                    existeToken = false;

                    Log.e("Server", "Couldn't get json from server.");

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
                //Toast.makeText(getApplicationContext(), "Datos: " + resultado, Toast.LENGTH_LONG).show();

                if (existeToken) {

                    Notification notification = new Notification();
                    notification.execute();

                    Log.e("idclie oooo", "onCreate: " + idClient);
                    // Log.e("idclie iiii", "onCreate: "+globals.getIdCliente());

                    Log.e("Menor distancia", String.valueOf(idMenorDistancia));

                    Order order = new Order();
                    order.setIdgascylindertype(idCylinder);
                    Log.e("idclie", "onCreate: " + idClient);
                    order.setIdgascustomer(idClient);
                    Log.i("num cilinder", "onValueChange: " + numberCylinder);
                    order.setIdreceptioncentergas(idMenorDistancia);
                    order.setQuantitygasorder(Integer.parseInt(String.valueOf(numberCylinder)));
                    order.setOrdergascode(ordergascode);
                    order.setStatusgasorder(statusgasorder);
                    order.setXgascoordinate((float) latitude);
                    order.setYgascoordinate((float) longitude);
                    order.setGasorderdate(gasorderdate);
                    order.setReferenceorder(txtReference.getText().toString());

                    RegisterOrder register = new RegisterOrder();

                    register.execute(order);

                    setContentView(R.layout.activity_menu_gas);

                    Intent goSelecCylinder = new Intent(MapsGasActivity.this,SelectCylinderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    goSelecCylinder.putExtra("idCylinder",idCylinder);
                    goSelecCylinder.putExtra("numberCylinder",numberCylinder);
                    goSelecCylinder.putExtra("selectedCylinderImage",selectedCylinderImage);
                    goSelecCylinder.putExtra("idClient",idClient);

                    goSelecCylinder.putExtra("idgascustomer",globals.getIdClient());
                    goSelecCylinder.putExtra("gascustomerfirstname",globals.getGascustomerfirstname());
                    goSelecCylinder.putExtra("gascustomerlastname",globals.getGascustomerlastname());
                    goSelecCylinder.putExtra("gascustomercedruc",globals.getGascustomercedruc());
                    goSelecCylinder.putExtra("gascustomeremail",globals.getGascustomeremail());
                    goSelecCylinder.putExtra("gascustomerphone",globals.getGascustomerphone());
                    goSelecCylinder.putExtra("gascustomerpassword",globals.getGascustomerpassword());
                    goSelecCylinder.putExtra("gascustomerstatus",globals.isGascustomerstatus());
                    goSelecCylinder.putExtra("tokengascustomer",globals.getTokengascustomer());
                    goSelecCylinder.putExtra("typeService",typeService);


                    Toast.makeText(getApplicationContext(), R.string.mjsExitoPedido, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),R.string.lblDeliveryTimeOfTheOrder, Toast.LENGTH_LONG).show();
                    startActivity(goSelecCylinder);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No existe token", Toast.LENGTH_SHORT).show();
                }


            }

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}