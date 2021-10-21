package com.example.lo_encontrecom.appeniclients;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo_encontrecom.appeniclients.entity.Client;
import com.example.lo_encontrecom.appeniclients.global_variables.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInGasActivity extends AppCompatActivity {

    /*int time = 10;
    Timer t = new Timer();
    TimerTask task;
    Button start;*/
    private Typeface verdana;

    /*Atributos*/
    private String resultado = "";
    EditText txtFirstName, txtLastName, txtCedulaRUC, txtPhone, txtEmail, txtPassword;
    TextView titulo;
    private String txtTimestamp;
    Button btnAccept;
    Globals globals = new Globals();
    Boolean datosLlenos = false;
    Boolean cedula = false;
    Boolean caracteres = false;
    Boolean valemail = false;
    Boolean valephone = false;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private BroadcastReceiver broadcastReceiver;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_gas);


        /*Para obtener la fecha actual*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        txtTimestamp = dateFormat.format(date);

        Log.e("Fecha", txtTimestamp);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtCedulaRUC = findViewById(R.id.txtCedulaRUC);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnAccept = findViewById(R.id.btnAccept);
        titulo = findViewById(R.id.lblSignIn);


        /*Para obtener el token*/
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                token = SharedPrefManager.getInstance(SignInGasActivity.this).getToken();
                //textView.setText(SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken());
            }
        };

        if (SharedPrefManager.getInstance(this) != null) {
            this.token = SharedPrefManager.getInstance(SignInGasActivity.this).getToken();
            //textView.setText(SharedPrefManager.getInstance(SelectCylinderActivity.this).getToken());
        }
        Log.d("myfcmtokenshared", SharedPrefManager.getInstance(this).getToken());

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Client client = new Client();
                client.setGascustomerfirstname(txtFirstName.getText().toString());
                client.setGascustomerlastname(txtLastName.getText().toString());
                client.setGascustomercedruc(txtCedulaRUC.getText().toString());
                client.setGascustomeremail(txtEmail.getText().toString());
                client.setGascustomerphone(txtPhone.getText().toString());
                client.setGascustomertimestamp(txtTimestamp.toString());
                client.setGascustomerpassword(txtPassword.getText().toString());
                client.setTokengascustomer(token);

                RegisterUser register = new RegisterUser();

                register.execute(client);

            }
        });

    }

    public boolean validation(EditText textView) {
        return textView.getText().toString().isEmpty();
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }


    public boolean validarLongitudCedula(String CED) {
        boolean caracteresCorrectos = false;
        if (CED.length() == 10) {
            caracteresCorrectos = true;
        }
        /*if (RUC.length()>10){
            caracteresCorrectos=false;
        }
        if (RUC.length()==13){
            caracteresCorrectos=true;
        }
        if (RUC.length()==10){
            caracteresCorrectos=true;
        }*/
        return caracteresCorrectos;

    }

    public boolean validarLongitudRUC(String RUC) {
        boolean caracteresCorrectos = false;
        if (RUC.length() == 13) {
            caracteresCorrectos = true;
        }
        return caracteresCorrectos;
    }

    public static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public boolean validarLongitudTel(String tel) {
        boolean caracteresCorrectos = false;
        if (tel.length() > 8) {
            caracteresCorrectos = true;
        }
        return caracteresCorrectos;
    }

    private class RegisterUser extends AsyncTask<Client, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Client... clients) {
            datosLlenos = false;
            cedula = false;
            caracteres = false;
            valemail = false;
            valephone = false;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(globals.getUrl_base() + "gasCustomer");

            post.setHeader("content-type", "application/json;charset=UTF-8");
            post.setHeader("Authorization", globals.getAut());
            // post.setHeaders("content-type","application/json");

            try {

                Client client = clients[0];
                if (!validation(txtFirstName) && !validation(txtLastName) && !validation(txtCedulaRUC) && !validation(txtEmail) && !validation(txtPhone) && !validation(txtPassword)) {

                    if (validarLongitudTel(client.getGascustomerphone())) {

                        valephone = true;
                    }

                    if (validateEmail(client.getGascustomeremail())) {

                        valemail = true;
                    }

                    if (validarLongitudCedula(client.getGascustomercedruc())) {
                        if (validadorDeCedula(client.getGascustomercedruc())) {


                            Map<String, String> values = new HashMap<String, String>();
                            values.put("gascustomerfirstname", client.getGascustomerfirstname());
                            values.put("gascustomerlastname", client.getGascustomerlastname());
                            values.put("gascustomercedruc", client.getGascustomercedruc());
                            values.put("gascustomeremail", client.getGascustomeremail());
                            values.put("gascustomerphone", client.getGascustomerphone());
                            values.put("gascustomertimestamp", client.getGascustomertimestamp());
                            values.put("gascustomerpassword", client.getGascustomerpassword());
                            values.put("tokengascustomer", client.getTokengascustomer());

                            JSONObject jsonObject = new JSONObject(values);
                            StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
                            Log.e("jsonn", String.valueOf(values));
                            post.setEntity(entity);
                            HttpResponse resp = httpClient.execute(post);
                            resultado = EntityUtils.toString(resp.getEntity());


                            cedula = true;
                        }
                        caracteres = true;
                    }
                    if (validarLongitudRUC(client.getGascustomercedruc())) {


                        Map<String, String> values = new HashMap<String, String>();
                        values.put("gascustomerfirstname", client.getGascustomerfirstname());
                        values.put("gascustomerlastname", client.getGascustomerlastname());
                        values.put("gascustomercedruc", client.getGascustomercedruc());
                        values.put("gascustomeremail", client.getGascustomeremail());
                        values.put("gascustomerphone", client.getGascustomerphone());
                        values.put("gascustomertimestamp", client.getGascustomertimestamp());
                        values.put("gascustomerpassword", client.getGascustomerpassword());
                        values.put("tokengascustomer", client.getTokengascustomer());

                        JSONObject jsonObject = new JSONObject(values);
                        StringEntity entity = new StringEntity(jsonObject.toString(),"UTF-8");
                        Log.e("jsonn", String.valueOf(values));
                        post.setEntity(entity);
                        HttpResponse resp = httpClient.execute(post);
                        resultado = EntityUtils.toString(resp.getEntity());


                        cedula = true;
                        caracteres = true;
                    }

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
                Log.e("Resu", resultado );
                try {
                    JSONObject jsonObj = new JSONObject(resultado);

                    int status=jsonObj.getInt("status");

                    if(status != 500){
                        //Toast.makeText(getApplicationContext(), "El resultado es: " + resultado, Toast.LENGTH_LONG).show();
                        if (datosLlenos && cedula && caracteres && valemail && valephone) {
                            Intent bringSelectedTypeService = getIntent();
                            Bundle extras = bringSelectedTypeService.getExtras();
                            int typeService = extras.getInt("TypeService");

                            Toast.makeText(getApplicationContext(), R.string.mjsRegistroExito, Toast.LENGTH_LONG).show();
                            Intent goLogin = new Intent(SignInGasActivity.this, LoginActivity.class);
                            goLogin.putExtra("TypeService", typeService);
                            startActivity(goLogin);
                        }
                        if (datosLlenos == false) {
                            Toast.makeText(getApplicationContext(), R.string.mjsRegistroDatosVacios, Toast.LENGTH_LONG).show();
                        }
                        if (caracteres == false) {
                            txtCedulaRUC.setError("Cédula o RUC incorrecto");
                            txtCedulaRUC.requestFocus();
                            //Toast.makeText(getApplicationContext(), "Longitud de cédula o RUC incorrecta", Toast.LENGTH_LONG).show();
                        }

                        if (cedula == false) {
                            txtCedulaRUC.setError("Cédula o RUC incorrecto");
                            txtCedulaRUC.requestFocus();
                            //Toast.makeText(getApplicationContext(), "Su cédula o RUC es incorrecto", Toast.LENGTH_LONG).show();
                        }

                        if (valemail == false) {
                            txtEmail.setError("Email incorrecto");
                            txtEmail.requestFocus();
                            //Toast.makeText(getApplicationContext(), "Su email es incorrecto", Toast.LENGTH_LONG).show();
                        }
                        if (valephone == false) {
                            txtPhone.setError("El Teléfono debe tener 9 o 10 dígitos");
                            txtPhone.requestFocus();
                            //Toast.makeText(getApplicationContext(), "Su teléfono debe tener 9 o 10 dígitos", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Usuario existente", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error Json", Toast.LENGTH_LONG).show();
                }



            }

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
