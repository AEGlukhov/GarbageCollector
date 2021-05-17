package com.example.garbagecollector.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
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

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    List<User> topUsers;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        topUsers = new ArrayList<>();
        for (User user : StartActivity.users) {
            topUsers.add(user);
        }
        Collections.sort(topUsers, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user2.getScore() - user1.getScore();
            }
        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StorageReference riversRef = storageReference.child("images/" + topUsers.get(position).getPhoto());
        try {
            final File file = File.createTempFile("name", "jpg");
            riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.leaderboard_photo.setImageBitmap(bitmap);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(null, bitmap);
                    roundedBitmapDrawable.setCircular(true);
                    holder.leaderboard_photo.setImageDrawable(roundedBitmapDrawable);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }



        if (position == 0){
            holder.leaderboard_position.setTextColor(Color.parseColor("#FFCC00"));
            holder.leaderboard_position.setTextSize(18);
        }
        if (position == 1){
            holder.leaderboard_position.setTextColor(Color.parseColor("#008B8B"));
            holder.leaderboard_position.setTextSize(18);
        }
        if (position == 2){
            holder.leaderboard_position.setTextColor(Color.parseColor("#CC6600"));
            holder.leaderboard_position.setTextSize(18);
        }
        holder.leaderboard_name.setText(topUsers.get(position).getName());
        holder.leaderboard_score.setText("Очки: " + topUsers.get(position).getScore().toString());
        holder.leaderboard_position.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        if (StartActivity.users.size() <10){
            return StartActivity.users.size();
        } else {
            return 10;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView leaderboard_photo, leaderboard_country;
        TextView leaderboard_name, leaderboard_score, leaderboard_position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leaderboard_photo = itemView.findViewById(R.id.leaderboard_photo);
            leaderboard_country = itemView.findViewById(R.id.leaderboard_country);
            leaderboard_name = itemView.findViewById(R.id.leaderboard_name);
            leaderboard_score = itemView.findViewById(R.id.leaderboard_score);
            leaderboard_position = itemView.findViewById(R.id.leaderboard_position);
        }
    }

}
