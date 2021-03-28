package com.example.garbagecollector.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddPlaceFragment extends Fragment {

    TextView textView;
    AppCompatButton add_marked_place;
    Retrofit retrofit;
    ClientAPI clientAPI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.13:8080").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        Bundle bundle = getArguments();
        String text = bundle.getString("adress");
        textView = view.findViewById(R.id.tv_test);
        add_marked_place = view.findViewById(R.id.add_marked_place);
        textView.setText(text);
        add_marked_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> addPlaceCall = clientAPI.addPlace(StartActivity.currentUserID+1, 0, text, new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()), "photo", 5.1, 5.2, "proof");
                addPlaceCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }


}