package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    //MÉTODOS

    //Para hacer el llamado de la activity Login
    public void login(View v){
        Intent goLogin = new Intent(this, Login_Activity.class);
        startActivity(goLogin);
    }

    public void loginGranel(View v){
        Intent goLogin = new Intent(this, LogingranelActivity.class);
        startActivity(goLogin);
    }

    public void loginLubricante(View v){
        Intent goLogin = new Intent(this, LoginLubricantesActivity.class);
        startActivity(goLogin);
    }
}
