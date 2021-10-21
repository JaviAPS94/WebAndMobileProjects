package com.example.lo_encontrecom.appeniclients;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.lo_encontrecom.appeniclients.global_variables.Globals;


public class OrderInformationGasFragment extends Fragment {

    ImageView icon_cylinder;
    int numCylinder = 1;
    Button btnChoose;
    Globals globals = new Globals();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_information_gas, container, false);

        icon_cylinder = v.findViewById(R.id.icon_cylinder);


        //Get the widgets reference from XML layout
        //final TextView txtNumberCylinderSelected = (TextView) v.findViewById(R.id.txtNumberCylinderSelected);
        NumberPicker txtNumberCylinder = (NumberPicker) v.findViewById(R.id.txtNumberCylinder);

        //Set TextView text color
        // txtNumberCylinderSelected.setTextColor(Color.parseColor("#1a0dab"));

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


        Intent bringSelectedCylinder = getActivity().getIntent();
        Bundle extras = bringSelectedCylinder.getExtras();

        final String selectedCylinderImage = extras.getString("selectedCylinderImage");
        final int idCylinder = extras.getInt("idCylinder");
        final int typeService = extras.getInt("typeService");

        globals.setIdClient(extras.getInt("idgascustomer"));

        icon_cylinder.setImageResource(this.getResources().getIdentifier(selectedCylinderImage, "drawable", this.getActivity().getPackageName()));
        Log.i("TAGgg", "onCreate: " + selectedCylinderImage);

        btnChoose = v.findViewById(R.id.btnChoose);

        globals.setIdClient(this.getArguments().getInt("idgascustomer"));
        globals.setGascustomerfirstname(this.getArguments().getString("gascustomerfirstname"));
        globals.setGascustomerlastname(this.getArguments().getString("gascustomerlastname"));
        globals.setGascustomercedruc(this.getArguments().getString("gascustomercedruc"));
        globals.setGascustomeremail(this.getArguments().getString("gascustomeremail"));
        globals.setGascustomerphone(this.getArguments().getString("gascustomerphone"));
        globals.setGascustomerpassword(this.getArguments().getString("gascustomerpassword"));
        globals.setGascustomerstatus(this.getArguments().getBoolean("gascustomerstatus"));
        globals.setTokengascustomer(this.getArguments().getString("tokengascustomer"));

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent goMaps = new Intent(getActivity(), MapsGasActivity.class);
                goMaps.putExtra("idCylinder", idCylinder);
                goMaps.putExtra("numberCylinder", numCylinder);
                goMaps.putExtra("idgascustomer", globals.getIdClient());
                goMaps.putExtra("idgascustomer", globals.getIdClient());
                goMaps.putExtra("selectedCylinderImage", selectedCylinderImage);
                goMaps.putExtra("typeService", typeService);

                goMaps.putExtra("idgascustomer", globals.getIdClient());
                goMaps.putExtra("gascustomerfirstname", globals.getGascustomerfirstname());
                goMaps.putExtra("gascustomerlastname", globals.getGascustomerlastname());
                goMaps.putExtra("gascustomercedruc", globals.getGascustomercedruc());
                goMaps.putExtra("gascustomeremail", globals.getGascustomeremail());
                goMaps.putExtra("gascustomerphone", globals.getGascustomerphone());
                goMaps.putExtra("gascustomerpassword", globals.getGascustomerpassword());
                goMaps.putExtra("gascustomerstatus", globals.isGascustomerstatus());
                goMaps.putExtra("tokengascustomer", globals.getTokengascustomer());


                startActivity(goMaps);


            }
        });

        return v;
    }


}
