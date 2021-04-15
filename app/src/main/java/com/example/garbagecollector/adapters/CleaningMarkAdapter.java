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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CleaningMarkAdapter extends RecyclerView.Adapter<CleaningMarkAdapter.MyViewHolder> {
    List<Place> shownPlaces;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_mark_item, parent, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        return new CleaningMarkAdapter.MyViewHolder(view);
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
                    holder.cleaning_mark_photo.setImageBitmap(bitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (shownPlaces.get(position).getCleanerId() == 0) {
            holder.cleaning_mark_info.setText(shownPlaces.get(position).getAddress() + "\n" + shownPlaces.get(position).getDate() + "\nСтатус: Не убрано");
        } else {
            holder.cleaning_mark_info.setText(shownPlaces.get(position).getAddress() + "\n" + shownPlaces.get(position).getDate() + "\nСтатус: Убрано");
        }
    }

    @Override
    public int getItemCount() {
        shownPlaces = new ArrayList<>();
        for (int i = 0; i < StartActivity.places.size(); i++) {
            if (StartActivity.currentUserID == StartActivity.places.get(i).getOwnerId() - 1) {
                shownPlaces.add(StartActivity.places.get(i));
            }
        }
        return shownPlaces.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cleaning_mark_photo;
        TextView cleaning_mark_info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cleaning_mark_photo = itemView.findViewById(R.id.cleaning_mark_photo);
            cleaning_mark_info = itemView.findViewById(R.id.cleaning_mark_info);
        }
    }
}
