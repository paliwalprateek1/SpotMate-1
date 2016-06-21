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

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;


    public MainActivity(){}


    EditText etLogUser;
    EditText etLogPass;
    String username, password;

   // String password=MD5_hash("pandey");
    String test;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "052250", Toast.LENGTH_SHORT).show();
/**
 * This piece of code is executed when the user clicks on the new user icon.
 */
        ImageButton reg=(ImageButton)findViewById(R.id.newUser);
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent userReg=new Intent(MainActivity.this, Register.class);
                startActivity(userReg);
                finish();
            }
        });

        /*
        This code executes on login.
         */

        etLogUser=(EditText)findViewById(R.id.usr);
        etLogPass=(EditText)findViewById(R.id.pass);
        Button login=(Button)findViewById(R.id.login_button);
    }

    private void userLogin() {
        username = etLogUser.getText().toString();
        password = etLogPass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "Login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(MainActivity.this,"Login Succesfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, home.class);
                            intent.putExtra("username", username);
                            Log.d("user  "+username, "sdk" );

                            startActivity(intent);
                            progressDialog.dismiss();


                        }else{
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                password = MD5_hash(password);
                map.put("username",username);
                map.put("password",password);
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
            for (int i=0; i<hashData.length; i++)
                hashVal.append(Integer.toHexString(0xFF & hashData[i]));
            return hashVal.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void loginUser(View view) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("ho raha hai bhai");
        progressDialog.show();
        userLogin();
    }
}
