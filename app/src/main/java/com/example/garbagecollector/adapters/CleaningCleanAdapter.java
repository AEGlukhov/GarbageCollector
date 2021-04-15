package com.example.garbagecollector.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CleaningCleanAdapter extends RecyclerView.Adapter<CleaningCleanAdapter.MyViewHolder> {

    List<Place> shownPlaces;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_clean_item, parent, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        StorageReference riversRef = storageReference.child("images/" + shownPlaces.get(position).getPhoto());
        try {
            final File file = File.createTempFile("name", "jpg");
            riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.cleaning_clean_photo.setImageBitmap(bitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        holder.cleaning_clean_info.setText(shownPlaces.get(position).getAddress() + "\n" + shownPlaces.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        shownPlaces = new ArrayList<>();
        for (int i = 0; i < StartActivity.places.size(); i++) {
            if (StartActivity.currentUserID != StartActivity.places.get(i).getOwnerId() - 1) {
                shownPlaces.add(StartActivity.places.get(i));
            }
        }
        Collections.sort(shownPlaces, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                String[] date1 = o1.getDate().split("\\.");
                String[] date2 = o2.getDate().split("\\.");
                if (Integer.parseInt(date1[2]) > Integer.parseInt(date2[2])) {
                    return -1;
                } else if (Integer.parseInt(date1[2]) == Integer.parseInt(date2[2])) {
                    if (Integer.parseInt(date1[1]) > Integer.parseInt(date2[1])) {
                        return -1;
                    } else if (Integer.parseInt(date1[1]) == Integer.parseInt(date2[1])) {
                        if (Integer.parseInt(date1[0]) > Integer.parseInt(date2[0])) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else {
                        return 1;
                    }

                } else {
                    return 1;
                }
            }
        });
        return shownPlaces.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cleaning_clean_photo;
        TextView cleaning_clean_info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cleaning_clean_photo = itemView.findViewById(R.id.cleaning_clean_photo);
            cleaning_clean_info = itemView.findViewById(R.id.cleaning_clean_info);
        }
    }
}
