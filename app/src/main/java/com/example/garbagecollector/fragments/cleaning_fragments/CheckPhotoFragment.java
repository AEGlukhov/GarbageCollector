package com.example.garbagecollector.fragments.cleaning_fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckPhotoFragment extends Fragment {
    Retrofit retrofit;
    ClientAPI clientAPI;
    ImageView imgCheck;
    int id;
    TextView tv_check_info;
    AppCompatButton btn_accept_clean, btn_reject_clean;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_photo, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.176:8080").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);

        imgCheck = view.findViewById(R.id.img_check);
        tv_check_info = view.findViewById(R.id.tv_check_info);
        btn_accept_clean = view.findViewById(R.id.btn_accept_clean);
        btn_reject_clean = view.findViewById(R.id.btn_reject_clean);

        btn_accept_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StartActivity.places.get(id).getProof().equals("proof")) {
                    tv_check_info.setTextColor(Color.RED);
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            tv_check_info.setTextColor(Color.BLACK);
                        }
                    };
                    timer.start();
                } else {

                    int tmpId = StartActivity.places.get(id).getCleanerId();
                    Call<ResponseBody> addSMUserCall = clientAPI.changeUser(tmpId,
                            StartActivity.users.get(tmpId - 1).getName(),
                            StartActivity.users.get(tmpId - 1).getPassword(),
                            "Russia",
                            StartActivity.users.get(tmpId - 1).getMoney() + 50,
                            StartActivity.users.get(tmpId - 1).getScore() + 1,
                            StartActivity.users.get(tmpId - 1).getPhoto());
                    addSMUserCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });


                    Call<ResponseBody> acceptPhotoCall = clientAPI.changePlace(id + 1, -228,
                            -228,
                            StartActivity.places.get(id).getAddress(),
                            StartActivity.places.get(id).getDate(),
                            StartActivity.places.get(id).getPhoto(),
                            StartActivity.places.get(id).getLat(),
                            StartActivity.places.get(id).getLon(),
                            StartActivity.places.get(id).getProof());
                    acceptPhotoCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    getActivity().onBackPressed();
                }

            }
        });

        btn_reject_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StartActivity.places.get(id).getProof().equals("proof")) {
                    tv_check_info.setTextColor(Color.RED);
                    CountDownTimer timer = new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            tv_check_info.setTextColor(Color.BLACK);
                        }
                    };
                    timer.start();
                } else {
                    Call<ResponseBody> rejectPhotoCall = clientAPI.changePlace(id + 1, StartActivity.places.get(id).getOwnerId(), -1,
                            StartActivity.places.get(id).getAddress(),
                            StartActivity.places.get(id).getDate(),
                            StartActivity.places.get(id).getPhoto(),
                            StartActivity.places.get(id).getLat(),
                            StartActivity.places.get(id).getLon(),
                            "proof");
                    rejectPhotoCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    getActivity().onBackPressed();
                }
            }
        });

        MainActivity.setIsFirstFragment(false);

        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        id--;

        if (!StartActivity.places.get(id).getProof().equals("proof")) {
            tv_check_info.setText("");
        }
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference riversRef = storageReference.child("images/" + StartActivity.places.get(id).getProof());
        try {
            final File file = File.createTempFile("name", "jpg");
            riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imgCheck.setImageBitmap(bitmap);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
}