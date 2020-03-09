package com.ateam.petworld.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.ateam.petworld.R;
import com.ateam.petworld.models.Country;
import com.ateam.petworld.services.PrintfulRESTService;

import java.util.ArrayList;
import java.util.List;

public class SearchLocation extends AppCompatActivity {
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        PrintfulRESTService printfulRESTService = new PrintfulRESTService();
        List<Country> countries = printfulRESTService.fetchAllCountries();
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Spinner spnCountries = findViewById(R.id.spn_country_list);
            List<String> items = new ArrayList<>();
            for (Country country : countries) {
                items.add(country.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            spnCountries.setAdapter(adapter);
        }, 1000);
        getLocations();
    }

    private void getLocations() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //new code
                handler.postDelayed(this, 1000);
            }
        });
    }

}
