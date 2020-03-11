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

public class OwnerAppointmentListAdapter extends RecyclerView.Adapter<OwnerAppointmentListAdapter.ViewHolder> {

    View.OnClickListener bookListener = v -> {

    };
    private List<Appointments> appointmentsList;

    public OwnerAppointmentListAdapter(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row, parent, false);
        return new OwnerAppointmentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Appointments appointment = appointmentsList.get(position);

        /*
        TODO set color background
         */

        holder.userName.setText(String.format("%s %s", appointment.getSitter().getFirstName(), appointment.getSitter().getLastName()));
        holder.appointmentStartDate.setText(appointment.getAppointmentStartDate());
        holder.appointmentEndDate.setText(appointment.getAppointmentEndDate());
        holder.totalAmount.setText(String.format("$ %s", String.valueOf(appointment.getTotalAmount())));
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
