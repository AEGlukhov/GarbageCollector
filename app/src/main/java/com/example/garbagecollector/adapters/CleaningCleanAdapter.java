package com.example.garbagecollector.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;
import com.example.garbagecollector.models.Place;

import java.util.List;

public class CleaningCleanAdapter extends RecyclerView.Adapter<CleaningCleanAdapter.myviewholder> {

    List<Place> places;

    public CleaningCleanAdapter(List<Place> places) {
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_clean_item, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.cleaning_clean_info.setText(places.get(position).getAddress()+"\n"+places.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView cleaning_clean_photo;
        TextView cleaning_clean_info;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            cleaning_clean_photo = itemView.findViewById(R.id.cleaning_clean_photo);
            cleaning_clean_info = itemView.findViewById(R.id.cleaning_clean_info);
        }
    }
}
