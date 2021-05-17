package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.fragments.RegisterFragment;
import com.example.garbagecollector.models.Place;
import com.example.garbagecollector.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity {
    public static Integer currentUserID;
    Retrofit retrofit;
    ClientAPI clientAPI;
    public static List<User> users;
    public static List<Place> places;
    private AppCompatButton btn_login;
    private EditText login_name, login_password;
    private TextView btn_registration, incorrect_login_password;
    FragmentTransaction transaction;
    RegisterFragment registerFragment;
    private static boolean isRegisterFragment;
    private boolean rememberMe = false;
    private ImageView checkbox_remember;

    public static void setIsRegisterFragment(boolean isFirstFragment) {
        StartActivity.isRegisterFragment = isFirstFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.176:8080").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        Call<List<User>> call = clientAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    users = (ArrayList<User>) response.body();


                } else {
                    Log.d("MyTag", response.code()+ " ");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("MyTag", t.getMessage());
            }
        });
        UpdateThread updateThread = new UpdateThread();
        updateThread.start();
        Call<List<Place>> placesCall = clientAPI.getPlaces();
        placesCall.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.code() == 200) {
                    places = (ArrayList<Place>) response.body();


                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {

            }
        });
        registerFragment = new RegisterFragment();
        btn_login = findViewById(R.id.btn_login);
        login_name = findViewById(R.id.login_name);
        login_password = findViewById(R.id.login_password);
        SharedPreferences sp = getSharedPreferences("Key", Activity.MODE_PRIVATE);
        login_name.setText(sp.getString("Name", ""));
        login_password.setText(sp.getString("Password", ""));
        incorrect_login_password = findViewById(R.id.incorrect_login_password);
        checkbox_remember = findViewById(R.id.checkbox_remember);
        checkbox_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rememberMe == true) {
                    checkbox_remember.setImageResource(R.drawable.ic_empty_checkbox);

                } else {
                    checkbox_remember.setImageResource(R.drawable.ic_checkbox);


                }
                rememberMe =!rememberMe;
            }
        });
        btn_registration = findViewById(R.id.btn_registration);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer thisID = 0;
                boolean isHave = false;
                boolean correctPassword = false;
                for (int i = 0; i < StartActivity.users.size(); i++) {
                    if (login_name.getText().toString().equals(StartActivity.users.get(i).getName())) {
                        isHave = true;
                        thisID = i;
                    }
                }
                if (isHave == true && login_password.getText().toString().equals(StartActivity.users.get(thisID).getPassword())) {
                    currentUserID = thisID;
                    incorrect_login_password.setText("");
                    if (rememberMe == true){
                        SharedPreferences sp = getSharedPreferences("Key", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor edt = sp.edit();
                        edt.putString("Name", login_name.getText().toString());
                        edt.putString("Password", login_password.getText().toString());
                        edt.commit();
                    }

                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    incorrect_login_password.setText("Неверный логин или пароль");
                }


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