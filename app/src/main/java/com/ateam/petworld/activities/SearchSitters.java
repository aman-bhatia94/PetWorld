package com.ateam.petworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.SearchSitterListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.services.OwnerDataService;
import com.ateam.petworld.services.SitterDataService;
import com.ateam.petworld.utils.DistanceCalculator;
import com.ateam.petworld.utils.ListFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchSitters extends AppCompatActivity {
    Owner owner;
    Context context;

    List<String> sortingList;
    List<Sitter> sitterList;
    List<Sitter> sitterFullList;

    SearchSitterListAdapter searchSitterListAdapter;

    TextView tvNoResultsFound;
    EditText etSearchText;
    RecyclerView rvSitterSearchResult;
    Spinner spnSitterSortList;

    SitterDataService sitterDataService;
    OwnerDataService ownerDataService;

    private AdapterView.OnItemSelectedListener sortingListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (searchSitterListAdapter == null)
                return;
            searchSitterListAdapter.sortBy(i);
            searchSitterListAdapter.notifyDataSetChanged();
            rvSitterSearchResult.scrollToPosition(0);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void afterTextChanged(Editable editable) {
            String searchText = editable.toString();
            List<Sitter> filteredSearchList;
            if (searchText.trim().isEmpty()) {
                filteredSearchList = new ArrayList<>(sitterFullList);
            } else {
                searchText = searchText.toLowerCase();
                String finalSearchText = searchText;
                Predicate<Sitter> byName = sitter -> sitter.getFirstName().toLowerCase().contains(finalSearchText) || sitter.getLastName().toLowerCase().contains(finalSearchText);
                filteredSearchList = sitterFullList.stream().filter(byName).collect(Collectors.toList());
            }
            rvSitterSearchResult.scrollToPosition(0);
            searchSitterListAdapter.updateDataSet(filteredSearchList);
            searchSitterListAdapter.notifyDataSetChanged();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sitters);
        init();
        owner = ownerDataService.getOwner(owner);
        sitterList = sitterDataService.searchSitters(this);
        setViewListeners();
    }

    private void init() {
        ClientFactory.init(this);
        context = this;
        sitterList = new ArrayList<>();

        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());

        etSearchText = findViewById(R.id.et_search_sitter_name_text);
        rvSitterSearchResult = findViewById(R.id.rv_sitter_search_result);
        tvNoResultsFound = findViewById(R.id.tv_no_result_found);
        spnSitterSortList = findViewById(R.id.spn_sort_by_list);

        sortingList = new ArrayList<String>() {{
            add("Default");
            add("Name");
            add("Price");
            add("Distance");
        }};

        spnSitterSortList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortingList));

        owner = new Owner();
        Intent intent = getIntent();
        String id = intent.getStringExtra("ownerId");
        owner.setId(id);

        rvSitterSearchResult.setHasFixedSize(true);
        rvSitterSearchResult.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSearchSitterResult(List<Sitter> sitterListResult) {
        Owner finalOwner = owner;
        if (sitterListResult == null)
            return;
        sitterList = new ArrayList<>(sitterListResult);
        for (Sitter sitter : sitterList) {
            sitter.setDistanceFromOwner(DistanceCalculator.calculateDistance(finalOwner.getLocation().getLatitude(), finalOwner.getLocation().getLongitude(),
                    sitter.getLocation().getLatitude(), sitter.getLocation().getLongitude(), "M"));
        }
        if (sitterList.size() > 0) {
            sitterList = sitterList
                    .stream()
                    .filter(ListFunctions.distinctByKeys(Sitter::getId))
                    .collect(Collectors.toList());
            tvNoResultsFound.setVisibility(View.GONE);
            searchSitterListAdapter = new SearchSitterListAdapter(sitterList, this);
            sitterFullList = new ArrayList<>(sitterList);
            rvSitterSearchResult.setAdapter(searchSitterListAdapter);
        } else {
            tvNoResultsFound.setVisibility(View.VISIBLE);
        }
    }

    private void setViewListeners() {
        etSearchText.addTextChangedListener(searchTextWatcher);
        spnSitterSortList.setOnItemSelectedListener(sortingListener);
    }

    public void bookAppointment(Sitter sitter) {
        Intent bookAppointmentIntent = new Intent(this, BookAppointment.class);
        bookAppointmentIntent.putExtra("SitterId", sitter.getId());
        bookAppointmentIntent.putExtra("OwnerId", owner.getId());
        startActivity(bookAppointmentIntent);
    }
}
