package com.ateam.petworld.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.OwnerAppointmentListAdapter;
import com.ateam.petworld.models.Appointments;

import java.util.ArrayList;
import java.util.List;

public class SitterDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_dashboard);
        RecyclerView recyclerView = findViewById(R.id.rv_sitter_appointment_list);
        List<Appointments> sitterAppointmentList = new ArrayList<>();
        recyclerView.setAdapter(new OwnerAppointmentListAdapter(sitterAppointmentList));

    }
}
