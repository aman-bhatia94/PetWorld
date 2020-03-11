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

public class Profile extends AppCompatActivity {
    EditText etFirstName;
    EditText etLastName;
    EditText etPayPerDay;
    EditText etPassword;
    OwnerDataService ownerDataService;
    SitterDataService sitterDataService;
    boolean isOwner;
    Owner owner;
    Sitter sitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientFactory.init(this);
        setContentView(R.layout.activity_profile);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPayPerDay = findViewById(R.id.et_pay_per_day);
        etPassword = findViewById(R.id.et_user_password);
        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());

        Intent intent = getIntent();
        String ownerId = intent.getStringExtra("ownerId");
        String sitterId = intent.getStringExtra("sitterId");

        if (sitterId == null) {
            etPayPerDay.setVisibility(View.GONE);
            isOwner = true;
            owner = new Owner();
            owner.setId(ownerId);
            ownerDataService.getOwner(owner, this);
        } else {
            isOwner = false;
            sitter = new Sitter();
            sitter.setId(sitterId);
            sitterDataService.getSitter(sitter, this);
        }
    }

    public void saveProfileChanges(View view) {
        String first = etFirstName.getText().toString();
        String last = etLastName.getText().toString();
        String pay = etPayPerDay.getText().toString();
        String password = etPassword.getText().toString();

        if (first.isEmpty() || last.isEmpty() || pay.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    getString(R.string.errMsg1),
                    Toast.LENGTH_LONG
            ).show();
        } else {
            if (isOwner) {
                Owner owner = new Owner();
                owner.setFirstName(first);
                owner.setLastName(last);
                owner.setPassword(password);
                ownerDataService.updateOwner(owner);
            } else {
                Sitter sitter = new Sitter();
                sitter.setFirstName(first);
                sitter.setLastName(last);
                sitter.setPassword(password);
                sitter.setPayPerDay(Double.parseDouble(pay));
                sitterDataService.updateSitter(sitter);
            }

        }


    }

    public void setOwner(Owner owner) {
        etFirstName.setText(owner.getFirstName());
        etLastName.setText(owner.getLastName());
        etPassword.setText(owner.getPassword());

    }

    public void setSitter(Sitter sitter) {
        etPayPerDay.setText(String.valueOf(sitter.getPayPerDay()));
        etFirstName.setText(sitter.getFirstName());
        etLastName.setText(sitter.getLastName());
        etPassword.setText(sitter.getPassword());

    }
}

