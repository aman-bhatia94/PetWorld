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
import com.ateam.petworld.models.Sitter;

import java.util.List;

public class SearchSitterListAdapter extends RecyclerView.Adapter<SearchSitterListAdapter.ViewHolder> {
    private List<Sitter> mSitterList;
    private final String buttonString = "BOOK FOR $%.2f PER DAY";

    public SearchSitterListAdapter(List<Sitter> mSitterList) {
        this.mSitterList = mSitterList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDisplayPicture;
        TextView tvDisplayName;
        TextView tvDisplayLocation;
        TextView tvDistance;
        Button btnBookAppointment;

        ViewHolder(View itemView) {
            super(itemView);
            ivDisplayPicture = itemView.findViewById(R.id.iv_display_picture);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvDisplayLocation = itemView.findViewById(R.id.tv_display_location);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            btnBookAppointment = itemView.findViewById(R.id.btn_book_appointment);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sitter sitter = mSitterList.get(position);
//        Picasso.get().load(imageBasePath + sitter.getPosterPath()).into(holder.ivMovie);
        holder.tvDisplayName.setText(sitter.getFirstName() + " " + sitter.getLastName());
        holder.tvDisplayLocation.setText("Irvine, CA");
        holder.tvDistance.setText(String.valueOf("0.3"));
        holder.btnBookAppointment.setText(String.format(buttonString, sitter.getPayPerDay()));
    }

    @Override
    public int getItemCount() {
        return mSitterList.size();
    }
}
