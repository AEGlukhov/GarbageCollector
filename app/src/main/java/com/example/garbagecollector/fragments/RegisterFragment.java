package com.example.garbagecollector.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {
    private TextView tv_isHave;
    private Retrofit retrofit;
    private ClientAPI clientAPI;
    private AppCompatButton btn_register;
    private ImageView btn_show_password;
    private boolean showPassword = false;
    private EditText register_name, register_password, register_country;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://188.225.46.21:8084").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        tv_isHave = view.findViewById(R.id.isHave);
        register_name = view.findViewById(R.id.register_name);
        register_password = view.findViewById(R.id.register_password);
        register_country = view.findViewById(R.id.register_country);
        btn_show_password = view.findViewById(R.id.btn_show_password);
        register_password.setTransformationMethod(new PasswordTransformationMethod());
        btn_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showPassword == false) {
                    btn_show_password.setImageResource(R.drawable.ic_hide_password);
                    register_password.setTransformationMethod(null);
                } else {
                    btn_show_password.setImageResource(R.drawable.ic_show_password);
                    register_password.setTransformationMethod(new PasswordTransformationMethod());

                }
                showPassword = !showPassword;

            }
        });
        btn_register = view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHave = false;
                for (int i = 0; i < StartActivity.users.size(); i++) {
                    if (register_name.getText().toString().equals(StartActivity.users.get(i).getName())) {
                        isHave = true;
                    }
                }
                if (isHave == true) {

                    tv_isHave.setText("Такой логин уже используется");
                } else {

                    Call<ResponseBody> addUserCall = clientAPI.addUser(register_name.getText().toString(), register_password.getText().toString(), "Russia", 0, 0, "0");
                    addUserCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    StartActivity.currentUserID = StartActivity.users.size();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    List<Fragment> fragments = getFragmentManager().getFragments();
                    int size = fragments.size();
                    if (size > 0)
                        getFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                    StartActivity.setIsRegisterFragment(false);
                }
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