package com.example.prateek.spotmate;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GetLocationStatus extends AppCompatActivity {

    String username;


    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 3600000; // in Milliseconds
    protected LocationManager locationManager;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Log.d("here", "1");

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        username = getIntent().getStringExtra("username");
        //updatelocation("100", "100");
        showCurrentLocation();
        finish();


    }

    protected void showCurrentLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d("here", "2");
        if (location != null) {
            Log.d("here", "3");
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            updateLocation(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
            Toast.makeText(GetLocationStatus.this, message,
                    Toast.LENGTH_SHORT).show();
        }
        else
        {Toast.makeText(GetLocationStatus.this, "Location Currently Unavailable",Toast.LENGTH_SHORT).show();}
    }

    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(GetLocationStatus.this, message, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(GetLocationStatus.this, "Provider status changed",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(GetLocationStatus.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(GetLocationStatus.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateLocation(final String latitude, final String longitude){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "SendLocation.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GetLocationStatus.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GetLocationStatus.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longitude", longitude);
                params.put("username", username);

                Log.d("Latitudes= "+latitude, "longitudes= "+ longitude );
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }
}