package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

    OwnerDataService ownerDataService;
    SitterDataService sitterDataService;

    Owner owner;
    Sitter sitter;
    List<Owner> ownerList;
    List<Sitter> sitterList;

    private String emailId;
    private String password;
    private String ownerId;
    private String sitterId;
    private boolean isOwnerClicked;

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

        if (isEmpty) {

            Toast.makeText(this,
                    getString(R.string.login_missing_values),
                    Toast.LENGTH_LONG
            ).show();

        } else {
            String userExists = checkUserExists(isOwnerClicked);
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

        Intent intent = new Intent(this, OwnerDashboard.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("password", password);
        intent.putExtra("ownerId", ownerId);
        startActivity(intent);
    }

    private void loginSitter() {

        Intent intent = new Intent(this, SitterDashboard.class);
        intent.putExtra("emailId", emailId);
        intent.putExtra("password", password);
        intent.putExtra("sitterId", sitterId);
        startActivity(intent);
    }


    private String checkUserExists(boolean isOwnerClicked) {

        String exists = "err400";
        Owner queryOwner = checkOwnerExists(ownerList);
        Sitter querySitter = checkSitterExists(sitterList);

        if(isOwnerClicked == true) {

            if (queryOwner == null || queryOwner.getEmailId() == null) {
                //Owner doesn't exist
                exists = "err400";
                return exists;
            } else {
                exists = "owner";
                //owner.setId(queryOwner.getId());
                ownerId = queryOwner.getId();
                return exists;
            }
        }
        else if(!isOwnerClicked) {
            if (querySitter == null || querySitter.getEmailId() == null) {
                exists = "err400";
                return exists;
            } else {
                exists = "sitter";
                sitterId = querySitter.getId();
                return exists;
            }
        }
        //return exists;
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
        List<Owner> ownerList = ownerDataService.searchOwners();
        for (Owner owner : ownerList) {
            if (owner.getEmailId().equals(emailId) && owner.getPassword().equals(password)) {
                return owner;
            }
        }
        return null;
    }

    private Sitter checkSitterExists(String emailId, String password) {
        List<Sitter> sitterList = sitterDataService.searchSitters(this);
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

    public void onClickSignUp(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.login_owner:
                if (checked)
                    isOwnerClicked = true;
                break;
            case R.id.login_sitter:
                if (checked)
                    isOwnerClicked = false;
                break;
        }
    }
}
