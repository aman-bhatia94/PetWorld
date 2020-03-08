package com.ateam.petworld.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.services.LocationDataService;
import com.ateam.petworld.services.LocationIQRESTService;
import com.ateam.petworld.services.OwnerDataService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private boolean useUserLocation;
    private com.ateam.petworld.models.Location userLocationData;
    private Owner owner;
    private AWSAppSyncClient awsAppSyncClient;

    OwnerDataService ownerDataService;
    LocationDataService locationDataService;
    //Location specific declarations
    private int PERMISSION_ID = 1;
    FusedLocationProviderClient mFusedLocationClient;

    //service specific declarations
    private LocationIQRESTService locationIQRESTService;
    private Intent locationRESTIntent;
    private boolean bound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            LocationIQRESTService.LocationIQBinder locationIQBinder
                    = (LocationIQRESTService.LocationIQBinder) binder;

            locationIQRESTService = locationIQBinder.getLocationRESTServiceBinder();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //unbounded from service
            bound = false;
        }
    };

    //source https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    //source https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    //source https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    //source https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                getLastLocation();
            }
        }
    }

    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude()+"";
                                    longitude = location.getLongitude()+"";
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            latitude = mLastLocation.getLatitude()+"";
            longitude = mLastLocation.getLongitude()+"";

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_register);
        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        locationDataService = new LocationDataService(ClientFactory.appSyncClient());
        if(savedInstanceState != null){

            emailId = savedInstanceState.getString("userEmail");
            password = savedInstanceState.getString("password");
            firstName = savedInstanceState.getString("firstName");
            lastName = savedInstanceState.getString("lastName");
            location = savedInstanceState.getString("location");
            phoneNumber = savedInstanceState.getString("phoneNumber");
            latitude = savedInstanceState.getString("latitude");
            longitude = savedInstanceState.getString("longitude");
            useUserLocation = savedInstanceState.getBoolean("useUserLocation");

            locationRESTIntent = new Intent(this,LocationIQRESTService.class);
            if(latitude == null || longitude == null){
                bindService(locationRESTIntent,connection,Context.BIND_AUTO_CREATE);
            }

        }
        else {
            locationRESTIntent = new Intent(this, LocationIQRESTService.class);
            ClientFactory.init(this);
            bindService(locationRESTIntent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {

        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("userEmail",emailId);
        savedInstance.putString("userPassword",password);
        savedInstance.putString("firstName",firstName);
        savedInstance.putString("lastName",lastName);
        savedInstance.putString("location",location);
        savedInstance.putString("phoneNumber",phoneNumber);
        savedInstance.putString("latitude",latitude);
        savedInstance.putString("longitude",longitude);
        savedInstance.putBoolean("useUserLocation",useUserLocation);

    }



    public void onSearchButtonClick(View view) {

        List<com.ateam.petworld.models.Location> locations = locationIQRESTService.fetchUserLocation(location);
        //give the user an option to select one of these


    }

    public void onRegisterButtonClick(View view) {

        //add location only if not already present
        com.ateam.petworld.models.Location location = locationDataService.getLocation(userLocationData);
        if(location != null){
            //location already exists

        }
        else{
            locationDataService.createLocation(userLocationData);
        }
        //add owner data
        owner = new Owner();
        owner.setEmailId(emailId);
        owner.setPassword(password);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setPhoneNumber(phoneNumber);
        owner.setLocation(userLocationData);

        ownerDataService.createOwner(owner);

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);


    }

    public void onUseMyLocationButtonClick(View view) {

        useUserLocation = true;
        getLastLocation();
        userLocationData = locationIQRESTService.fetchUserLocation(longitude,latitude);

        //if location already exists
        //just map it to the owner



    }
}
