package com.ateam.petworld.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    TextView tvSearchNotFound;
    RecyclerView rvSearchLocationList;
    ImageButton ibSearch;
    SearchLocationListAdapter searchLocationListAdapter;
    List<Location> locations;
    List<Country> countries;
    LocationIQRESTService locationIQRESTService;
    View.OnClickListener searchListener = view -> {
        searchText = String.valueOf(etSearchText.getText());
        int countryIdx = spnCountries.getSelectedItemPosition();
        Country selectedCountry = countries.get(countryIdx);
        locationIQRESTService.autoCompleteLocations(searchText, selectedCountry == null ? "" : selectedCountry.getCode(), this);
    };

    public void setLocationList(List<Location> autoCompleteLocations) {
        if (autoCompleteLocations.size() > 0) {
            searchLocationListAdapter.updateData(autoCompleteLocations);
            searchLocationListAdapter.notifyDataSetChanged();
            tvSearchNotFound.setVisibility(View.GONE);
        } else {
            tvSearchNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        etSearchText = findViewById(R.id.et_search_text);
        ibSearch = findViewById(R.id.ib_search);
        rvSearchLocationList = findViewById(R.id.rv_search_location_result);
        tvSearchNotFound = findViewById(R.id.tv_no_result_found);
        rvSearchLocationList.setHasFixedSize(true);
        rvSearchLocationList.setLayoutManager(new LinearLayoutManager(this));
        locations = new ArrayList<>();
        locationIQRESTService = new LocationIQRESTService();
        searchLocationListAdapter = new SearchLocationListAdapter(locations, this);
        rvSearchLocationList.setAdapter(searchLocationListAdapter);
        spnCountries = findViewById(R.id.spn_country_list);
        PrintfulRESTService printfulRESTService = new PrintfulRESTService();
        printfulRESTService.fetchAllCountries(this);
        ibSearch.setOnClickListener(searchListener);

    }

    public void setCountryList(List<Country> countriesResponse) {
        countries = new ArrayList<>(countriesResponse);
        List<String> items = new ArrayList<>();
        for (Country country : countries) {
            items.add(country.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spnCountries.setAdapter(adapter);
    }


    public void setLocation(Location location) {
        int i = 10;
    }
}
