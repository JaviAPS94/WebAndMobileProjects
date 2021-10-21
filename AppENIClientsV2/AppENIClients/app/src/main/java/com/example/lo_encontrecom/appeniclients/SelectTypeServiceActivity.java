package com.example.lo_encontrecom.appeniclients;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectTypeServiceActivity extends AppCompatActivity {

    /*Atributos*/
    ImageButton btnGas,btnGranel,btnLubricantes;
    String saltoPantalla="";
    int typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type_service);

        SharedPreferences shared= getSharedPreferences("Datos",MODE_PRIVATE);

        saltoPantalla=shared.getString("eni","");
        typeService=shared.getInt("typeService",0);


        Log.e("mjs", String.valueOf(typeService));
        Log.e("mjs", saltoPantalla );

        if (saltoPantalla.equalsIgnoreCase("eni") && typeService==1){
            Intent goSelecCylinder = new Intent(SelectTypeServiceActivity.this, SelectCylinderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goSelecCylinder);
            finish();
        }else if(saltoPantalla.equalsIgnoreCase("eni") && typeService==3){
            Intent goMenuLubricante = new Intent(SelectTypeServiceActivity.this, MenuLubricantesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goMenuLubricante);
            finish();
        }else if(saltoPantalla.equalsIgnoreCase("eni") && typeService==2){
            Intent goMenuGranel = new Intent(SelectTypeServiceActivity.this, MenuGranelActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(goMenuGranel);
            finish();
        }

        btnGas=findViewById(R.id.btnGas);
        btnGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginGAS= new Intent(SelectTypeServiceActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                goLoginGAS.putExtra("TypeService",1);
                startActivity(goLoginGAS);
            }
        });
        btnGranel=findViewById(R.id.btnGranel);
        btnGranel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginGAS= new Intent(SelectTypeServiceActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                goLoginGAS.putExtra("TypeService",2);
                startActivity(goLoginGAS);
            }
        });
        btnLubricantes=findViewById(R.id.btnLubricantes);
        btnLubricantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginGAS= new Intent(SelectTypeServiceActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                goLoginGAS.putExtra("TypeService",3);
                startActivity(goLoginGAS);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
