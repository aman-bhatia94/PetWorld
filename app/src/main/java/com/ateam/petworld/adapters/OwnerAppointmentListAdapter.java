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

public class OwnerAppointmentListAdapter extends RecyclerView.Adapter<OwnerAppointmentListAdapter.ViewHolder> {

    private List<Appointments> appointmentsList;
    public OwnerAppointmentListAdapter(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView appointmentDate;
        TextView appointmentTime;
        Button cancelButton;
        Button rescheduleButton;
        View horizontalDivider;

        ViewHolder(View itemView){
            super(itemView);

            /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new MovieListAdapter.ViewHolder(view);*/

            userName = (TextView)itemView.findViewById(R.id.appointmentUserName);
            appointmentDate = (TextView)itemView.findViewById(R.id.appointmentDate);
            appointmentTime = (TextView)itemView.findViewById(R.id.appointmentTime);
            cancelButton = (Button)itemView.findViewById(R.id.cancelAppointmentButton);
            rescheduleButton = (Button)itemView.findViewById(R.id.rescheduleAppointmentButton);
            horizontalDivider = (View)itemView.findViewById(R.id.divider);

        }

    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new OwnerAppointmentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Appointments appointment = appointmentsList.get(position);

        /*
        TODO set color background
         */

        holder.userName.setText(appointment.getSitter().getFirstName());
        holder.appointmentDate.setText(appointment.getAppointmentDate());
        holder.appointmentTime.setText(appointment.getAppointMentTime());

        if(appointment.isUpcomingAppointment()){
            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(),R.color.upcomingAppointment));
        }
        else{
            holder.horizontalDivider.setBackgroundColor(ContextCompat.getColor(MainApplication.getAppContext(),R.color.pastAppointment));
        }

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }



}
