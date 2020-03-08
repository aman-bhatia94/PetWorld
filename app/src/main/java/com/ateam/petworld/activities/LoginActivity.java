package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.services.LocationDataService;
import com.ateam.petworld.services.OwnerDataService;

public class LoginActivity extends AppCompatActivity {

    private String emailId;
    private String password;
    OwnerDataService ownerDataService;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null){

        }
        ClientFactory.init(this);
        OwnerDataService ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        Owner owner = new Owner();
        LocationDataService locationDataService = new LocationDataService(ClientFactory.appSyncClient());
        Location location = new Location();
        //Location location = locationDataService.getLocationById("445597");
//        create
//        Location location = new Location();
//        location.setId("445597");
//        owner.setFirstName("Aman");
//        owner.setLastName("Bhatia");
//        owner.setEmailId("aman@gmail.com");
//        owner.setPhoneNumber("9997778888");
//        owner.setLocation(location);
//        ownerDataService.createOwner(owner);

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


    @Override
    public void onSaveInstanceState(Bundle savedInstance) {

        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("userEmail",emailId);
        savedInstance.putString("userPassword",password);

    }
    //method that handles the behavior after user has clicked Login
    public void onClickLogin(View view) {


        EditText etEmail = (EditText)findViewById(R.id.et_user_email_id);
        emailId = etEmail.getText().toString();

        EditText etPassword = (EditText)findViewById(R.id.et_user_password);
        password = etPassword.getText().toString();

        //check if this user exists or not
        owner.setEmailId(emailId);
        owner.setPassword(password);

        Owner ownerExists = ownerDataService.getOwner(owner);
        if(ownerExists == null){
            Toast toast = Toast.makeText(getApplicationContext(),"User doesn't exist",Toast.LENGTH_LONG);
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, OwnerDashboard.class);
            intent.putExtra("owner",ownerExists);
            startActivity(intent);
        }



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
