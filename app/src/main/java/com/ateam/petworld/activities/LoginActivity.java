package com.ateam.petworld.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ateam.petworld.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    //method that handles the behavior after user has clicked Login
    public void onClickLogin(View view) {

        Intent intent = new Intent(this,OwnerDashboard.class);
        startActivity(intent);

    }

    /*
        method that handles the behavior after user has clicked SignUp
        creates an intent and starts RegisterActivity
     */

    public void onClickSignUp(View view) {

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
