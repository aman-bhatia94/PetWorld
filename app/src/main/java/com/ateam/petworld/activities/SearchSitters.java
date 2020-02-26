package com.ateam.petworld.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        RecyclerView recyclerView = findViewById(R.id.rv_sitter_search_result);
        List<Sitter> searchSitterList = new ArrayList<>();
        Sitter sitter = new Sitter();
        sitter.setFirstName("Thomas");
        sitter.setLastName("Edison");
        searchSitterList.add(sitter);
        recyclerView.setAdapter(new SearchSitterListAdapter(searchSitterList));
    }
}
