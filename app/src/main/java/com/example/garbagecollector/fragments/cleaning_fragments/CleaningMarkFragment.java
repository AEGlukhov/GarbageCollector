package com.example.garbagecollector.fragments.cleaning_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.MapMarkActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.adapters.CleaningCleanAdapter;
import com.example.garbagecollector.adapters.CleaningMarkAdapter;
import com.example.garbagecollector.models.Place;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CleaningMarkFragment extends Fragment {
    AppCompatButton btn_add;
    private TextView btn_clean;
    CleaningCleanFragment cleaningCleanFragmentFragment;
    FragmentTransaction transaction;
    RecyclerView rv_cleaning_mark;
    CleaningMarkAdapter cleaningMarkAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaning_mark, container, false);
        cleaningCleanFragmentFragment = new CleaningCleanFragment();
        btn_clean = view.findViewById(R.id.btn_clean);
        btn_add = view.findViewById(R.id.btn_add);
        rv_cleaning_mark = view.findViewById(R.id.rv_cleaning_mark);
        rv_cleaning_mark.setLayoutManager(new LinearLayoutManager(getContext()));
        cleaningMarkAdapter = new CleaningMarkAdapter();
        rv_cleaning_mark.setAdapter(cleaningMarkAdapter);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapMarkActivity.class);
                startActivity(intent);
            }
        });

        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, cleaningCleanFragmentFragment);
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
        MainActivity.setIsFirstFragment(false);
    }
}