package com.example.evelina.blomapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private BottomNavigationView mBottomNav;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = (TextView) findViewById(R.id.tvUserName);
        String username = Session.getInstance().currentUser.getUsername();
        tvName.setText(username);


        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation_view);
        mBottomNav.getMenu().getItem(2).setChecked(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ActivitySwitcher.selectActivity(Profile.this,item);
                return true;
            }
        });
    }

}
