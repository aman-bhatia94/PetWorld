package com.ateam.petworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.MainApplication;
import com.ateam.petworld.R;
import com.ateam.petworld.models.Appointments;

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

        holder.userName.setText(appointment.getOwner().getFirstName()+" "+ appointment.getOwner().getLastName());
        holder.appointmentStartDate.setText(appointment.getAppointmentStartDate());
        holder.appointmentEndDate.setText(appointment.getAppointmentEndDate());
        holder.totalAmount.setText(String.valueOf(appointment.getTotalAmount()));
//        holder.appointmentTime.setText(appointment.getAppointMentTime());

        /*if (appointment.isUpcomingAppointment()) {
            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(), R.color.upcomingAppointment));
        } else {
            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(), R.color.pastAppointment));
        }*/

    }

    @Override
    public SitterAppointmentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row, parent, false);
        return new SitterAppointmentListAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView appointmentStartDate;
        TextView appointmentEndDate;
        TextView totalAmount;

        ViewHolder(View itemView) {
            super(itemView);

            /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new MovieListAdapter.ViewHolder(view);*/

            userName = itemView.findViewById(R.id.appointmentUserName);
            appointmentStartDate = itemView.findViewById(R.id.appointmentStartDate);
            appointmentEndDate = itemView.findViewById(R.id.appointmentEndDate);
            totalAmount = itemView.findViewById(R.id.totalAmount);

        }

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }
}
