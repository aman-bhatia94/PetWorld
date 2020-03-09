package com.ateam.petworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.activities.SearchLocation;
import com.ateam.petworld.models.Location;

import java.util.List;

public class SearchLocationListAdapter extends RecyclerView.Adapter<SearchLocationListAdapter.ViewHolder> {
    private List<Location> mLocationList;
    private Context mContext;

    public SearchLocationListAdapter(List<Location> mLocationList, Context mContext) {
        this.mLocationList = mLocationList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_location_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = mLocationList.get(position);
        holder.tvDisplayName.setText(location.getDisplayName());
        holder.tvDisplayAddress.setText(location.getDisplayAddress());
    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDisplayName;
        TextView tvDisplayAddress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisplayAddress = itemView.findViewById(R.id.tv_display_address);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            Location location = mLocationList.get(id);
            if (mContext instanceof SearchLocation) {
                ((SearchLocation) mContext).setLocation(location);
            }
        }
    }
}
