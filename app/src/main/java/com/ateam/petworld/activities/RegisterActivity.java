package com.ateam.petworld.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;

public class RegisterActivity extends AppCompatActivity {

    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private String phoneNumber;

    private AWSAppSyncClient awsAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();*/

        if(savedInstanceState != null){

        }
        ClientFactory.init(this);
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

    }



    public void onSearchButtonClick(View view) {



    }

    public void onRegisterButtonClick(View view) {



    }

    public void onUseMyLocationButtonClick(View view) {



    }
}
