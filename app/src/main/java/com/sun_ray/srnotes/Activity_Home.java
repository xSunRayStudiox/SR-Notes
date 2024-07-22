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

public class Activity_Home extends AppCompatActivity {
    private SmoothBottomBar navigationView;
    private Fragment currentFragment;
    private OnBackPressedDispatcher onBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialization
        navigationView = findViewById(R.id.bottomBar);
        setFragment(new Notes_List());
        Bottom_Navigation();
        Back_Pressed();
    }

    private void Bottom_Navigation(){
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
    }

    public void setFragment(Fragment fragment) {
        // Fragment Set
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main_Fragment, fragment);
        transaction.commit();
        currentFragment = fragment;
    }

    private void Back_Pressed(){
        onBackPressed = getOnBackPressedDispatcher();
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

}