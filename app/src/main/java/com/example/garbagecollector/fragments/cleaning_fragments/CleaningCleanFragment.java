package com.example.garbagecollector.fragments.cleaning_fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.example.garbagecollector.adapters.CleaningCleanAdapter;
import com.example.garbagecollector.adapters.LeaderboardAdapter;
import com.example.garbagecollector.models.Place;
import com.example.garbagecollector.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CleaningCleanFragment extends Fragment {
    private TextView btn_mark;
    CleaningMarkFragment cleaningMarkFragment;
    FragmentTransaction transaction;
    RecyclerView rv_cleaning_clean;
    CleaningCleanAdapter cleaningCleanAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaning_clean, container, false);
        rv_cleaning_clean = view.findViewById(R.id.rv_cleaning_clean);
        rv_cleaning_clean.setLayoutManager(new LinearLayoutManager(getContext()));
        cleaningCleanAdapter = new CleaningCleanAdapter();

        rv_cleaning_clean.setAdapter(cleaningCleanAdapter);
        cleaningMarkFragment = new CleaningMarkFragment();
        btn_mark = view.findViewById(R.id.btn_mark);
        btn_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, cleaningMarkFragment);
                transaction.commit();
                List<Fragment> fragments = getFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();


            }
        });
        return view;
    }






    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(true);


        ((TextView) getActivity().findViewById(R.id.title_main)).setText(R.string.cleaning_title_ru);
        ((ImageView) getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_green);
        ((ImageView) getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_gray);


    }


}