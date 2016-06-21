package com.example.prateek.spotmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText etName, etUsername, etMobile, etPassword, etRePass;
    String name, username, mobile, password, repassword;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.name);
        etUsername = (EditText) findViewById(R.id.username);
        etMobile = (EditText) findViewById(R.id.mobile);
        etPassword = (EditText) findViewById(R.id.password);
        etRePass = (EditText) findViewById(R.id.repassword);


        Button regist = (Button) findViewById(R.id.reg);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("ho raha hai bhai");
                progressDialog.show();

                name = etName.getText().toString();
                username = etUsername.getText().toString();
                mobile = etMobile.getText().toString();
                password = etPassword.getText().toString();
                repassword = etRePass.getText().toString();

                if (name.length() == 0 || username.length() == 0 || mobile.length() == 0 || password.length() == 0 || repassword.length() == 0) {
                    Toast.makeText(Register.this, "None of the fields can be left blank", Toast.LENGTH_SHORT).show();
                } else if (password.equals(repassword)) {

                    password = MD5_hash(password);
                    User user = new User(name, username, mobile, password);
                    registerUser(user);

                    Toast.makeText(Register.this, "Registering", Toast.LENGTH_SHORT).show();
                    ///progressDialog.dismiss();


                } else {
                    Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String MD5_hash(String input) {
        try {
            // Create MD5 Hash
            MessageDigest hash = java.security.MessageDigest.getInstance("MD5");
            hash.update(input.getBytes());
            byte hashData[] = hash.digest();

            // Create Hexadecimal Hash String
            StringBuilder hashVal = new StringBuilder();
            for (int i=0; i<hashData.length; i++)
                hashVal.append(Integer.toHexString(0xFF & hashData[i]));
            return hashVal.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void registerUser(final User user) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "Register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Register.this,response,Toast.LENGTH_LONG).show();
                        Intent reg = new Intent(Register.this, MainActivity.class);
                        startActivity(reg);
                        finish();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",user.name);
                params.put("username", user.username);
                params.put("mobile", user.mobile);
                params.put("password", user.password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }
}



