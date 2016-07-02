package com.example.prateek.spotmate;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.media.audiofx.BassBoost;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.TooManyListenersException;

public class home extends Activity {
    String username, contactName="", contactPhone="";
    String phoneArray[], nameArray[], verNameArray[];
    private LocationManager locationManager;
    private LocationListener locationListener;
    ListView lvContact;
    public final String SERVER_ADDRESS = "http://spotmate.freeoda.com/";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
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
            locationManager.requestLocationUpdates("gps", 10000, 0, locationListener);
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
            locationManager.requestLocationUpdates("gps", 10000, 0, locationListener);
        }
        volleyRequest();
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//
//        username = StoreSharePreferances.getUserName(getApplicationContext());
//        String s = StoreSharePreferances.getUserName(getApplicationContext());
//        System.out.println(s+"is the username");

//        Intent intent = new Intent();
//            intent.setClass(this, GetLocationStatus.class);
//            intent.putExtra("username", username);
//            startActivity(intent);
            nowStart();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 10000, 0, locationListener);
                return;
            case 11:
                if(grantResults.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                return;

        }
    }

    public void nowStart(){
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10800000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Toast.makeText(this, "Background Location update has started", Toast.LENGTH_SHORT).show();
    }

    private void showResults(){
            lvContact = (ListView) findViewById(R.id.android_list);
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (phone.length() > 10) {
                    phone = phone.substring(phone.length() - 10);
                }
                //System.out.println(name + "==                 " + phone);
                if(checkNumber(phone)) {
                    if (name != null) {
                        contactName += name + ",";
                        contactPhone += phone + ",";
                    }
                }
            }
            System.out.println(contactName);
            cursor.close();
            nameArray = contactName.split(",");
            phoneArray = contactPhone.split(",");
            ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameArray);
            lvContact.setAdapter(ad);
            lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // Toast.makeText(home.this, phoneArray[position] + "and the " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), ShowsLocation.class);

                    Bundle extras = new Bundle();
                    extras.putString("number",phoneArray[position]);
                    extras.putString("name",nameArray[position]);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

    }

    public void volleyRequest() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SERVER_ADDRESS + "getContacts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        int n = response.length();
                        String str = response.substring(1, n-1);
                        extractContacts(str);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        queue.add(stringRequest);

    }

    public void extractContacts(String str){
        verNameArray = str.split(",");
        int n = verNameArray.length;
        for (int i = 0;i<n;i++)
        {
            int l = verNameArray[i].length();
            verNameArray[i] = verNameArray[i].substring(1, l-1);
            System.out.println(verNameArray[i]);
        }
        showResults();
    }

    public boolean checkNumber(String str){
        int flag=0;
        for(int i=0;i<verNameArray.length;i++)
        {
            if(str.equals(verNameArray[i])){
                flag = 1;
                break;
            }
        }
        if(flag==1){
            return true;
        }
        else
            return false;
    }
}
