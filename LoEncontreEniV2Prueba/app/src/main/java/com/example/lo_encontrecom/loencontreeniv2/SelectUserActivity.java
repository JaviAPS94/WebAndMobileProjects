package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
    }

    public void login(View view){
        Intent goLogin = new Intent(this, LoginLubricantesActivity.class);
        startActivity(goLogin);
    }
}
