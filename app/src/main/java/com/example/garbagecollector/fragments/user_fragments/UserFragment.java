package com.example.garbagecollector.fragments.user_fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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

import com.example.garbagecollector.MainActivity;
import com.example.garbagecollector.R;
import com.example.garbagecollector.StartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class UserFragment extends Fragment {
    private AppCompatButton btn_logout;
    private TextView user_info, btn_edit_user, user_name;
    private EditText et_password;
    FragmentTransaction transaction;
    EditUserFragment editUserFragment;
    private ImageView user_photo;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        et_password = view.findViewById(R.id.et_password);

        user_info = view.findViewById(R.id.user_info);
        user_name = view.findViewById(R.id.user_name);
        user_name.setText(StartActivity.users.get(StartActivity.currentUserID).getName());
        user_photo = view.findViewById(R.id.user_photo);
        user_info.setText("Деньги: " + StartActivity.users.get(StartActivity.currentUserID).getMoney().toString()
                + "\nОчки: " + StartActivity.users.get(StartActivity.currentUserID).getScore().toString());
        et_password.setText(StartActivity.users.get(StartActivity.currentUserID).getPassword());
        et_password.setTransformationMethod(new PasswordTransformationMethod());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference riversRef = storageReference.child("images/" + StartActivity.users.get(StartActivity.currentUserID).getPhoto());
        try {
            final File file = File.createTempFile("name", "jpg");
            riversRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    user_photo.setImageBitmap(bitmap);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(null, bitmap);
                    roundedBitmapDrawable.setCircular(true);
                    user_photo.setImageDrawable(roundedBitmapDrawable);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_logout = view.findViewById(R.id.btn_logout);
        editUserFragment = new EditUserFragment();
        btn_edit_user = view.findViewById(R.id.btn_edit_user);
        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.fl_main, editUserFragment);
                transaction.commit();
                List<Fragment> fragments = getFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartActivity.currentUserID = 0;
                getActivity().finish();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.setIsFirstFragment(false);
        ((TextView) getActivity().findViewById(R.id.title_main)).setText(R.string.user_title_ru);
        ((ImageView) getActivity().findViewById(R.id.btn_user)).setImageResource(R.drawable.ic_user_green);
        ((ImageView) getActivity().findViewById(R.id.btn_cleaning)).setImageResource(R.drawable.ic_cleaning_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_shop)).setImageResource(R.drawable.ic_shop_gray);
        ((ImageView) getActivity().findViewById(R.id.btn_leaderboard)).setImageResource(R.drawable.ic_leaderboard_gray);


    }

}