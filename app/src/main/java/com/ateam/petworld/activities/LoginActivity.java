package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.services.LocationDataService;
import com.ateam.petworld.services.OwnerDataService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ClientFactory.init(this);
        OwnerDataService ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        Owner owner = new Owner();

        LocationDataService locationDataService = new LocationDataService(ClientFactory.appSyncClient());
        Location location = locationDataService.getLocationById("445597");
        //create
//        Location location = new Location();
//        location.setId("445597");
        owner.setFirstName("Aman");
        owner.setLastName("Bhatia");
        owner.setEmailId("aman@gmail.com");
        owner.setPhoneNumber("9997778888");
        owner.setLocation(location);
        ownerDataService.createOwner(owner);

//        LocationDataService locationDataService = new LocationDataService(awsAppSyncClient);
//        Location location = new Location();
//        location.setId("445597");
//        location.setDisplayName("Irvine, Marion County, Florida, 32686, USA");
//        location.setDisplayAddress("");
//        location.setDisplayPlace("");
//        location.setLongitude(-82.2512098);
//        location.setLatitude(29.4055273);
//        locationDataService.createLocation(location);
        //get
        //owner.setId("39515a0c-bbd0-47b6-87de-95e116757138");
        //ownerDataService.getOwner(owner);
    }


    //method that handles the behavior after user has clicked Login
    public void onClickLogin(View view) {

        Intent intent = new Intent(this, OwnerDashboard.class);
        startActivity(intent);

    }

    /*
        method that handles the behavior after user has clicked SignUp
        creates an intent and starts RegisterActivity
     */

    public void onClickSignUp(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
