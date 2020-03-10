package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.services.OwnerDataService;
import com.ateam.petworld.services.SitterDataService;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private String emailId;
    private String password;
    private String ownerId;
    private String sitterId;
    OwnerDataService ownerDataService;
    SitterDataService sitterDataService;
    Owner owner;
    Sitter sitter;
    List<Owner> ownerList;
    List<Sitter> sitterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            emailId = savedInstanceState.getString("userEmail");
            password = savedInstanceState.getString("password");
        }
        ClientFactory.init(this);
        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());
        owner = new Owner();
        sitter = new Sitter();
        ownerList = new ArrayList<>();
        sitterList = new ArrayList<>();

        ownerList = ownerDataService.searchOwners();
        sitterList = sitterDataService.searchSitters(this);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstance) {

        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("userEmail", emailId);
        savedInstance.putString("userPassword", password);

    }

    //method that handles the behavior after user has clicked Login
    public void onClickLogin(View view) {


        EditText etEmail = findViewById(R.id.et_user_email_id);
        emailId = etEmail.getText().toString();

        EditText etPassword = findViewById(R.id.et_user_password);
        password = etPassword.getText().toString();

        //check if fields are empty
        boolean isEmpty = checkFieldsEmpty(emailId, password);

        if (isEmpty == true) {

            Toast.makeText(this,
                    getString(R.string.login_missing_values),
                    Toast.LENGTH_LONG
            ).show();

        } else {
            String userExists = checkUserExists();
            if (userExists.equals("err400")) {
                //user doesn't exist
                Toast.makeText(this,
                        getString(R.string.user_absent),
                        Toast.LENGTH_LONG
                ).show();
            } else if (userExists.equals("owner")) {
                loginOwner();
            } else {
                loginSitter();
            }
        }
    }

    private void loginOwner() {

        //Owner owner = new Owner();
        //owner.setEmailId(emailId);
        //owner.setPassword(password);

        Intent intent = new Intent(this, OwnerDashboard.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("password", password);
        intent.putExtra("ownerId", ownerId);
        startActivity(intent);

    }

    private void loginSitter() {

        //Sitter sitter = new Sitter();
        //sitter.setEmailId(emailId);
        //sitter.setPassword(password);

        Intent intent = new Intent(this, SitterDashboard.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("password", password);
        intent.putExtra("sitterId", sitterId);
        startActivity(intent);
    }


    private String checkUserExists() {

        String exists = "err400";
        /*Owner checkOwner = new Owner();
        Sitter checkSitter = new Sitter();

        checkOwner.setEmailId(emailId);
        checkSitter.setEmailId(emailId);

        //Owner queryOwner = checkOwnerExists(emailId,password);
        //Sitter querySitter = checkSitterExists(emailId,password);*/
        Owner queryOwner = checkOwnerExists(ownerList);
        Sitter querySitter = checkSitterExists(sitterList);


        if (queryOwner == null || queryOwner.getEmailId() == null) {
            //Owner doesn't exist
            exists = "err400";
        } else {
            exists = "owner";
            //owner.setId(queryOwner.getId());
            ownerId = queryOwner.getId();
            return exists;
        }

        if (querySitter == null || querySitter.getEmailId() == null) {
            exists = "err400";
        } else {
            exists = "sitter";
            //sitter.setId(querySitter.getId());
            sitterId = querySitter.getId();
            return exists;

        }

        return exists;
    }

    private Owner checkOwnerExists(List<Owner> ownerList) {
        for (Owner owner : ownerList) {

            if (owner.getEmailId().equals(emailId) && owner.getPassword().equals(password)) {
                return owner;
            }
        }

        return null;
    }

    private Sitter checkSitterExists(List<Sitter> sitterList) {
        for (Sitter sitter : sitterList) {

            if (sitter.getEmailId().equals(emailId) && sitter.getPassword().equals(password)) {
                return sitter;
            }
        }

        return null;
    }

    private Owner checkOwnerExists(String emailId, String password) {

        List<Owner> ownerList = new ArrayList<>();

        ownerList = ownerDataService.searchOwners();

        for (Owner owner : ownerList) {

            if (owner.getEmailId().equals(emailId) && owner.getPassword().equals(password)) {
                return owner;
            }
        }

        return null;


    }

    private Sitter checkSitterExists(String emailId, String password) {

        List<Sitter> sitterList = new ArrayList<>();
        sitterList = sitterDataService.searchSitters(this);
        for (Sitter sitter : sitterList) {

            if (sitter.getEmailId().equals(emailId) && sitter.getPassword().equals(password)) {
                return sitter;
            }
        }

        return null;

    }


    private boolean checkFieldsEmpty(String emailId, String password) {

        return emailId == null || emailId.isEmpty() || password == null || password.isEmpty();
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
