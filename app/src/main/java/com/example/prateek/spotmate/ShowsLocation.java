package com.example.prateek.spotmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowsLocation extends AppCompatActivity {
    String mobile;
    TextView person, latitude, longitude;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(getApplicationContext(), "11", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_location);

        mobile = getIntent().getStringExtra("number");
        System.out.println("mobile is"+mobile);
        person = (TextView)findViewById(R.id.mobile);
        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);
        person.setText(mobile.toString());




        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "Locate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       System.out.println(response);

                        try {

                            final JSONObject obj = new JSONObject(response);
                            final JSONArray apps = obj.getJSONArray("apps");
                            final int n = apps.length();
                            for (int i = 0; i < n; ++i) {
                                final JSONObject person = apps.getJSONObject(i);
                                System.out.println(person.getString("latitude"));
                                System.out.println(person.getString("longitude"));
                                latitude.setText(person.getString("latitude"));
                                longitude.setText(person.getString("longitude"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error che");
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", mobile);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }
}
