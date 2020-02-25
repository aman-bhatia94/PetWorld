package com.ateam.petworld.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.models.Appointments;
import com.ateam.petworld.models.Owner;
import com.ateam.petworld.models.User;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ViewHolder> {

    private List<Appointments> appointmentsList;

    public AppointmentListAdapter(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView appointmentDate;
        TextView appointmentTime;
        Button cancelButton;
        Button rescheduleButton;

        ViewHolder(View itemView){
            super(itemView);

            /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new MovieListAdapter.ViewHolder(view);*/

            userName = (TextView)itemView.findViewById(R.id.appointmentUserName);
            appointmentDate = (TextView)itemView.findViewById(R.id.appointmentDate);
            appointmentTime = (TextView)itemView.findViewById(R.id.appointmentTime);
            cancelButton = (Button)itemView.findViewById(R.id.cancelAppointmentButton);
            rescheduleButton = (Button)itemView.findViewById(R.id.rescheduleAppointmentButton);

        }

    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new AppointmentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Appointments appointment = appointmentsList.get(position);

        /*
        TODO set user depending on role
         */

        holder.userName.setText(appointment.getSitter().getFirstName());
        holder.appointmentDate.setText(appointment.getAppointmentDate());
        holder.appointmentTime.setText(appointment.getAppointMentTime());

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }



}
