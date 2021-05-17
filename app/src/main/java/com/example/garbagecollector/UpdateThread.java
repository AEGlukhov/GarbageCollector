package com.example.garbagecollector;

import com.example.garbagecollector.models.Place;
import com.example.garbagecollector.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateThread extends Thread {
    Retrofit retrofit;
    ClientAPI clientAPI;

    public UpdateThread() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.176:8080").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
    }

    @Override
    public void run() {
        while (true) {
            Call<List<User>> call = clientAPI.getUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.code() == 200) {
                        StartActivity.users = (ArrayList<User>) response.body();


                    } else {
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });

            Call<List<Place>> placesCall = clientAPI.getPlaces();
            placesCall.enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                    if (response.code() == 200) {
                        StartActivity.places = (ArrayList<Place>) response.body();


                    } else {
                    }
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {

                }
            });
        }
    }
}
