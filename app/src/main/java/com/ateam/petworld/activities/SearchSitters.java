package com.ateam.petworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class SearchSitters extends AppCompatActivity {
    Owner owner;
    Context context;

    List<String> sortingList;
    List<Sitter> searchSitterFullList;
    List<Sitter> filteredSearchList;

    TextView tvNoResultsFound;
    EditText etSearchText;
    RecyclerView rvSitterSearchResult;

    SitterDataService sitterDataService;
    OwnerDataService ownerDataService;
    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            filteredSearchList = searchSitterFullList.
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sitters);
        init();
        owner = ownerDataService.getOwner(owner);
        searchSitterFullList = sitterDataService.searchSitters();
        setSearchSitterResult();
        setViewListeners();
    }

    private void init() {
        ClientFactory.init(this);
        context = this;

        ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());

        etSearchText = findViewById(R.id.et_search_sitter_name_text);
        rvSitterSearchResult = findViewById(R.id.rv_sitter_search_result);
        tvNoResultsFound = findViewById(R.id.tv_no_result_found);

        sortingList = new ArrayList<String>() {{
            add("name");
            add("price");
            add("distance");
        }};

        owner = new Owner();
        owner.setId("f0d809ae-4c78-482a-8887-0e331e6f56d6");

        rvSitterSearchResult.setHasFixedSize(true);
        rvSitterSearchResult.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setSearchSitterResult() {
        final Handler handler = new Handler();
        Owner finalOwner = owner;
        handler.postDelayed(() -> {
            filteredSearchList = new ArrayList<>(searchSitterFullList);
            for (Sitter sitter : searchSitterFullList) {
                sitter.setDistanceFromOwner(DistanceCalculator.calculateDistance(finalOwner.getLocation().getLatitude(), finalOwner.getLocation().getLongitude(),
                        sitter.getLocation().getLatitude(), sitter.getLocation().getLongitude(), "M"));
            }
            if (searchSitterFullList.size() > 0) {
                tvNoResultsFound.setVisibility(View.GONE);
                rvSitterSearchResult.setAdapter(new SearchSitterListAdapter(searchSitterFullList, this));
            } else {
                tvNoResultsFound.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    private void setViewListeners() {
        etSearchText.addTextChangedListener(searchTextWatcher);
    }

    public void bookAppointment(Sitter sitter) {
        Intent bookAppointmentIntent = new Intent(this, BookAppointment.class);
        bookAppointmentIntent.putExtra("SitterId", sitter.getId());
        bookAppointmentIntent.putExtra("OwnerId", owner.getId());
        startActivity(bookAppointmentIntent);
    }
}
