package com.example.garbagecollector.fragments.user_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditUserFragment extends Fragment {
    Retrofit retrofit;
    ClientAPI clientAPI;
    private EditText edit_name, edit_password;
    private AppCompatButton btn_save, btn_load_photo;
    private ImageView edit_user_photo, btn_show_password;
    private TextView tv_isHave;
    private boolean showPassword = false;
    FragmentTransaction transaction;
    UserFragment userFragment;
    private String ref = "photo";
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.176:8080").addConverterFactory(GsonConverterFactory.create()).build();
        clientAPI = retrofit.create(ClientAPI.class);
        ref = StartActivity.users.get(StartActivity.currentUserID).getPhoto();
        edit_name = view.findViewById(R.id.edit_name);
        edit_password = view.findViewById(R.id.edit_password);
        edit_password.setTransformationMethod(new PasswordTransformationMethod());
        btn_load_photo = view.findViewById(R.id.btn_load_photo);
        edit_user_photo = view.findViewById(R.id.edit_user_photo);
        tv_isHave = view.findViewById(R.id.isHave2);
        userFragment = new UserFragment();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference riversRef = storageReference.child("images/" + StartActivity.users.get(StartActivity.currentUserID).getPhoto());
        try {
            final File file = File.createTempFile("name", "jpg");
            riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    edit_user_photo.setImageBitmap(bitmap);
                    //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(null, bitmap);
                    //roundedBitmapDrawable.setCircular(true);
                   // user_photo.setImageDrawable(roundedBitmapDrawable);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_load_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });


        btn_show_password = view.findViewById(R.id.btn_show_password2);
        btn_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showPassword == false) {
                    btn_show_password.setImageResource(R.drawable.ic_hide_password);
                    edit_password.setTransformationMethod(null);
                } else {
                    btn_show_password.setImageResource(R.drawable.ic_show_password);
                    edit_password.setTransformationMethod(new PasswordTransformationMethod());

                }
                showPassword = !showPassword;

            }
        });
        edit_name.setText(StartActivity.users.get(StartActivity.currentUserID).getName());
        edit_password.setText(StartActivity.users.get(StartActivity.currentUserID).getPassword());
        btn_save = view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHave = false;
                for (int i = 0; i < StartActivity.users.size(); i++) {
                    if (edit_name.getText().toString().equals(StartActivity.users.get(i).getName()) ) {
                        isHave = true;
                    }
                }
                if (isHave == true&& !edit_name.getText().toString().equals(StartActivity.users.get(StartActivity.currentUserID).getName())) {

                    tv_isHave.setText("Такой логин уже используется");
                } else {
                    //Call<ResponseBody> changeUserCall = clientAPI.changeUser(StartActivity.currentUserID + 1, edit_name.getText().toString(), edit_password.getText().toString(), "Russia", StartActivity.users.get(StartActivity.currentUserID).getMoney(), StartActivity.users.get(StartActivity.currentUserID).getScore(), StartActivity.users.get(StartActivity.currentUserID).getPhoto());
                    Call<ResponseBody> changeUserCall = clientAPI.changeUser(StartActivity.currentUserID + 1, edit_name.getText().toString(), edit_password.getText().toString(), "Russia", StartActivity.users.get(StartActivity.currentUserID).getMoney(), StartActivity.users.get(StartActivity.currentUserID).getScore(), ref);
                    changeUserCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    MainActivity.usableBack = true;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.frameLayout, userFragment);
                    transaction.commit();
                    List<Fragment> fragments = getFragmentManager().getFragments();
                    int size = fragments.size();
                    if (size > 0)
                        getFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                    StartActivity.users.get(StartActivity.currentUserID).setName(edit_name.getText().toString());
                    StartActivity.users.get(StartActivity.currentUserID).setPassword(edit_password.getText().toString());
                    StartActivity.users.get(StartActivity.currentUserID).setPhoto(ref);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.usableBack = false;
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode== -1 && data != null && data.getData()!=null){
            imageUri = data.getData();
            edit_user_photo.setImageURI(imageUri);
            uploadPicture();
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