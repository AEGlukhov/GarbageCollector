package com.example.garbagecollector.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;

import java.util.List;


public class RegisterFragment extends Fragment {
    private AppCompatButton btn_register;
    private ImageView btn_show_password;
    private boolean showPassword = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        btn_show_password = view.findViewById(R.id.btn_show_password);
        btn_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showPassword == false){
                    btn_show_password.setImageResource(R.drawable.ic_hide_password);
                } else {
                    btn_show_password.setImageResource(R.drawable.ic_show_password);
                }
                showPassword = !showPassword;

            }
        });
        btn_register = view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                List<Fragment> fragments = getFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                StartActivity.setIsRegisterFragment(false);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        StartActivity.setIsRegisterFragment(true);
    }
}