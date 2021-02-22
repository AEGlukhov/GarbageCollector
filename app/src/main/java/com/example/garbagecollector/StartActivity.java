package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.garbagecollector.fragments.RegisterFragment;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    private AppCompatButton btn_login;
    private TextView btn_registration;
    FragmentTransaction transaction;
    RegisterFragment registerFragment;
    private static boolean isRegisterFragment;

    public static void setIsRegisterFragment(boolean isFirstFragment) {
        StartActivity.isRegisterFragment = isFirstFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        registerFragment = new RegisterFragment();
        btn_login = findViewById(R.id.btn_login);
        btn_registration = findViewById(R.id.btn_registration);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fl_start, registerFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isRegisterFragment == true) {
            isRegisterFragment = false;
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            int size = fragments.size();
            if (size > 0)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();

        } else {
            finish();
        }
    }
}