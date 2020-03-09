package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

import java.util.List;

public class SearchSitters extends AppCompatActivity {
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ClientFactory.init(this);
        OwnerDataService ownerDataService = new OwnerDataService(ClientFactory.appSyncClient());
        SitterDataService sitterDataService = new SitterDataService(ClientFactory.appSyncClient());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sitters);
        owner = new Owner();
        owner.setId("f0d809ae-4c78-482a-8887-0e331e6f56d6");
        owner = ownerDataService.getOwner(owner);
        List<Sitter> searchSitterList = sitterDataService.searchSitters();
        RecyclerView rvSitterSearchResult = findViewById(R.id.rv_sitter_search_result);
        rvSitterSearchResult.setHasFixedSize(true);
        rvSitterSearchResult.setLayoutManager(new LinearLayoutManager(this));
        TextView tvNoResultsFound = findViewById(R.id.tv_no_result_found);
        final Handler handler = new Handler();
        Owner finalOwner = owner;
        handler.postDelayed(() -> {
            for (Sitter sitter : searchSitterList) {
                sitter.setDistanceFromOwner(DistanceCalculator.calculateDistance(finalOwner.getLocation().getLatitude(), finalOwner.getLocation().getLongitude(),
                        sitter.getLocation().getLatitude(), sitter.getLocation().getLongitude(), "M"));
            }
            if (searchSitterList.size() > 0) {
                tvNoResultsFound.setVisibility(View.GONE);
                rvSitterSearchResult.setAdapter(new SearchSitterListAdapter(searchSitterList, this));
            } else {
                tvNoResultsFound.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void bookAppointment(Sitter sitter) {
        Intent bookAppointmentIntent = new Intent(this, BookAppointment.class);
        bookAppointmentIntent.putExtra("SitterId", sitter.getId());
        bookAppointmentIntent.putExtra("OwnerId", owner.getId());
        startActivity(bookAppointmentIntent);
    }

}
