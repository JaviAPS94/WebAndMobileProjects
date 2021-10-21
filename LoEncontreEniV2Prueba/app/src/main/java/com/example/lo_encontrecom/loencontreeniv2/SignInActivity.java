package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lo_encontrecom.loencontreeniv2.entity.Client;
import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private String resultado = "";
    EditText txtFirstName, txtLastName, txtCedulaRUC, txtPhone, txtEmail, txtPassword;
    private String txtAddress, txtTimestamp;
    Button btnAccept;
    Globals globals = new Globals();
    Boolean datosLlenos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtCedulaRUC = findViewById(R.id.txtCedulaRUC);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtAddress = "Quito";
        txtTimestamp = "2017-09-09";
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Client client = new Client();
                client.setGascustomerfirstname(txtFirstName.getText().toString());
                client.setGascustomerlastname(txtLastName.getText().toString());
                client.setGascustomercedruc(txtCedulaRUC.getText().toString());
                client.setGascustomeremail(txtEmail.getText().toString());
                client.setGascustomerphone(txtPhone.getText().toString());
                client.setGascustomeraddress(txtAddress.toString());
                client.setGascustomertimestamp(txtTimestamp.toString());
                client.setGascustomerpassword(txtPassword.getText().toString());

                RegisterUser register = new RegisterUser();

                register.execute(client);

            }
        });
    }

    public boolean validation(EditText textView) {
       // Log.e("datos", String.valueOf(!txtFirstName.getText().toString().isEmpty()));
        return textView.getText().toString().isEmpty();
    }

    private class RegisterUser extends AsyncTask<Client, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Client... clients) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "gasCustomer");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            // post.setHeaders("content-type","application/json");


            try {

                Client client = clients[0];
                if (!validation(txtFirstName)&&!validation(txtLastName)&&!validation(txtCedulaRUC)&&!validation(txtEmail)&&!validation(txtPhone)&&!validation(txtPassword)) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("gascustomerfirstname", client.getGascustomerfirstname());
                    values.put("gascustomerlastname", client.getGascustomerlastname());
                    values.put("gascustomercedruc", client.getGascustomercedruc());
                    values.put("gascustomeremail", client.getGascustomeremail());
                    values.put("gascustomerphone", client.getGascustomerphone());
                    values.put("gascustomeraddress", client.getGascustomeraddress());
                    values.put("gascustomertimestamp", client.getGascustomertimestamp());
                    values.put("gascustomerpassword", client.getGascustomerpassword());

                    JSONObject jsonObject = new JSONObject(values);
                    StringEntity entity = new StringEntity(jsonObject.toString());
                    Log.e("jsonn", String.valueOf(values));
                    post.setEntity(entity);
                    HttpResponse resp = httpClient.execute(post);
                    resultado = EntityUtils.toString(resp.getEntity());
                    datosLlenos = true;
                } else {
                    Log.e("Error", "datos vacios");
                    datosLlenos = false;
                }
                return true;

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == false) {
                Toast.makeText(getApplicationContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                if (datosLlenos) {
                    Toast.makeText(getApplicationContext(), R.string.mjsRegistroExito, Toast.LENGTH_LONG).show();
                    Intent goLogin = new Intent(SignInActivity.this, Login_Activity.class);
                    startActivity(goLogin);
                }else {
                    Toast.makeText(getApplicationContext(), R.string.mjsRegistroDatosVacios, Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
