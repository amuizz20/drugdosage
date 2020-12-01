package com.amtechnology.drugdosage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class home extends AppCompatActivity {
    public static String ProfilePic,ProfileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            ProfilePic = bundle.getString("ProfilePic");
            ProfileName = bundle.getString("Name");
        }
        final AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem reminderitem = new AHBottomNavigationItem("Reminder", R.drawable.ic_alarm_black_24dp);
        AHBottomNavigationItem additem = new AHBottomNavigationItem("Add", R.drawable.ic_add_circle_outline_black_24dp);
        AHBottomNavigationItem graphitem = new AHBottomNavigationItem("Graph", R.mipmap.graph);
        bottomNavigation.setCurrentItem(0);
        Fragment fragment = new Reminder();
        CallFragment(fragment);

        bottomNavigation.addItem(reminderitem);
        bottomNavigation.addItem(additem);
        bottomNavigation.addItem(graphitem);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0){
                    Fragment fragment = new Reminder();
                    CallFragment(fragment);
                    return true;
                }
                else if (position ==1){
                    Fragment fragment = new add();
                    CallFragment(fragment);
                    return true;
                }
                else if(position ==2){
                    Fragment fragment = new graph();
                    CallFragment(fragment);
                    return true;
                }
                return false;
            }
        });

    }
    private void CallFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.white)
                .setIcon(R.mipmap.close)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to log out")
                .setPositiveButton("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor sharedPreferences = getApplicationContext().getSharedPreferences("AutoLogin", Context.MODE_PRIVATE).edit();
                        sharedPreferences.putBoolean("autologin",false);
                        sharedPreferences.apply();
                        Intent intent = new Intent(home.this,MainActivity.class);
                        startActivity(intent);

                    }
                }).setNegativeButton("No", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();


    }
}
