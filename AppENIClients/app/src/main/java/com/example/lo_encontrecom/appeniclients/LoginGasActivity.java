package com.example.lo_encontrecom.appeniclients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginGasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_gas);
    }

    public void signIn(View view){
        Intent goSignIn = new Intent(this, SignInGasActivity.class);
        startActivity(goSignIn);
    }
}
