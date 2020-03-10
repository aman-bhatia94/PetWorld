package com.ateam.petworld.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MyLocationService extends Service {

    private final IBinder binder = new LocationBinder();
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private String latitude;
    private String longitude;
    private ArrayList<String> coordinates = new ArrayList<>();
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            coordinates.add(latitude);
            coordinates.add(longitude);
        }
    };

    @SuppressLint("MissingPermission")
    public ArrayList<String> getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
                                latitude = location.getLatitude() + "";
                                longitude = location.getLongitude() + "";
                                coordinates.add(latitude);
                                coordinates.add(longitude);

                            }
                        }
                );
                return coordinates;
            } else {
                Toast.makeText(this, "Turn on location and restart the application", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            coordinates.add("no_permission");
            coordinates.add("no_permission");
            return coordinates;
        }
        return coordinates;
    }

    //called when a component wants to bind to a service
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("I am bound now ");
        return binder;
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    public class LocationBinder extends Binder {

        public MyLocationService getLoc() {
            return MyLocationService.this;
        }
    }
}

