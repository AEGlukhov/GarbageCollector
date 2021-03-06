package com.example.garbagecollector.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.adapters.LeaderboardAdapter;


public class LeaderboardFragment extends Fragment {
    private RecyclerView rv_leaderboard;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        rv_leaderboard = view.findViewById(R.id.rv_leaderboard);
        rv_leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_leaderboard.setAdapter(new LeaderboardAdapter());
        

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(false);


        ((TextView)getActivity().findViewById(R.id.title_main)).setText(R.string.leaderboard_title_ru);
        ((ImageView)getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_green);
        ((ImageView)getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_gray);



    }
}