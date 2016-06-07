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

    private final Context mContext;

    /**
     * A function to check network availability;
     * @return
     */
    boolean isLocationEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    boolean canGetLocation=false;

    Location location;
    double latitude;
    double longitude;

    private static final long distanceIntercept=10; // Minimum distance intercept to update the table with new location.(10 meters)

    private static final long timeIntercept=30*1000; // Time interval at which the location is to be updated. i.e. 30sec.

    private LocationManager locationManager;

    public LocationTracker(Context context){
        this.mContext=context;
        getLocation();
    }


}
