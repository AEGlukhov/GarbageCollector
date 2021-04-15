package com.example.garbagecollector.fragments.user_fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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

import java.util.List;


public class UserFragment extends Fragment {
    private AppCompatButton btn_logout, btn_edit_user;
    private TextView user_info;
    private EditText et_password, et_country;
    FragmentTransaction transaction;
    EditUserFragment editUserFragment;
    private ImageView user_photo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        et_password = view.findViewById(R.id.et_password);
        et_country = view.findViewById(R.id.et_country);
        user_info = view.findViewById(R.id.user_info);
        user_photo = view.findViewById(R.id.user_photo);
        user_info.setText(StartActivity.users.get(StartActivity.currentUserID).getName()+
                "\nОчки: " +StartActivity.users.get(StartActivity.currentUserID).getScore().toString() +
                "\nДеньги: " +StartActivity.users.get(StartActivity.currentUserID).getMoney().toString());
        et_password.setText(StartActivity.users.get(StartActivity.currentUserID).getPassword());
        et_password.setTransformationMethod(new PasswordTransformationMethod());
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