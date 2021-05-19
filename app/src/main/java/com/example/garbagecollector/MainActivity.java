package com.example.garbagecollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbagecollector.fragments.RegisterFragment;
import com.example.garbagecollector.fragments.cleaning_fragments.CleaningCleanFragment;
import com.example.garbagecollector.fragments.LeaderboardFragment;
import com.example.garbagecollector.fragments.shop_fragments.ShopBuyFragment;
import com.example.garbagecollector.fragments.user_fragments.UserFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static boolean usableBack;
    private FrameLayout frameLayout;
    private CleaningCleanFragment cleaningCleanFragment;
    private LeaderboardFragment leaderboardFragment;
    private ShopBuyFragment shopBuyFragment;
    private UserFragment userFragment;
    private FragmentTransaction transaction;
    private ImageView btn_cleaning, btn_leaderboard, btn_shop, btn_user;
    private TextView title_main;
    private static boolean isFirstFragment;

    public static void setIsFirstFragment(boolean isFirstFragment) {
        MainActivity.isFirstFragment = isFirstFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);
        title_main = findViewById(R.id.title_main);
        cleaningCleanFragment = new CleaningCleanFragment();
        usableBack = true;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, cleaningCleanFragment);
        transaction.commit();
        leaderboardFragment = new LeaderboardFragment();
        shopBuyFragment = new ShopBuyFragment();
        userFragment = new UserFragment();
        btn_cleaning = findViewById(R.id.btn_cleaning);
        btn_leaderboard = findViewById(R.id.btn_leaderboard);
        btn_shop = findViewById(R.id.btn_shop);
        btn_user = findViewById(R.id.btn_user);
        btn_cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, cleaningCleanFragment);
                transaction.commit();
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                btn_cleaning.setClickable(false);
                btn_leaderboard.setClickable(true);
                btn_shop.setClickable(true);
                btn_user.setClickable(true);


            }
        });
        btn_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, leaderboardFragment);
                transaction.commit();
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                btn_leaderboard.setClickable(false);
                btn_cleaning.setClickable(true);
                btn_shop.setClickable(true);
                btn_user.setClickable(true);

            }
        });
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, shopBuyFragment);
                transaction.commit();
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                btn_shop.setClickable(false);
                btn_leaderboard.setClickable(true);
                btn_cleaning.setClickable(true);
                btn_user.setClickable(true);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, userFragment);
                transaction.commit();
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
                btn_user.setClickable(false);
                btn_leaderboard.setClickable(true);
                btn_shop.setClickable(true);
                btn_cleaning.setClickable(true);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (usableBack == true) {
            if (isFirstFragment == false) {


                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                int size = fragments.size();
                if (size > 0)
                    getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();


                transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, cleaningCleanFragment);
                transaction.commit();
                btn_leaderboard.setClickable(true);
                btn_shop.setClickable(true);
                btn_user.setClickable(true);
            } else {
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, userFragment);
            transaction.commit();
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            int size = fragments.size();
            if (size > 0)
                getSupportFragmentManager().beginTransaction().remove(fragments.get(size - 1)).commit();
            usableBack = true;
        }
    }


}