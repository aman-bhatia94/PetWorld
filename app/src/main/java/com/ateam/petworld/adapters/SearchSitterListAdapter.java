package com.ateam.petworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.activities.SearchSitters;
import com.ateam.petworld.models.Sitter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchSitterListAdapter extends RecyclerView.Adapter<SearchSitterListAdapter.ViewHolder> {
    private List<Sitter> mSitterList;
    private final String distanceString = "%.2f Miles Away";
    private final String buttonString = "BOOK FOR $%.2f PER DAY";
    private Context mContext;

    public SearchSitterListAdapter(List<Sitter> mSitterList, Context mContext) {
        this.mSitterList = mSitterList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sitter sitter = mSitterList.get(position);
        Picasso.get().load("https://static-cdn.jtvnw.net/jtv_user_pictures/dogdog-profile_image-5550ade194780dfc-300x300.jpeg").into(holder.ivDisplayPicture);
        holder.tvDisplayName.setText(sitter.getFirstName() + " " + sitter.getLastName());
        holder.tvDisplayLocation.setText(sitter.getLocation().getDisplayName());
        holder.tvDistance.setText(String.format(distanceString, sitter.getDistanceFromOwner()));
        holder.btnBookAppointment.setText(String.format(buttonString, sitter.getPayPerDay()));
        holder.itemView.setId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            btnBookAppointment.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            Sitter sitter = mSitterList.get(id);
            if (mContext instanceof SearchSitters) {
                ((SearchSitters) mContext).bookAppointment(sitter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSitterList.size();
    }
}
