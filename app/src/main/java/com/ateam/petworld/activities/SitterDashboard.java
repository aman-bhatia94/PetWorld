package com.ateam.petworld.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.amplify.generated.graphql.OnCreateAppointmentSubscription;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.ateam.petworld.R;
import com.ateam.petworld.adapters.SitterAppointmentListAdapter;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.services.AppointmentDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

public class SitterDashboard extends AppCompatActivity {

    Context context;
    AppointmentDataService appointmentDataService;
    Intent intent;

    Button updateProfileButton;
    RecyclerView recyclerView;
    SitterAppointmentListAdapter sitterAppointmentListAdapter;
    private String sitterEmailId;
    private String sitterId;
    View.OnClickListener updateProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Profile.class);
            intent.putExtra("sitterId", sitterId);
            startActivity(intent);
        }
    };
    private AppSyncSubscriptionCall subscriptionWatcher;
    private AppSyncSubscriptionCall.Callback subCallback = new AppSyncSubscriptionCall.Callback() {
        @Override
        public void onResponse(@Nonnull Response response) {
            Log.i("Response", "Received subscription notification: " + response.data().toString());
            OnCreateAppointmentSubscription.OnCreateAppointment data = ((OnCreateAppointmentSubscription.Data) response.data()).onCreateAppointment();
            Appointments appointment = new Appointments();
            Sitter sitter = new Sitter();
            if (data.sitter() != null) {
                sitter.setId(data.sitter().id());
                sitter.setPassword(data.sitter().password());
                sitter.setEmailId(data.sitter().emailId());
                sitter.setPayPerDay(data.sitter().payPerDay());
                sitter.setFirstName(data.sitter().firstName());
                sitter.setLastName(data.sitter().lastName());
                sitter.setPhoneNumber(data.sitter().phoneNumber());
                appointment.setSitterId(data.sitter().id());
            }

            Owner owner = new Owner();
            if (data.owner() != null) {
                owner.setId(data.owner().id());
                owner.setPassword(data.owner().password());
                owner.setEmailId(data.owner().emailId());
                owner.setFirstName(data.owner().firstName());
                owner.setLastName(data.owner().lastName());
                owner.setPhoneNumber(data.owner().phoneNumber());
                appointment.setOwnerId(data.owner().id());
            }

            appointment.setId(data.id());
            appointment.setAppointmentStartDate(data.startDate());
            appointment.setAppointmentEndDate(data.endDate());
            appointment.setTotalAmount(data.totalAmount());
            appointment.setOwner(owner);
            appointment.setSitter(sitter);

            if (appointment.getSitterId().equals(sitterId)) {
                runOnUiThread(() -> {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal_background)
                            .setContentTitle("PetWorld Notification")
                            .setContentText("New Appointment Added!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(123, builder.build());
//                    recreate();
                });
            }
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }

        @Override
        public void onCompleted() {
            Log.i("Completed", "Subscription completed");
        }
    };
    private List<Appointments> sitterAppointments;

    private void subscribe() {
        OnCreateAppointmentSubscription subscription = OnCreateAppointmentSubscription.builder().build();
        subscriptionWatcher = ClientFactory.appSyncClient().subscribe(subscription);
        subscriptionWatcher.execute(subCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_dashboard);
        ClientFactory.init(this);
        context = this;
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        recyclerView = findViewById(R.id.rv_sitter_appointment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateProfileButton = findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(updateProfileListener);
        intent = getIntent();
        sitterEmailId = intent.getStringExtra("emailId");
        sitterId = intent.getStringExtra("sitterId");
        appointmentDataService.getAllAppointments(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    public void setSitterAppointmentList(List<Appointments> allAppointmentList) throws ParseException {
        sitterAppointments = new ArrayList<>();
        for (Appointments appointment : allAppointmentList) {
            if (appointment.getSitterId().equals(sitterId)) {
                Date startDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).parse(appointment.getAppointmentStartDate());
                Date endDate = new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).parse(appointment.getAppointmentEndDate());
                appointment.setAppointmentStartDate(new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(startDate));
                appointment.setAppointmentEndDate(new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(endDate));
                sitterAppointments.add(appointment);
            }
        }
        sitterAppointmentListAdapter = new SitterAppointmentListAdapter(sitterAppointments);
        recyclerView.setAdapter(new SitterAppointmentListAdapter(sitterAppointments));
    }
}
