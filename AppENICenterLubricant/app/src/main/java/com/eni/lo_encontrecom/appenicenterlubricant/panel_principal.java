package com.eni.lo_encontrecom.appenicenterlubricant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class panel_principal extends AppCompatActivity {

    /*Atributos*/
    Button btnSeleccionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_principal);
        btnSeleccionar=findViewById(R.id.btnSeleccionar);
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginGAS= new Intent(panel_principal.this, SelectTypeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                goLoginGAS.putExtra("TypeService",1);
                startActivity(goLoginGAS);
            }
        });

    }
}
