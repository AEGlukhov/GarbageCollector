package com.example.garbagecollector.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garbagecollector.R;


public class AddPlaceFragment extends Fragment {

   TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        Bundle bundle = getArguments();
        String text = bundle.getString("adress");
        textView = view.findViewById(R.id.tv_test);
        textView.setText(text);
        return view;
    }




}