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
import com.example.garbagecollector.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    List<User> topUsers;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
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
        if (position == 0){
            holder.leaderboard_photo.setBackgroundResource(R.drawable.gold_frame);
        }
        if (position == 1){
            holder.leaderboard_photo.setBackgroundResource(R.drawable.silver_frame);
        }
        if (position == 2){
            holder.leaderboard_photo.setBackgroundResource(R.drawable.bronze_frame);
        }
        holder.leaderboard_name_score.setText(topUsers.get(position).getName() + "\n" + topUsers.get(position).getScore().toString());
        holder.leaderboard_position.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView leaderboard_photo, leaderboard_country;
        TextView leaderboard_name_score, leaderboard_position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leaderboard_photo = itemView.findViewById(R.id.leaderboard_photo);
            leaderboard_country = itemView.findViewById(R.id.leaderboard_country);
            leaderboard_name_score = itemView.findViewById(R.id.leaderboard_name_score);
            leaderboard_position = itemView.findViewById(R.id.leaderboard_position);
        }
    }

}
