package com.example.prateek.spotmate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by prateek on 29/6/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
       // Toast.makeText(arg0, "I'm running spotmate", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (arg0, GetLocationStatus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(intent);
    }
}
