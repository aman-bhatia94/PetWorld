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
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.services.AppointmentDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OwnerDashboard extends AppCompatActivity {
    Context context;
    AppointmentDataService appointmentDataService;
    Intent intent;

    Button bookButton;
    Button updateProfileButton;
    RecyclerView recyclerView;

    private String ownerEmailId;
    private String ownerId;

    View.OnClickListener bookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SearchSitters.class);
            intent.putExtra("ownerId", ownerId);
            startActivity(intent);
        }
    };
    View.OnClickListener updateProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Profile.class);
            intent.putExtra("ownerId", ownerId);
            startActivity(intent);
        }
    };
    private List<Appointments> ownerAppointments;

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
        appointmentDataService.getAllAppointments(this);
    }

    public void setOwnerAppointmentList(List<Appointments> allAppointmentList) throws ParseException {
        ownerAppointments = new ArrayList<>();
        for (Appointments appointment : allAppointmentList) {
            if (appointment.getOwnerId().equals(ownerId)) {
                Date startDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).parse(appointment.getAppointmentStartDate());
                Date endDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).parse(appointment.getAppointmentEndDate());
                appointment.setAppointmentStartDate(new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(startDate));
                appointment.setAppointmentEndDate(new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(endDate));
                ownerAppointments.add(appointment);
            }
        }
        recyclerView.setAdapter(new OwnerAppointmentListAdapter(ownerAppointments));
    }
}
