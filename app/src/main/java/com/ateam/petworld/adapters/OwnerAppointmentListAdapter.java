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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OwnerAppointmentListAdapter extends RecyclerView.Adapter<OwnerAppointmentListAdapter.ViewHolder> {

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

        holder.userName.setText(appointment.getSitter().getFirstName()+" "+ appointment.getSitter().getLastName());
        holder.appointmentStartDate.setText(appointment.getAppointmentStartDate());
        holder.appointmentEndDate.setText(appointment.getAppointmentEndDate());
        holder.totalAmount.setText(String.valueOf(appointment.getTotalAmount()));
        //holder.bookButton.setOnClickListener(bookListener);
//        holder.appointmentTime.setText(appointment.getAppointMentTime());

//        if (appointment.isUpcomingAppointment()) {
//            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(), R.color.upcomingAppointment));
//        } else {
//            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(), R.color.pastAppointment));
//        }

    }

    View.OnClickListener bookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    } ;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView appointmentStartDate;
        TextView appointmentEndDate;
        TextView totalAmount;
        //Button bookButton;
        //Button rescheduleButton;
        //View horizontalDivider;

        ViewHolder(View itemView) {
            super(itemView);

            /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new MovieListAdapter.ViewHolder(view);*/

            userName = itemView.findViewById(R.id.appointmentUserName);
            appointmentStartDate = itemView.findViewById(R.id.appointmentStartDate);
            appointmentEndDate = itemView.findViewById(R.id.appointmentEndDate);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            //bookButton = itemView.findViewById(R.id.bookAppointmentButton);
            //horizontalDivider = itemView.findViewById(R.id.divider);

        }

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }


}
