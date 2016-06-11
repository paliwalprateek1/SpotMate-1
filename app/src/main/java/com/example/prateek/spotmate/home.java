package com.example.prateek.spotmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        double lat=28.6129;
//        double lng=77.2295;

//        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng ;
//
//        Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            Toast.makeText(home.this, "App to ban jaane de Chutiye!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, GetLocationStatus.class);
//            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
