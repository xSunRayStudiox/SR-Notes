package com.sun_ray.srnotes;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.sun_ray.srnotes.fragment.Notes_List;
import com.sun_ray.srnotes.fragment.Password_List;
import com.sun_ray.srnotes.fragment.Url_List;

import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class B_Home extends AppCompatActivity {
    private SmoothBottomBar navigationView;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialization
        navigationView = findViewById(R.id.bottomBar);
        setFragment(new Notes_List());

        // Bottom Navigation OnClick Operation
        navigationView.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            if (i==0){
                setFragment(new Notes_List());
                return true;
            }
            if (i==1){
                setFragment(new Url_List());
                return true;
            }
            if (i==2){
                setFragment(new Password_List());
                return true;
            }
            return false;
        });

        OnBackPressedDispatcher onBackPressed = getOnBackPressedDispatcher();
        onBackPressed.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (currentFragment instanceof Notes_List){
                    onBackPressed.onBackPressed();
                } else {
                    setFragment(new Notes_List());
                    currentFragment = new Notes_List();
                    navigationView.setItemActiveIndex(0);
                }
            }
        });
    }
    private void setFragment(Fragment fragment) {
        // Fragment Set
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main_Fragment, fragment);
        transaction.commit();
        // Save Fragment in Variable
        currentFragment = fragment;
    }

//    @Override
//    public void onBackPressed() {
//        // Check Current Fragment And Set Or Back Press Close
//        if (currentFragment instanceof Notes_List){
//            super.onBackPressed();
//        } else {
//            setFragment(new Notes_List());
//            currentFragment = new Notes_List();
//            navigationView.setItemActiveIndex(0);
//        }
//    }

}