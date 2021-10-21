package com.example.lo_encontrecom.appeniclients;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsLubricanteFragment extends Fragment {

    MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps_lubricante, container, false);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Log.i("DEBUG", "onMapReady");
                googleMap.addMarker(new MarkerOptions()
                        .anchor(0.0f, 1.0f)
                        .position(new LatLng(-0.199006,-78.496133))
                        .title("Lubricadora 1"))
                ;
                googleMap.addMarker(new MarkerOptions()
                        .anchor(0.0f, 1.0f)
                        .position(new LatLng(-0.215865,-78.496712))
                        .title("Lubricadora 2"));

                googleMap.addMarker(new MarkerOptions()
                        .anchor(0.0f, 1.0f)
                        .position(new LatLng(-0.216788,-78.504930))
                        .title("Lubricadora 3"));
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(55.854049, 13.661331));
                LatLngBounds bounds = builder.build();
                int padding = 0;
                // Updates the location and zoom of the MapView

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-1.259546, -78.624709)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-1.259546, -78.624709), 6.0f));

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}