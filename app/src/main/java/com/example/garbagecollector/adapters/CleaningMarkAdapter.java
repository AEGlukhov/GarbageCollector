package com.example.garbagecollector.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.example.garbagecollector.fragments.cleaning_fragments.CheckPhotoFragment;
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
    CheckPhotoFragment checkPhotoFragment;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_mark_item, parent, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        checkPhotoFragment = new CheckPhotoFragment();

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

        holder.mark_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", shownPlaces.get(position).getId());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fl_main, checkPhotoFragment);
                transaction.commit();
                checkPhotoFragment.setArguments(b);
                List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    activity.getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
            }
        });


        holder.tv_adress_mark.setText(shownPlaces.get(position).getAddress());
        holder.tv_date_mark.setText(shownPlaces.get(position).getDate());
        if(shownPlaces.get(position).getCleanerId()!=-1){
            holder.check_complete.setImageResource(R.drawable.ic_dry_clean);
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
        ImageView cleaning_mark_photo, check_complete;
        LinearLayout mark_item;
        TextView tv_adress_mark, tv_date_mark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cleaning_mark_photo = itemView.findViewById(R.id.cleaning_mark_photo);
            tv_adress_mark = itemView.findViewById(R.id.tv_adress_mark);
            tv_date_mark = itemView.findViewById(R.id.tv_date_mark);
            mark_item = itemView.findViewById(R.id.mark_item);
            check_complete = itemView.findViewById(R.id.check_complete);
        }
    }
}
