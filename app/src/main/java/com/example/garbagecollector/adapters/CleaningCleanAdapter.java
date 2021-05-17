package com.example.garbagecollector.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.example.garbagecollector.fragments.cleaning_fragments.CleanedPhotoFragment;
import com.example.garbagecollector.fragments.cleaning_fragments.CleaningCleanFragment;
import com.example.garbagecollector.models.Place;
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
    CleanedPhotoFragment cleanedPhotoFragment;






    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_clean_item, parent, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        cleanedPhotoFragment = new CleanedPhotoFragment();
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



        holder.cleaning_adress_info.setText(shownPlaces.get(position).getAddress());
        holder.cleaning_date_info.setText(shownPlaces.get(position).getDate());

        holder.btn_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("google.navigation:q=" + shownPlaces.get(position).getAddress().toString().trim() + "&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.startActivity(mapIntent);
            }
        });






        holder.add_cleaned_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", shownPlaces.get(position).getId());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fl_main, cleanedPhotoFragment);
                transaction.commit();
                cleanedPhotoFragment.setArguments(b);
                List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    activity.getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        shownPlaces = new ArrayList<>();
        for (int i = 0; i < StartActivity.places.size(); i++) {
            if (StartActivity.currentUserID != StartActivity.places.get(i).getOwnerId() - 1 && StartActivity.places.get(i).getCleanerId()==-1) {
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
        ImageView cleaning_clean_photo, btn_navigation;
        TextView cleaning_adress_info, cleaning_date_info;
        ImageView add_cleaned_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cleaning_clean_photo = itemView.findViewById(R.id.cleaning_clean_photo);
            cleaning_adress_info = itemView.findViewById(R.id.tv_adress_info);
            cleaning_date_info = itemView.findViewById(R.id.tv_date_info);
            btn_navigation = itemView.findViewById(R.id.btn_navigation);
            add_cleaned_photo = itemView.findViewById(R.id.add_cleaned_photo);
        }
    }
}
