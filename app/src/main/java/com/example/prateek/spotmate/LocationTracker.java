package com.example.prateek.spotmate;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by nilesh on 7/6/16.
 */
public class LocationTracker extends Service implements LocationListener {
    /**
     * A function to check network availability;
     * @return
     */
    boolean isLocationEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

        }


}
