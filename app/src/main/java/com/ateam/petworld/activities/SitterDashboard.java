package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.adapters.OwnerAppointmentListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.services.AppointmentDataService;

import java.util.ArrayList;
import java.util.List;

public class SitterDashboard extends AppCompatActivity {

    AppointmentDataService appointmentDataService;
    Intent intent;
    private String sitterEmailId;
    private String sitterId;
    private List<Appointments> sitterAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_dashboard);
        ClientFactory.init(this);
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        RecyclerView recyclerView = findViewById(R.id.rv_sitter_appointment_list);
        intent = getIntent();
        sitterEmailId = intent.getStringExtra("emailId");
        sitterId = intent.getStringExtra("sitterId");
        List<Appointments> allAppointmentList = new ArrayList<>();
        allAppointmentList = appointmentDataService.getAllAppointments();
        sitterAppointments = new ArrayList<>();

        for(Appointments appointment : allAppointmentList){
            if(appointment.getSitterId().equals(sitterId)){
                sitterAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new OwnerAppointmentListAdapter(sitterAppointments));
    }
}
