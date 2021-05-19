package com.example.garbagecollector.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddPlaceFragment extends Fragment {

    private String ref = "photo";
    private TextView textView, tv_need_photo;
    private AppCompatButton add_marked_place;
    private Retrofit retrofit;
    private ClientAPI clientAPI;
    private Uri imageUri;
    private ImageView selectImage;
    private boolean photoAdded;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://188.225.46.21:8084").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        Bundle bundle = getArguments();
        String text = bundle.getString("adress");
        textView = view.findViewById(R.id.tv_test);
        tv_need_photo = view.findViewById(R.id.tv_need_photo);
        tv_need_photo.setText("");
        photoAdded = false;
        selectImage = view.findViewById(R.id.selectImage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });


        add_marked_place = view.findViewById(R.id.add_marked_place);
        textView.setText(text);
        add_marked_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoAdded) {
                    Call<ResponseBody> addPlaceCall = clientAPI.addPlace(StartActivity.currentUserID + 1, -1, text, new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()), ref, 5.1, 5.2, "proof");
                    addPlaceCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    getActivity().onBackPressed();
                } else {
                    tv_need_photo.setText("Необходимо выбрать фото");
                }
            }
        });
        return view;
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null && data.getData() != null) {
            imageUri = data.getData();
            selectImage.setImageURI(imageUri);
            uploadPicture();
            photoAdded = true;
            tv_need_photo.setText("");
        }
    }

    private void uploadPicture() {

        //final String randomKey = UUID.randomUUID().toString();
        ref = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + ref);
        // StorageReference riversRef = storageReference.child("images/qwerty");


        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }


}