package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.entity.Client;
import com.example.lo_encontrecom.loencontreeniv2.entity.ClienteGranel;
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

public class SignInGranel_Activity extends AppCompatActivity {

    EditText txtRazonSocial,txtDireccion,txtRUC,txtPhone,txtEmail,txtPassword;
    String granelcustomercode,granelcustomertimestamp;
    Boolean activesessiongranel,granelcustomerstatus;
    private String resultado = "";
    Boolean datosLlenos = false;
    Globals globals = new Globals();
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_granel);
        txtRazonSocial=findViewById(R.id.txtRazonSocial);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtRUC=findViewById(R.id.txtRUC);
        txtPhone=findViewById(R.id.txtPhone);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);

        granelcustomercode="SSS";
        granelcustomerstatus=true;
        granelcustomertimestamp="45454";
        activesessiongranel=true;

        btnAccept=findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClienteGranel clienteGranel = new ClienteGranel();
                clienteGranel.setGranelcustomercode(granelcustomercode.toString());
                clienteGranel.setGranelcustomerruc(txtRUC.getText().toString());
                clienteGranel.setGranelcustomerbusinessname(txtRazonSocial.getText().toString());
                clienteGranel.setGranelcustomeremail(txtEmail.getText().toString());
                clienteGranel.setGranelcustomerphonenumber(txtPhone.getText().toString());
                clienteGranel.setGranelcustomeraddress(txtDireccion.getText().toString());
                clienteGranel.setGranelcustomerstatus(granelcustomerstatus);
                clienteGranel.setGranelcustomerpassword(txtPassword.getText().toString());
                clienteGranel.setGranelcustomertimestamp(granelcustomertimestamp);
                clienteGranel.setActivesessiongranel(activesessiongranel);

                RegisterUser granelUser = new RegisterUser();
                granelUser.execute(clienteGranel);
            }
        });
    }

    public void selectCylinderType(View view) {
        Intent goSelectCylinderType = new Intent(this, SelectCylinderTypeActivity.class);
        startActivity(goSelectCylinderType);
    }
    public boolean validation(EditText textView) {
        // Log.e("datos", String.valueOf(!txtFirstName.getText().toString().isEmpty()));
        return textView.getText().toString().isEmpty();
    }

    private class RegisterUser extends AsyncTask<ClienteGranel, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ClienteGranel... clients) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "granelCustomer");
            post.setHeader("content-type", "application/json");
            post.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
            // post.setHeaders("content-type","application/json");


            try {

                ClienteGranel clienteGranel = clients[0];
                if (!validation(txtRazonSocial)&&!validation(txtDireccion)&&!validation(txtRUC)&&!validation(txtPhone)&&!validation(txtEmail)&&!validation(txtPassword)) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("granelcustomercode", clienteGranel.getGranelcustomercode());
                    values.put("granelcustomerruc", clienteGranel.getGranelcustomerruc());
                    values.put("granelcustomerbusinessname", clienteGranel.getGranelcustomerbusinessname());
                    values.put("granelcustomeremail",clienteGranel.getGranelcustomeremail());
                    values.put("granelcustomerphonenumber", clienteGranel.getGranelcustomerphonenumber());
                    values.put("granelcustomeraddress", clienteGranel.getGranelcustomeraddress());
                    values.put("granelcustomerstatus", String.valueOf(clienteGranel.getGranelcustomerstatus()));
                    values.put("granelcustomerpassword", clienteGranel.getGranelcustomerpassword());
                    values.put("granelcustomertimestamp", clienteGranel.getGranelcustomertimestamp());
                    values.put("activesessiongranel", String.valueOf(clienteGranel.getActivesessiongranel()));


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
                    Intent goLogin = new Intent(SignInGranel_Activity.this, Login_Activity.class);
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
