package com.example.prateek.spotmate;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShowsLocation extends AppCompatActivity {
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";
    String mobile, name, lat, lng, day;
    TextView item_ph, item_name, latitude, longitude, lastSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_location);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mobile = extras.getString("number");
        name = extras.getString("name");

        System.out.println("mobile is" + mobile);
        item_ph = (TextView) findViewById(R.id.mobile);
        item_name = (TextView) findViewById(R.id.ID);
        item_ph.setText(mobile.toString());
        item_name.setText(name.toString());
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        lastSeen = (TextView) findViewById(R.id.lastSeen);


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
                                System.out.println(person.getString("datetime"));
                                lng = person.getString("longitude");
                                lat = person.getString("latitude");
                                latitude.setText(person.getString("latitude"));
                                longitude.setText(", " + person.getString("longitude"));
                                lastSeen.setText(person.getString("datetime"));
                                day = person.getString("datetime");
                                String dayCrop = day.substring(0, Math.min(day.length(), 10));
                                System.out.println(dayCrop);
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String displayDate = dateFormat.format(date);
                                System.out.println(displayDate);
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

        Button onMap = (Button) findViewById(R.id.map);
        assert onMap != null;
        onMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng;

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(i);

            }
        });
    }
}
