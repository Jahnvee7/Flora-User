package com.example.florausher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText password, email;
    private Button login,register;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);

        login.setOnClickListener(this);
        register.setOnClickListener(this);




    }
    private void userLogin()
    {
        final String Email=email.getText().toString().trim();
        final String Password=password.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        final String UserLastDateLogin = sdf.format(new Date());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        if(Email.equals("admin@gmail.com") && Password.equals("admin@"))
        {
            startActivity(new Intent(this,AdminCategory.class));
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    //Intent intent = new Intent(Login.this,
                           // Welcome.class);
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                obj.getInt("UserId"),
                                obj.getString("UserName"),
                                obj.getString("Email")
                                //obj.getString("MobileNumber")


                        );
                        startActivity(new Intent(Login.this,Welcome.class));
                        //Intent intent = new Intent(Login.this,Welcome.class);
                        //Intent intent=getPackageManager().getLaunchIntentForPackage("com.example.search");
                        //startActivity(intent);
                        //Toast.makeText(getApplicationContext(),"User login Successfully",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Email",Email);
                params.put("Password",Password);
                params.put("UserLastDateLogin",UserLastDateLogin);
                params.put("UserIsActive","1");
                return params;
            }
        };

        RequestHandle.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view==login)
            userLogin();
        if(view==register)
            startActivity(new Intent(this,MainActivity.class));
    }
}
