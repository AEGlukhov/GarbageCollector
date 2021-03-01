package com.example.garbagecollector.fragments.cleaning_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.MapMarkActivity;
import com.example.garbagecollector.R;

import java.util.List;


public class CleaningMarkFragment extends Fragment {
    AppCompatButton btn_add;
    private TextView btn_clean;
    CleaningCleanFragment cleaningCleanFragmentFragment;
    FragmentTransaction transaction;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaning_mark, container, false);
        cleaningCleanFragmentFragment = new CleaningCleanFragment();
        btn_clean = view.findViewById(R.id.btn_clean);
        btn_add = view.findViewById(R.id.btn_add);

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