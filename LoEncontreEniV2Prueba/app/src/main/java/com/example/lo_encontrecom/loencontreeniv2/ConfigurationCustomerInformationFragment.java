package com.example.lo_encontrecom.loencontreeniv2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lo_encontrecom.loencontreeniv2.R;
import com.example.lo_encontrecom.loencontreeniv2.entity.Client;
import com.example.lo_encontrecom.loencontreeniv2.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ConfigurationCustomerInformationFragment extends Fragment {

    private String resultado = "";
    EditText txtFirstName, txtLastName, txtCedulaRUC, txtPhone, txtEmail, txtPassword;
    private String txtAddress, txtTimestamp;

    Globals globals = new Globals();
    Boolean datosLlenos = false;

    Button btnAccept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_configuration_customer_information, container, false);

        txtFirstName = v.findViewById(R.id.txtFirstName);
        txtLastName = v.findViewById(R.id.txtLastName);
        txtCedulaRUC = v.findViewById(R.id.txtCedulaRUC);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtPassword = v.findViewById(R.id.txtPassword);
        txtAddress = "Quito";
        txtTimestamp = "2017-09-09";

        globals.setIdClient(this.getArguments().getInt("idgascustomer"));
        globals.setGascustomerfirstname(this.getArguments().getString("gascustomerfirstname"));
        globals.setGascustomerlastname(this.getArguments().getString("gascustomerlastname"));
        globals.setGascustomercedruc(this.getArguments().getString("gascustomercedruc"));
        globals.setGascustomeremail(this.getArguments().getString("gascustomeremail"));
        globals.setGascustomerphone(this.getArguments().getString("gascustomerphone"));
        globals.setGascustomerpassword(this.getArguments().getString("gascustomerpassword"));

        Log.e("conf", " "+globals.getIdClient() );

        txtFirstName.setText(globals.getGascustomerfirstname());
        txtLastName.setText(globals.getGascustomerlastname());
        txtCedulaRUC.setText(globals.getGascustomercedruc());
        txtEmail.setText(globals.getGascustomeremail());
        txtPhone.setText(globals.getGascustomerphone());
        txtPassword.setText(globals.getGascustomerpassword());

        btnAccept = (Button) v.findViewById(R.id.btnAccept);
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

                UpdateUser updateUser = new UpdateUser();

                updateUser.execute(client);

            }
        });

       /* btnAccept=v.findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSelectCylinderType= new Intent(getActivity(), SelectCylinderTypeActivity.class);
                startActivity(goSelectCylinderType);
            }
        });*/
        return v;
    }

    public boolean validation(EditText textView) {
        // Log.e("datos", String.valueOf(!txtFirstName.getText().toString().isEmpty()));
        return textView.getText().toString().isEmpty();
    }

    private class UpdateUser extends AsyncTask<Client, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Client... clients) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPut put = new HttpPut(globals.getUrl_base() + "gasCustomer/update/"+globals.getIdClient());
            put.setHeader("content-type", "application/json");
            put.setHeader("Authorization", "Basic dXNlcjp1c2Vy");
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
                    put.setEntity(entity);
                    HttpResponse resp = httpClient.execute(put);
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
                Toast.makeText(getContext(), R.string.mjsRegistroFallo, Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                if (datosLlenos) {
                    /*Toast.makeText(getContext(), R.string.mjsRegistroExito, Toast.LENGTH_LONG).show();
                    Intent goLogin = new Intent(SignInActivity.this, Login_Activity.class);
                    startActivity(goLogin);*/

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new OrderInformationFragment()).addToBackStack(null).commit();

                }else {
                    Toast.makeText(getContext(), R.string.mjsRegistroDatosVacios, Toast.LENGTH_LONG).show();
                }

            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

}
