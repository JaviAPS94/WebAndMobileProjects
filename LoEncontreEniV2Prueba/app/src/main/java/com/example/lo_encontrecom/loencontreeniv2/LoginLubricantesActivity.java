package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginLubricantesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_lubricantes);
    }

    public void registro(View view){
        Intent goRegistro =  new Intent(this, SignInLubricantesActivity.class);
        startActivity(goRegistro);
    }

    public void menu(View view){
        Intent goMenu=new Intent(this, MenuLubricanteActivity.class);
        startActivity(goMenu);
    }
}
