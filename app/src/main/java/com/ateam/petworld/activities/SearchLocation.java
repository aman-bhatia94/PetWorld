package com.ateam.petworld.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.SearchLocationListAdapter;
import com.ateam.petworld.models.Country;
import com.ateam.petworld.models.Location;
import com.ateam.petworld.services.LocationIQRESTService;
import com.ateam.petworld.services.PrintfulRESTService;

import java.util.ArrayList;
import java.util.List;

public class SearchLocation extends AppCompatActivity {
    String searchText;
    Spinner spnCountries;
    EditText etSearchText;
    RecyclerView rvSearchLocationList;
    SearchLocationListAdapter searchLocationListAdapter;
    List<Location> locations;
    List<Country> countries;
    LocationIQRESTService locationIQRESTService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        etSearchText = findViewById(R.id.et_search_text);
        rvSearchLocationList = findViewById(R.id.rv_search_location_result);
        locations = new ArrayList<>();
        locationIQRESTService = new LocationIQRESTService();
        searchLocationListAdapter = new SearchLocationListAdapter(locations, this);
        rvSearchLocationList.setAdapter(searchLocationListAdapter);
        spnCountries = findViewById(R.id.spn_country_list);
        PrintfulRESTService printfulRESTService = new PrintfulRESTService();
        countries = printfulRESTService.fetchAllCountries();
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
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
                String newSearchText = String.valueOf(etSearchText.getText());
                if (newSearchText.length() > 2 && !newSearchText.equals(searchText)) {
                    if (locations != null)
                        locations.clear();
                    searchText = newSearchText;
                    int countryIdx = spnCountries.getSelectedItemPosition();
                    Country selectedCountry = countries.get(countryIdx);
                    locations = locationIQRESTService.autoCompleteLocations(searchText, selectedCountry == null ? "" : selectedCountry.getCode());
                    searchLocationListAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void setLocation(Location location) {

    }
}
