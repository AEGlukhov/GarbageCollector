package com.example.garbagecollector.fragments.user_fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;

import java.util.List;


public class UserFragment extends Fragment {
    private AppCompatButton btn_logout;
    private TextView user_info;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user_info = view.findViewById(R.id.user_info);
        user_info.setText(StartActivity.users.get(StartActivity.currentUserID).getName()+
                "\nОчки: " +StartActivity.users.get(StartActivity.currentUserID).getScore().toString() +
                "\nДеньги: " +StartActivity.users.get(StartActivity.currentUserID).getMoney().toString());
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.currentUserID = 0;
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(false);
        ((TextView) getActivity().findViewById(R.id.title_main)).setText(R.string.user_title_ru);
        ((ImageView) getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_green);
        ((ImageView) getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_gray);


    }
}