package com.example.florausher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, password, email, mobile, permanent_add;
    private Button submit;

    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobile);
        permanent_add=(EditText)findViewById(R.id.permanent_add);
        textViewLogin=(TextView)findViewById(R.id.textViewLogin);
        submit=(Button)findViewById(R.id.submit);
        progressDialog=new ProgressDialog(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password, "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.mobile, "/^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}", R.string.mobileerror);
        awesomeValidation.addValidation(this,R.id.permanent_add,"^[a-zA-Z0-9,.!? ]{20,}$",R.string.addresserror);
        submit.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    private void registerUser() {
        if(awesomeValidation.validate())
        {
            final String UserName = name.getText().toString().trim();
            final String Password = password.getText().toString().trim();
            final String Email = email.getText().toString().trim();
            final String MobileNumber = mobile.getText().toString().trim();
            final String PermanentAddress = permanent_add.getText().toString().trim();
            final String ShipAddress = PermanentAddress;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            final String UserLastDateLogin = sdf.format(new Date());

            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this,Welcome.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("UserName", UserName);
                    params.put("Email", Email);
                    params.put("MobileNumber", MobileNumber);
                    params.put("ShipAddress", ShipAddress);
                    params.put("PermanentAddress", PermanentAddress);
                    params.put("UserIsActive", "1");
                    params.put("UserLastDateLogin", UserLastDateLogin);
                    params.put("Password", Password);
                    return params;
                }
            };
            RequestHandle.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==submit)
            registerUser();
        if(view==textViewLogin)
            startActivity(new Intent(this,Login.class));
    }


}
