package com.ateam.petworld.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.SearchSitterListAdapter;
import com.ateam.petworld.models.Sitter;

import java.util.ArrayList;
import java.util.List;

public class SearchSitters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sitters);
        RecyclerView rvSitterSearchResult = findViewById(R.id.rv_sitter_search_result);
        List<Sitter> searchSitterList = new ArrayList<>();
        Sitter sitter = new Sitter();
        sitter.setFirstName("Thomas");
        sitter.setLastName("Edison");
        searchSitterList.add(sitter);
        rvSitterSearchResult.setHasFixedSize(true);
        rvSitterSearchResult.setLayoutManager(new LinearLayoutManager(this));
        TextView tvNoResultsFound = findViewById(R.id.tv_no_result_found);
        if (searchSitterList.size() > 0) {
            tvNoResultsFound.setVisibility(View.GONE);
            rvSitterSearchResult.setAdapter(new SearchSitterListAdapter(searchSitterList));
        } else {
            tvNoResultsFound.setVisibility(View.VISIBLE);
        }
    }
}
