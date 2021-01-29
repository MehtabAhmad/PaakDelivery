package com.paak.delivery.BackgroundServices.Location;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationClient {

    public FusedLocationProviderClient fusedLocationClient;
    private static LocationClient locationClient;

    private LocationClient(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public static LocationClient getInstance(Context context) {
        if (locationClient == null) {
            locationClient = new LocationClient(context);
        }
        return locationClient;
    }
}
