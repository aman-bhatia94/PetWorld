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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.services.LocationDataService;
import com.ateam.petworld.services.LocationIQRESTService;
import com.ateam.petworld.services.MyLocationService;
import com.ateam.petworld.services.OwnerDataService;
import com.ateam.petworld.services.SitterDataService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    List<Owner> ownerList;
    List<Sitter> sitterList;
    TextView tvLocationText;
    //Android coordinates specific declarations
    int PERMISSION_ID = 44;
    Intent intentLocations;
    ArrayList<String> coordinates = new ArrayList<>();
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private double payPerDay;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private boolean useUserLocation;
    private boolean isOwner;
    private Location fetchedLocation;
    private boolean isLocationPresentInDatabase;
    private LocationDataService locationDataService;
    private OwnerDataService ownerDataService;
    private SitterDataService sitterDataService;
    private boolean isInfoEmpty;
    private boolean isOwnerPresentInDatabase;
    private boolean isSitterPresentInDatabase;
    private LocationIQRESTService locationIQRESTService;
    private MyLocationService locationService;
    private boolean bound = false;
    Intent intentLoginActivity;
    EditText etPayPerDay;

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
        if (savedInstanceState != null) {
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
            isOwnerPresentInDatabase = savedInstanceState.getBoolean("isOwnerPresentInDatabase");
            isSitterPresentInDatabase = savedInstanceState.getBoolean("isSitterPresentInDatabase");
            isInfoEmpty = savedInstanceState.getBoolean("isInfoEmpty");
        }
        tvLocationText = findViewById(R.id.tv_current_location);
        locationIQRESTService = new LocationIQRESTService();
        intentLocations = new Intent(this, MyLocationService.class);
        bindService(intentLocations, connection, Context.BIND_AUTO_CREATE);
        ClientFactory.init(this);
        etPayPerDay = findViewById(R.id.et_pay_per_day);
        locationDataService = new LocationDataService(ClientFactory.appSyncClient());
        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());
        ownerList = new ArrayList<>();
        ownerList = ownerDataService.searchOwners();
        sitterList = new ArrayList<>();
        sitterList = sitterDataService.searchSitters(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id != null) {
            fetchedLocation = new Location();
            fetchedLocation.setDisplayAddress(intent.getStringExtra("displayAddress"));
            fetchedLocation.setDisplayPlace(intent.getStringExtra("displayPlace"));
            fetchedLocation.setDisplayName(intent.getStringExtra("displayName"));
            fetchedLocation.setId(intent.getStringExtra("id"));
            fetchedLocation.setLongitude(intent.getDoubleExtra("lon", 0));
            fetchedLocation.setLatitude(intent.getDoubleExtra("lat", 0));
            tvLocationText.setText(fetchedLocation.getDisplayName());
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                coordinates = locationService.getLastLocation();
            }
        }
        latitude = coordinates.get(0);
        longitude = coordinates.get(1);

        //fetched location based on the coordinates from LocationIQREST done(in RequestPermission)
        System.out.println("latitude: " + latitude);
        System.out.println("longitude" + longitude);
        fetchedLocation = locationIQRESTService.fetchUserLocation(longitude, latitude);

        //creating a delayed handler to handle this event
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (fetchedLocation == null) {
                //couldn't fetch
                System.out.println("could not fetch the location, check the api calls");
            } else {
                //fetched
                System.out.println("fetched the location");
                tvLocationText.setText(fetchedLocation.getDisplayName());
                //check if the location is already present in the database;
                isLocationPresentInDatabase = checkLocationInDatabase(fetchedLocation);


            }
        }, 1000);
    }

    private boolean checkLocationInDatabase(Location fetchedLocation) {

        Location location = locationDataService.getLocation(fetchedLocation);

        return location.getId() != null;

    }

    private void runLocationService() {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //new code
                if (bound) {
                    coordinates = locationService.getLastLocation();
                    if (coordinates.size() == 0 || coordinates.get(0).equals("no_permission")) {
                        //request permission
                        requestPermissions();
                    } else {
                        latitude = coordinates.get(0);
                        longitude = coordinates.get(1);
                    }
                }
                if (!bound) {
                    handler.postDelayed(this, 2000);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {

        super.onSaveInstanceState(savedInstance);
        savedInstance.putString("userEmail", emailId);
        savedInstance.putString("userPassword", password);
        savedInstance.putString("firstName", firstName);
        savedInstance.putString("lastName", lastName);
        savedInstance.putString("location", location);
        savedInstance.putString("phoneNumber", phoneNumber);
        savedInstance.putString("latitude", latitude);
        savedInstance.putString("longitude", longitude);
        savedInstance.putBoolean("useUserLocation", useUserLocation);
        savedInstance.putBoolean("isOwner", isOwner);
        savedInstance.putBoolean("isOwnerPresentInDatabase", isOwnerPresentInDatabase);
        savedInstance.putBoolean("isSitterPresentInDatabase", isSitterPresentInDatabase);
        savedInstance.putBoolean("isInfoEmpty", isInfoEmpty);
    }

    public void onSearchButtonClick(View view) {

        //List<com.ateam.petworld.models.Location> locations = locationIQRESTService.fetchUserLocation(location);
        //give the user an option to select one of these
        Intent intent = new Intent(this, SearchLocation.class);
        startActivity(intent);


    }

    public void onRegisterButtonClick(View view) {

        //Use the fetched location based on the coordinates from LocationIQREST done(in RequestPermission)


        //check that fields are not empty

        //get info from the textfields
        EditText et_firstName = findViewById(R.id.et_first_name);
        firstName = et_firstName.getText().toString();

        EditText et_lastName = findViewById(R.id.et_last_name);
        lastName = et_lastName.getText().toString();

        EditText et_emailId = findViewById(R.id.et_register_email_id);
        emailId = et_emailId.getText().toString();

        EditText et_register_password = findViewById(R.id.et_register_password);
        password = et_register_password.getText().toString();

        EditText et_phoneNumber = findViewById(R.id.et_phone_number);
        phoneNumber = et_phoneNumber.getText().toString();

        payPerDay = isOwner ? 0 : etPayPerDay.getText().toString().isEmpty() ? null : Double.parseDouble(etPayPerDay.getText().toString());


        isInfoEmpty = checkFieldsEmpty(firstName, lastName, emailId, password, phoneNumber, payPerDay);

        if (isInfoEmpty) {
            //create a toast to show user that fields are empty
            Toast.makeText(this,
                    getString(R.string.register_enter_missing_values),
                    Toast.LENGTH_LONG
            ).show();
        } else {
            //check if this location is present in database
            //if not add it
            if (isLocationPresentInDatabase == true) {
                //location is already present so no need to add;
                System.out.println("Location already present, not adding to database");
            } else if (isLocationPresentInDatabase == false) {
                locationDataService.createLocation(fetchedLocation);
                System.out.println("Location added to database");
            }
            //check if owner or sitter
            if (isOwner == true) {
                //check if owner is present already, if yes, dont add him again
                Owner queryOwner = new Owner();
                queryOwner.setEmailId(emailId);
                for (Owner owner : ownerList) {

                    if (owner.getEmailId().equals(queryOwner.getEmailId())) {
                        isOwnerPresentInDatabase = true;
                        Toast.makeText(this,
                                getString(R.string.register_owner_present),
                                Toast.LENGTH_LONG
                        ).show();
                        isOwnerPresentInDatabase = true;
                        break;
                    }
                }

                if (isOwnerPresentInDatabase == false) {
                    Owner owner = new Owner();
                    owner.setEmailId(emailId);
                    owner.setFirstName(firstName);
                    owner.setLastName(lastName);
                    owner.setPassword(password);
                    owner.setPhoneNumber(phoneNumber);
                    owner.setLocation(fetchedLocation);
                    ownerDataService.createOwner(owner, this);
                    Toast.makeText(this,
                            getString(R.string.register_owner_added),
                            Toast.LENGTH_LONG
                    ).show();
                    //goToDashboard(,owner);
                }
            } else if (isOwner == false) {
                //its a sitter
                Sitter querySitter = new Sitter();
                querySitter.setEmailId(emailId);
                for (Sitter sitter : sitterList) {

                    if (sitter.getEmailId().equals(querySitter.getEmailId())) {
                        isSitterPresentInDatabase = true;
                        Toast.makeText(this,
                                getString(R.string.register_sitter_present),
                                Toast.LENGTH_LONG
                        ).show();
                        isSitterPresentInDatabase = true;
                        break;

                    }
                }

                if (isSitterPresentInDatabase == false) {
                    Sitter sitter = new Sitter();
                    sitter.setEmailId(emailId);
                    sitter.setFirstName(firstName);
                    sitter.setLastName(lastName);
                    sitter.setPassword(password);
                    sitter.setPhoneNumber(phoneNumber);
                    sitter.setLocation(fetchedLocation);
                    sitter.setPayPerDay(payPerDay);
                    sitterDataService.createSitter(sitter, this);
                    //goToDashboard(,sitter);
                    Toast.makeText(this,
                            getString(R.string.register_sitter_added),
                            Toast.LENGTH_LONG
                    ).show();

                }
            }
            //new_change344
//
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();


        }
    }

    public void goToDashboard(Sitter sitter) {

        Toast.makeText(this,
                getString(R.string.register_sitter_added),
                Toast.LENGTH_LONG
        ).show();
        Intent intent = new Intent(this, SitterDashboard.class);
        intent.putExtra("emailId", sitter.getEmailId());
        intent.putExtra("password", sitter.getPassword());
        intent.putExtra("sitterId", sitter.getId());
        startActivity(intent);
    }

    public void goToDashboard(Owner owner) {


        Intent intent = new Intent(this, OwnerDashboard.class);
        intent.putExtra("emailId", owner.getEmailId());
        intent.putExtra("password", owner.getPassword());
        intent.putExtra("ownerId", owner.getId());
        startActivity(intent);
    }

    private boolean checkFieldsEmpty(String firstName, String lastName, String emailId, String password, String phoneNumber, Double pay) {

        return firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || emailId == null || emailId.isEmpty()
                || password == null || password.isEmpty()
                || phoneNumber == null || phoneNumber.isEmpty() || (isOwner && pay == null);
    }


    public void onUseMyLocationButtonClick(View view) {

        runLocationService();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_owner:
                if (checked) {
                    etPayPerDay.setVisibility(View.GONE);
                    isOwner = true;
                }
                break;
            case R.id.rb_sitter:
                if (checked) {
                    etPayPerDay.setVisibility(View.VISIBLE);
                    isOwner = false;
                }
                break;
        }
    }
}
