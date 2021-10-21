package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.json.JSONException;

public class OrderInformationActivity extends AppCompatActivity {

    TextView txtSelectedCylinder;
    ImageView icon_cylinder;

    private static final String JSON_STRING =
            "{\"person\":{\"name\":\"A\",\"age\":30,\"children\":[{\"name\":\"B\",\"age\":5}," + "\"name\":\"C\",\"age\":7},{\"name\":\"D\",\"age\":9}]}}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);



        /*TextView line1 = (TextView)findViewById(R.id.line1);
        TextView line2 = (TextView)findViewById(R.id.line2);
        TextView line3 = (TextView)findViewById(R.id.line3);
        try {
            JSONObject person = (new JSONObject(JSON_STRING)).getJSONObject("person");
            String name = person.getString("name");
            Log.i("TAGaaaaa", "onCreate: "+name);
            line1.setText("This person's name is " + name);
            line2.setText(name + " is " + person.getInt("age") + " years old.");
            line3.setText(name + " has " + person.getJSONArray("children").length()
                    + " children.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        txtSelectedCylinder = findViewById(R.id.txtSelectedCylinder);
        icon_cylinder=findViewById(R.id.icon_cylinder);

        //Get the widgets reference from XML layout
        final TextView txtNumberCylinderSelected = (TextView) findViewById(R.id.txtNumberCylinderSelected);
        NumberPicker txtNumberCylinder = (NumberPicker) findViewById(R.id.txtNumberCylinder);

        //Set TextView text color
        txtNumberCylinderSelected.setTextColor(Color.parseColor("#1a0dab"));

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
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                txtNumberCylinderSelected.setText(" " + newVal);
            }
        });

        Intent bringSelectedCylinderType= getIntent();
        Bundle extras =bringSelectedCylinderType.getExtras();

        String selectedCylinder= extras.getString("selectedCylinderName");
        String selectedCylinderImage = extras.getString("selectedCylinderImage");

        txtSelectedCylinder.setText(selectedCylinder);
        icon_cylinder.setImageResource(this.getResources().getIdentifier(selectedCylinderImage, "drawable", this.getPackageName()));
        Log.i("TAGgg", "onCreate: "+selectedCylinderImage);


    }

    public void string (View view) {
    }
}
