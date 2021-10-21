package com.example.lo_encontrecom.appeniclients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectTypeServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type_service);
    }

    public void loginGas(View view){
        Intent goLoginGAS= new Intent(this, LoginGasActivity.class);
        startActivity(goLoginGAS);
    }
}
