package com.ateam.petworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Appointment;
import com.ateam.petworld.R;
import com.ateam.petworld.adapters.OwnerAppointmentListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.services.AppointmentDataService;

import java.util.ArrayList;
import java.util.List;

public class OwnerDashboard extends AppCompatActivity {
    Context context;
    AppointmentDataService appointmentDataService;
    Intent intent;
    private String ownerEmailId;
    private String ownerId;
    private List<Appointments> ownerAppointments;
    Button bookButton;
    Button updateProfileButton;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        context = this;
        ClientFactory.init(this);
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        recyclerView = findViewById(R.id.rv_owner_appointment_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookButton = findViewById(R.id.bookAppointmentButton);
        bookButton.setOnClickListener(bookListener);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(updateProfileListener);
        intent = getIntent();
        ownerEmailId = intent.getStringExtra("emailId");
        ownerId = intent.getStringExtra("ownerId");
        List<Appointments> allAppointmentList = new ArrayList<>();
        allAppointmentList = appointmentDataService.getAllAppointments(this);
//        setOwnerAppointmentList();
//        ownerAppointments = new ArrayList<>();
//
//        for(Appointments appointment : allAppointmentList){
//            if(appointment.getOwnerId().equals(ownerId)){
//                ownerAppointments.add(appointment);
//            }
//        }
//        recyclerView.setAdapter(new OwnerAppointmentListAdapter(ownerAppointments));
    }
    View.OnClickListener bookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,SearchSitters.class);
            intent.putExtra("ownerId",ownerId);
            startActivity(intent);
        }
    } ;

    View.OnClickListener updateProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,Profile.class);
            intent.putExtra("ownerId",ownerId);
            startActivity(intent);
        }
    } ;

    public void setOwnerAppointmentList(List<Appointments> allAppointmentList) {
        ownerAppointments = new ArrayList<>();
        for(Appointments appointment : allAppointmentList){
            if(appointment.getOwnerId().equals(ownerId)){
                ownerAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new OwnerAppointmentListAdapter(ownerAppointments));
    }


}
