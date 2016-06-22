package com.example.prateek.spotmate;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home extends Activity {
    int i = 1;
    String username, contactName="", contactPhone="";
    String phoneArray[], nameArray[];
    private LocationManager locationManager;
    private LocationListener locationListener;
    Button button;
    ListView lvContact;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(home.this, location.getLatitude()+  " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10 );
                return;
            }
        }else{
            locationManager.requestLocationUpdates("gps", 3600000, 0, locationListener);
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.INTERNET
                }, 11 );
                return;
            }
        }else{
            locationManager.requestLocationUpdates("gps", 3600000, 0, locationListener);
        }
        showResults();

//
//        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng ;
//
//        Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//        startActivity(i);

        username = getIntent().getStringExtra("username");

        Intent intent = new Intent();
            intent.setClass(this, GetLocationStatus.class);
            intent.putExtra("username", username);
            startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 3600000, 0, locationListener);
                return;
            case 11:
                if(grantResults.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    //showResults();
                return;

        }
    }

    private void showResults(){

        lvContact = (ListView)findViewById(R.id.android_list);
        ContentResolver resolver=getContentResolver();
        Cursor cursor= resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(phone.length()>10)
            phone= phone.substring(phone.length() - 11);

            if(name!=null)
            {
                contactName += name +",";
                contactPhone += phone +",";
            }
        }
        cursor.close();
        nameArray = contactName.split(",");
        phoneArray = contactPhone.split(",");


        for(int i= 0;i<phoneArray.length;i++){
           // if(verifyContact(phoneArray[i])==true){

                // nilesh nilesh nilesh nilesh\
                //you'll have to make this, I am unable to code it, I am getting some weird errors
                //if it is true it means that the number is registered, now store both of them with the same index into two new
                //separate arrays and those arrays will be the one's who have registered
            //}
        }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, nameArray);
                lvContact.setAdapter(adapter);
//                lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String num = verPhoneArray[position];
//                        Toast.makeText(home.this, num, Toast.LENGTH_SHORT).show();
//                    }
//                });

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
    boolean flag;
    private boolean verifyContact(final String mobile) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "getContacts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            //Toast.makeText(getApplicationContext(), "booywah", Toast.LENGTH_SHORT).show();
                            flag = true;

                        } else {
                            flag = false;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      // Toast.makeText(getApplicationContext(), "error hai bhencho", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                }
        )

        {
            final String num = mobile;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", num);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        requestQueue.add(stringRequest);
        if(flag == true)
            return true;
        else
            return false;
    }

}
