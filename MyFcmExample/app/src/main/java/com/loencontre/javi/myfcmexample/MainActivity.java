package com.loencontre.javi.myfcmexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    private Button btnListarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textViewToken);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                textView.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
            }
        };




        if (SharedPrefManager.getInstance(this)!=null)

            textView.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
            Log.d("myfcmtokenshared", SharedPrefManager.getInstance(this).getToken());


        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        btnListarPedido=findViewById(R.id.btnListarPedido);
        btnListarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goOrders = new Intent(MainActivity.this, OrdersActivity.class);
                startActivity(goOrders);
            }
        });


    }
}
