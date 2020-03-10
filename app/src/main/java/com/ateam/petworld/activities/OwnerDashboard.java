package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Appointment;
import com.ateam.petworld.R;
import com.ateam.petworld.adapters.OwnerAppointmentListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.services.AppointmentDataService;

import java.util.ArrayList;
import java.util.List;

public class OwnerDashboard extends AppCompatActivity {

    AppointmentDataService appointmentDataService;
    Intent intent;
    private String ownerEmailId;
    private List<Appointments> ownerAppointments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        ClientFactory.init(this);
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        RecyclerView recyclerView = findViewById(R.id.rv_owner_appointment_list);

        intent = getIntent();
        ownerEmailId = intent.getStringExtra("emailId");
        List<Appointments> allAppointmentList = new ArrayList<>();
        allAppointmentList = appointmentDataService.getAllAppointments();
        ownerAppointments = new ArrayList<>();

        for(Appointments appointment : allAppointmentList){
            if(appointment.getOwner().getEmailId().equals(ownerEmailId)){
                ownerAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new OwnerAppointmentListAdapter(ownerAppointments));
    }
}
