package com.example.garbagecollector.fragments.cleaning_fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;


public class CleaningCleanFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaning_clean, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(true);



        ((TextView)getActivity().findViewById(R.id.title_main)).setText(R.string.cleaning_title_ru);
        ((ImageView)getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_green);
        ((ImageView)getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_gray);


    }


}