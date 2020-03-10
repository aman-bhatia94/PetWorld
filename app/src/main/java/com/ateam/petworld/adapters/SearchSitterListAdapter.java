package com.ateam.petworld.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ateam.petworld.R;
import com.ateam.petworld.activities.SearchSitters;
import com.ateam.petworld.models.Sitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SearchSitterListAdapter extends RecyclerView.Adapter<SearchSitterListAdapter.ViewHolder> {
    private List<Sitter> mSitterList;
    private Context mContext;

    private Comparator<Sitter> compareById;
    private Comparator<Sitter> compareByName;
    private Comparator<Sitter> compareByDistance;
    private Comparator<Sitter> compareByPrice;

    public SearchSitterListAdapter(List<Sitter> mSitterList, Context mContext) {
        this.mSitterList = mSitterList;
        this.mContext = mContext;
        compareById = (Sitter s1, Sitter s2) -> s1.getId().compareTo(s2.getId());
        compareByName = (Sitter s1, Sitter s2) -> s1.getFirstName().compareTo(s2.getFirstName());
        compareByDistance = (Sitter s1, Sitter s2) -> (int) (s1.getDistanceFromOwner() - s2.getDistanceFromOwner());
        compareByPrice = (Sitter s1, Sitter s2) -> (int) (s1.getPayPerDay() - s2.getPayPerDay());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sitter sitter = mSitterList.get(position);
//        Picasso.get().load("https://static-cdn.jtvnw.net/jtv_user_pictures/dogdog-profile_image-5550ade194780dfc-300x300.jpeg").into(holder.ivDisplayPicture);
        holder.tvDisplayName.setText(String.format("%s %s", sitter.getFirstName(), sitter.getLastName()));
        holder.tvDisplayLocation.setText(sitter.getLocation().getDisplayName());
        String distanceString = "%.2f Miles Away";
        holder.tvDistance.setText(String.format(Locale.getDefault(), distanceString, sitter.getDistanceFromOwner()));
        String buttonString = "BOOK FOR $%.2f PER DAY";
        holder.btnBookAppointment.setText(String.format(Locale.getDefault(), buttonString, sitter.getPayPerDay()));
        holder.itemView.setId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row, parent, false);
        return new ViewHolder(view);
    }

    public void updateDataSet(List<Sitter> filteredSearchList) {
        mSitterList.clear();
        mSitterList = filteredSearchList;
    }

    public void sortBy(int idx) {
        List<Sitter> tempList = new ArrayList<>(mSitterList);
        switch (idx) {
            case 0:
                Collections.sort(tempList, compareById);
                break;
            case 1:
                Collections.sort(tempList, compareByName);
                break;
            case 2:
                Collections.sort(tempList, compareByPrice);
                break;
            case 3:
                Collections.sort(tempList, compareByDistance);
                break;
        }
        updateDataSet(tempList);
    }

    @Override
    public int getItemCount() {
        return mSitterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDisplayName;
        TextView tvDisplayLocation;
        TextView tvDistance;
        Button btnBookAppointment;

        ViewHolder(View itemView) {
            super(itemView);
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
}
