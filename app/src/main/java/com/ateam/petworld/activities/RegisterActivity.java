package com.ateam.petworld.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ateam.petworld.R;

import com.ateam.petworld.models.Location;

import com.ateam.petworld.services.LocationIQRESTService;
import com.ateam.petworld.services.MyLocationService;


import java.util.ArrayList;


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
    private boolean isOwner;
    private Location fetchedLocation;

    private LocationIQRESTService locationIQRESTService;

    //Android coordinates specific declarations
    int PERMISSION_ID = 44;
    private MyLocationService locationService;
    Intent intentLocations;
    ArrayList<String> coordinates = new ArrayList<>();
    private boolean bound = false;

    //Create a service connection
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            MyLocationService.LocationBinder locationBinder =
                    (MyLocationService.LocationBinder) binder;

            locationService = locationBinder.getLoc();
            bound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            isOwner = savedInstanceState.getBoolean("isOwner");
            bindService(intentLocations,connection, Context.BIND_AUTO_CREATE);
        }
        locationIQRESTService = new LocationIQRESTService();
        intentLocations = new Intent(this,MyLocationService.class);
        bindService(intentLocations,connection, Context.BIND_AUTO_CREATE);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                coordinates = locationService.getLastLocation();
            }
        }
        latitude = coordinates.get(0);
        longitude = coordinates.get(1);

        //fetched location based on the coordinates from LocationIQREST done(in RequestPermission)
        System.out.println("latitude: "+latitude);
        System.out.println("longitude"+longitude);
        fetchedLocation = locationIQRESTService.fetchUserLocation(longitude,latitude);

        //creating a delayed handler to handle this event
        final Handler handler = new Handler();

        if(fetchedLocation == null){
            //couldn't fetch
            System.out.println("could not fetch the location");
        }
        else{
            //fetched
            System.out.println("fetched the location");
        }

        /*handler.postDelayed(() -> {
            if(fetchedLocation == null){
                //couldn't fetch
                System.out.println("could not fetch the location");
            }
            else{
                //fetched
                System.out.println("fetched the location");
            }
        }, 500);*/
    }

    private void runLocationService() {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //new code
                if(bound){
                    coordinates = locationService.getLastLocation();
                    if(coordinates.size() == 0 || coordinates.get(0).equals("no_permission")){
                        //request permission
                        requestPermissions();
                    }
                    else{
                        latitude = coordinates.get(0);
                        longitude = coordinates.get(1);
                    }
                }
                if(!bound){
                    handler.postDelayed(this,1000);
                }
            }
        });

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
        savedInstance.putBoolean("isOwner",isOwner);
    }

    public void onSearchButtonClick(View view) {

        //List<com.ateam.petworld.models.Location> locations = locationIQRESTService.fetchUserLocation(location);
        //give the user an option to select one of these

    }

    public void onRegisterButtonClick(View view) {

        //Use the fetched location based on the coordinates from LocationIQREST done(in RequestPermission)

        //check if this location is present in database

        //if not add it

        //check if owner or sitter

        //check if the user is already present, based on email id

        //if present dont add
        //else
        //add to the database

    }

    public void onUseMyLocationButtonClick(View view) {

        runLocationService();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_owner:
                if (checked)
                    isOwner = true;
                    break;
            case R.id.rb_sitter:
                if (checked)
                    isOwner = false;
                    break;
        }
    }
}
