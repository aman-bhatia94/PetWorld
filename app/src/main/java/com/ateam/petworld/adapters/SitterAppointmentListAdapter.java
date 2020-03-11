package com.ateam.petworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.models.Appointments;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SitterAppointmentListAdapter extends RecyclerView.Adapter<SitterAppointmentListAdapter.ViewHolder> {

    private List<Appointments> appointmentsList;

    public SitterAppointmentListAdapter(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    @Override
    public void onBindViewHolder(SitterAppointmentListAdapter.ViewHolder holder, int position) {

        Appointments appointment = appointmentsList.get(position);

        /*
        TODO set color background
         */

        holder.userName.setText(String.format("%s %s", appointment.getOwner().getFirstName(), appointment.getOwner().getLastName()));
        holder.appointmentStartDate.setText(appointment.getAppointmentStartDate());
        holder.appointmentEndDate.setText(appointment.getAppointmentEndDate());
        holder.totalAmount.setText(String.format("$ %s", String.valueOf(appointment.getTotalAmount())));
    }

    @NotNull
    @Override
    public SitterAppointmentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row, parent, false);
        return new SitterAppointmentListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView appointmentStartDate;
        TextView appointmentEndDate;
        TextView totalAmount;

        ViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.appointmentUserName);
            appointmentStartDate = itemView.findViewById(R.id.appointmentStartDate);
            appointmentEndDate = itemView.findViewById(R.id.appointmentEndDate);
            totalAmount = itemView.findViewById(R.id.totalAmount);
        }
    }
}
