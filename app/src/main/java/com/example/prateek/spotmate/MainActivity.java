package com.example.prateek.spotmate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";
    ProgressDialog progressDialog;
    EditText etLogUser;
    EditText etLogPass;
    String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (StoreSharePreferances.getUserName(MainActivity.this).length()!=0 ||
                StoreSharePreferances.getUserName(MainActivity.this).length()!=0){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, home.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        }
        /**
         * This piece of code is executed when the user clicks on the new user icon.
         */
        ImageButton reg = (ImageButton) findViewById(R.id.newUser);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userReg = new Intent(MainActivity.this, Register.class);
                startActivity(userReg);
                finish();
            }
        });

        etLogUser = (EditText) findViewById(R.id.usr);
        etLogPass = (EditText) findViewById(R.id.pass);

    }

    private void userLogin() {

        username = etLogUser.getText().toString();
        password = etLogPass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "Login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {

                            StoreSharePreferances.setUserName(MainActivity.this, username);
                            StoreSharePreferances.setPassword(MainActivity.this, password);

                            Toast.makeText(MainActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), home.class);
                            //intent.setClass(MainActivity.this, home.class);
                            //intent.putExtra("username", username);
                            Log.d("user  " + username, "sdk");

                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();


                        } else {
                            Toast.makeText(MainActivity.this, "Username and Passwords do not match..\n" +
                                    " Please contact Nilesh or Prateek to beg for help!!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(MainActivity.this, "Connection Error!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(MainActivity.this, "Authentication Failure!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(MainActivity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(MainActivity.this, "Network Error!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                password = MD5_hash(password);
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }

    /*
    This piece of code generates hash value for a text.
     */
    public String MD5_hash(String input) {
        try {
            // Create MD5 Hash
            MessageDigest hash = java.security.MessageDigest.getInstance("MD5");
            hash.update(input.getBytes());
            byte hashData[] = hash.digest();

            // Create Hexadecimal Hash String
            StringBuilder hashVal = new StringBuilder();
            for (int i = 0; i < hashData.length; i++)
                hashVal.append(Integer.toHexString(0xFF & hashData[i]));
            return hashVal.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void loginUser(View view) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging in..");
        progressDialog.show();
        userLogin();
    }

    protected void onResume() {
        super.onResume();
        etLogPass.setText("");
        etLogUser.setText("");

    }
}
