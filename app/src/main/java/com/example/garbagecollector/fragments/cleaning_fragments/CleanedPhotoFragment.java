package com.example.garbagecollector.fragments.cleaning_fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garbagecollector.ClientAPI;
import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CleanedPhotoFragment extends Fragment {
    Retrofit retrofit;
    ClientAPI clientAPI;
    ImageView imageView;
    AppCompatButton btn;
    int id;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String ref = "photo";
    TextView textView, tv_no_photo;
    boolean photoAdded;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaned_photo, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://188.225.46.21:8084").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        photoAdded = false;
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        id--;
        tv_no_photo = view.findViewById(R.id.tv_no_photo);
        textView = view.findViewById(R.id.tv_reference);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        imageView = view.findViewById(R.id.img_choose_clean);
        btn = view.findViewById(R.id.btn_compelete_clean);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoAdded) {
                    Call<ResponseBody> changePlaceCall = clientAPI.changePlace(id + 1, StartActivity.places.get(id).getOwnerId(),
                            StartActivity.currentUserID + 1,
                            StartActivity.places.get(id).getAddress(),
                            StartActivity.places.get(id).getDate(),
                            StartActivity.places.get(id).getPhoto(),
                            StartActivity.places.get(id).getLat(),
                            StartActivity.places.get(id).getLon(),
                            ref);
                    changePlaceCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    getActivity().onBackPressed();
                } else {
                   tv_no_photo.setTextColor(Color.RED);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePicture();
            }
        });
        MainActivity.setIsFirstFragment(false);
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
            imageView.setImageURI(imageUri);
            uploadPicture();
            photoAdded = true;
            tv_no_photo.setTextColor(Color.WHITE);
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