package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainBottomNavigationActivity extends AppCompatActivity {
    BottomNavigationView mainBottomNavigationActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);
        mainBottomNavigationActivity = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        mainBottomNavigationActivity.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        Intent addActivityIntent = new Intent(getBaseContext(), Add.class);
                        startActivity(addActivityIntent);
                        break;
                    case R.id.settings:
                        Intent settingsActivity = new Intent(getBaseContext(), ActivitySettingsTest.class);
                        startActivity(settingsActivity);
                        break;
                    case R.id.list:
                        final Intent listActivity = new Intent(getBaseContext(), ActivityListCars.class);
                        startActivity(listActivity);
                        break;
                }
                return false;
            }
        });
    }


}