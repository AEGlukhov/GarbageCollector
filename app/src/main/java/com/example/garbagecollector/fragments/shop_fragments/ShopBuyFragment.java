package com.example.garbagecollector.fragments.shop_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.example.garbagecollector.adapters.LeaderboardAdapter;
import com.example.garbagecollector.adapters.ShopAdapter;


public class ShopBuyFragment extends Fragment {
    private TextView balance;
    private RecyclerView rv_partners;
    private ImageView partners_info;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_buy, container, false);
        balance = view.findViewById(R.id.balance);
        balance.setText(StartActivity.users.get(StartActivity.currentUserID).getMoney().toString());
        rv_partners = view.findViewById(R.id.partners);
        rv_partners.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_partners.setAdapter(new ShopAdapter());
        partners_info = view.findViewById(R.id.partners_info);
        partners_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartnersInfo partnersInfo = new PartnersInfo();
                partnersInfo.show(getFragmentManager(), "tag");
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(false);
        ((TextView)getActivity().findViewById(R.id.title_main)).setText(R.string.shop_title_ru);
        ((ImageView)getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_green);
        ((ImageView)getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_gray);
        ((ImageView)getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_gray);


    }


}