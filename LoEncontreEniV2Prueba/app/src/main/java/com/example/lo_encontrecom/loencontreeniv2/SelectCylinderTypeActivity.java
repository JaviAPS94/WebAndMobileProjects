package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

public class SelectCylinderTypeActivity extends AppCompatActivity {

    //Atributos
    TextView lblSelectedCylinder;
    ImageButton btnDomestic,btnMontaCarga,btnIndustrial15,btnIndustrial45;
    Globals globals = new Globals();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cylinder_type);

        btnDomestic = findViewById(R.id.btnDomestic);
        btnMontaCarga = findViewById(R.id.btnMontaCarga);
        btnIndustrial15=findViewById(R.id.btnIndustrial15);
        btnIndustrial45=findViewById(R.id.btnIndustrial45);

        Intent bringLogin = getIntent();
        Bundle extras = bringLogin.getExtras();

       // String selectedCylinder = extras.getString("selectedCylinderName");
        globals.setIdClient(extras.getInt("idgascustomer"));
        globals.setGascustomerfirstname(extras.getString("gascustomerfirstname"));
        globals.setGascustomerlastname(extras.getString("gascustomerlastname"));
        globals.setGascustomercedruc(extras.getString("gascustomercedruc"));
        globals.setGascustomeremail(extras.getString("gascustomeremail"));
        globals.setGascustomerphone(extras.getString("gascustomerphone"));
        globals.setGascustomerpassword(extras.getString("gascustomerpassword"));



        btnDomestic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent goOrderInformation = new Intent(SelectCylinderTypeActivity.this, MenuActivity.class);
                lblSelectedCylinder=findViewById(R.id.lblDomestic);
                goOrderInformation.putExtra("selectedCylinderName",lblSelectedCylinder.getText());
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnDomestic.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",1);

                startActivity(goOrderInformation);

            }
        });

        btnMontaCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderTypeActivity.this, MenuActivity.class);
                lblSelectedCylinder=findViewById(R.id.lblMontaCarga);
                goOrderInformation.putExtra("selectedCylinderName",lblSelectedCylinder.getText());
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnMontaCarga.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",2);
                startActivity(goOrderInformation);
            }
        });

        btnIndustrial15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderTypeActivity.this, MenuActivity.class);
                lblSelectedCylinder=findViewById(R.id.lblIndustril15);
                goOrderInformation.putExtra("selectedCylinderName",lblSelectedCylinder.getText());
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnIndustrial15.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",3);
                startActivity(goOrderInformation);
            }
        });

        btnIndustrial45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrderInformation = new Intent(SelectCylinderTypeActivity.this, MenuActivity.class);
                lblSelectedCylinder=findViewById(R.id.lblIndustril45);
                goOrderInformation.putExtra("selectedCylinderName",lblSelectedCylinder.getText());
                goOrderInformation.putExtra("selectedCylinderImage", (String) btnIndustrial45.getTag());

                dataClient(goOrderInformation);

                goOrderInformation.putExtra("idCylinder",4);
                startActivity(goOrderInformation);
            }
        });

    }

    public void dataClient(Intent goOrderInformation){
        goOrderInformation.putExtra("idgascustomer",globals.getIdClient());
        goOrderInformation.putExtra("gascustomerfirstname",globals.getGascustomerfirstname());
        goOrderInformation.putExtra("gascustomerlastname",globals.getGascustomerlastname());
        goOrderInformation.putExtra("gascustomercedruc",globals.getGascustomercedruc());
        goOrderInformation.putExtra("gascustomeremail",globals.getGascustomeremail());
        goOrderInformation.putExtra("gascustomerphone",globals.getGascustomerphone());
        goOrderInformation.putExtra("gascustomerpassword",globals.getGascustomerpassword());
    }
}
