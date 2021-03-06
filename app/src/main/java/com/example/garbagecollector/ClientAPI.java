package com.example.garbagecollector;

import com.example.garbagecollector.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClientAPI {

    @GET("/getUsers")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("/addUser")
    Call<ResponseBody> addUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("country") String country,
            @Field("money") Integer money,
            @Field("score") Integer score,
            @Field("photo") Integer photo
    );
}
