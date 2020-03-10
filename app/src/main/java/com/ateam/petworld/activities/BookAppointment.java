package com.ateam.petworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ateam.petworld.R;
import com.ateam.petworld.factory.ClientFactory;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.Sitter;
import com.ateam.petworld.services.AppointmentDataService;
import com.ateam.petworld.services.SitterDataService;
import com.ateam.petworld.utils.DateFunctions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class BookAppointment extends AppCompatActivity implements SlyCalendarDialog.Callback {

    Sitter sitter;
    Owner owner;
    Calendar startDate;
    Calendar endDate;
    String sitterId;
    String ownerId;
    AppointmentDataService appointmentDataService;
    SitterDataService sitterDataService;
    TextView tvPayPerDay;
    TextView tvNoOfDays;
    TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        Intent intent = getIntent();
        sitterId = intent.getStringExtra("SitterId");
        ownerId = intent.getStringExtra("OwnerId");
        init();
    }

    void init() {
        ClientFactory.init(this);
        appointmentDataService = new AppointmentDataService(ClientFactory.appSyncClient());
        sitterDataService = new SitterDataService(ClientFactory.appSyncClient());
        sitter = new Sitter();
        sitter.setId(sitterId);
        sitter = sitterDataService.getSitter(sitter);

        tvNoOfDays = findViewById(R.id.tv_no_of_days);
        tvPayPerDay = findViewById(R.id.tv_per_day_price);
        tvTotalAmount = findViewById(R.id.tv_total_amount);

        findViewById(R.id.btn_select_date).setOnClickListener(view -> new SlyCalendarDialog()
                .setSingle(false)
                .setCallback(BookAppointment.this)
                .setStartDate(new Date())
                .show(getSupportFragmentManager(), "TAG_SLYCALENDAR"));
    }

    @Override
    public void onCancelled() {
        //Nothing
    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null && secondDate != null) {

            TextView tvStartDate = findViewById(R.id.tv_start_date);
            TextView tvEndDate = findViewById(R.id.tv_end_date);
            startDate = firstDate;
            endDate = secondDate;
            long noOfDays = DateFunctions.calculateNoOfDays(startDate, endDate);
            double totalAmount = noOfDays * sitter.getPayPerDay();
            tvPayPerDay.setText(String.format("%s %s", getString(R.string.dollarSign), sitter.getPayPerDay()));
            tvTotalAmount.setText(String.format("%s %s", getString(R.string.dollarSign), totalAmount));
            tvNoOfDays.setText(String.valueOf(noOfDays));
            String startDateString = new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(firstDate.getTime());
            String endDateString = new SimpleDateFormat(getString(R.string.dp_dateFormat), Locale.getDefault()).format(secondDate.getTime());
            tvStartDate.setText(startDateString);
            tvEndDate.setText(endDateString);

        } else {
            Toast.makeText(this,
                    getString(R.string.dp_select_dates_msg),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void bookAppointment(View view) {
        if (startDate != null && endDate != null) {
            long noOfDays = DateFunctions.calculateNoOfDays(startDate, endDate);
            double totalAmount = noOfDays * sitter.getPayPerDay();
            Appointments appointment = new Appointments();
            appointment.setAppointmentStartDate(new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(startDate.getTime()));
            appointment.setAppointmentEndDate(new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(endDate.getTime()));
            appointment.setOwnerId(ownerId);
            appointment.setSitterId(sitterId);
            appointment.setTotalAmount(totalAmount);
            appointmentDataService.createAppointment(appointment);
            Toast.makeText(this,
                    getString(R.string.booking_success),
                    Toast.LENGTH_LONG
            ).show();
            goToDashBoard();
        } else {
            Toast.makeText(this,
                    getString(R.string.dp_select_dates_msg),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    void goToDashBoard() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
