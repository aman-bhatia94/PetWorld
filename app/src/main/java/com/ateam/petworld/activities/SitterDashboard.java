package com.ateam.petworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.OwnerAppointmentListAdapter;
import com.ateam.petworld.adapters.SitterAppointmentListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.services.AppointmentDataService;

import java.util.ArrayList;
import java.util.List;

public class SitterDashboard extends AppCompatActivity {

    Context context;
    AppointmentDataService appointmentDataService;
    Intent intent;
    private String sitterEmailId;
    private String sitterId;
    private List<Appointments> sitterAppointments;
    Button updateProfileButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_dashboard);
        ClientFactory.init(this);
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        recyclerView = findViewById(R.id.rv_sitter_appointment_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateProfileButton = findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(updateProfileListener);
        intent = getIntent();
        sitterEmailId = intent.getStringExtra("emailId");
        sitterId = intent.getStringExtra("sitterId");
        List<Appointments> allAppointmentList = new ArrayList<>();
        allAppointmentList = appointmentDataService.getAllAppointments(this);
        sitterAppointments = new ArrayList<>();

        for(Appointments appointment : allAppointmentList){
            if(appointment.getSitterId().equals(sitterId)){
                sitterAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new SitterAppointmentListAdapter(sitterAppointments));
    }

    View.OnClickListener updateProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,Profile.class);
            intent.putExtra("sitterId",sitterId);
            startActivity(intent);
        }
    } ;

    public void setSitterAppointmentList(List<Appointments> allAppointmentList) {
        sitterAppointments = new ArrayList<>();
        for(Appointments appointment : allAppointmentList){
            if(appointment.getSitterId().equals(sitterId)){
                sitterAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new SitterAppointmentListAdapter(sitterAppointments));
    }
}
