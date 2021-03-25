package com.example.garbagecollector.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.example.garbagecollector.models.Place;
import com.example.garbagecollector.models.User;

import java.util.ArrayList;
import java.util.List;

public class CleaningCleanAdapter extends RecyclerView.Adapter<CleaningCleanAdapter.MyViewHolder> {

    List<Place> shownPlaces;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_clean_item, parent, false);
        
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.cleaning_clean_info.setText(shownPlaces.get(position).getAddress()+"\n"+shownPlaces.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        shownPlaces = new ArrayList<>();
        for (int i = 0; i < StartActivity.places.size(); i++) {
            if (StartActivity.currentUserID != StartActivity.places.get(i).getOwnerId()-1){
                shownPlaces.add(StartActivity.places.get(i));
            }
        }
        return shownPlaces.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView cleaning_clean_photo;
        TextView cleaning_clean_info;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cleaning_clean_photo = itemView.findViewById(R.id.cleaning_clean_photo);
            cleaning_clean_info = itemView.findViewById(R.id.cleaning_clean_info);
        }
    }
}
