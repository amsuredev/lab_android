package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMainBottomNavigation extends AppCompatActivity {
    BottomNavigationView mainBottomNavigationActivity;
    TextView color_view;
    TextView preview_message;
    SharedPreferences mSettings;

    public static final String PREVIEW_SETTINGS = "PREVIEW_SETTINGS";
    public static final String COLOR_DISPLAY = "COLOR_DISPLAY";
    public static final String TEXT_PREVIEW_MESSAGE = "TEXT_PREVIEW_MESSAGE";
    public static final String TEXT_SIZE = "TEXT_SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);
        mSettings = getSharedPreferences(PREVIEW_SETTINGS, Context.MODE_PRIVATE);
        mainBottomNavigationActivity = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        mainBottomNavigationActivity.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        Intent addActivityIntent = new Intent(getBaseContext(), ActivityAddCar.class);
                        startActivity(addActivityIntent);
                        break;
                    case R.id.settings:
                        Intent settingsActivity = new Intent(getBaseContext(), ActivitySettings.class);
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
        color_view = (TextView) findViewById(R.id.color_square);
        preview_message = (TextView) findViewById(R.id.preview_message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String color_coded = mSettings.getString(COLOR_DISPLAY, null);
        if (color_coded != null) {
            String[] rgb = color_coded.split(";");
            color_view.setBackgroundColor(Color.rgb(convert_from_procent_to_color(Integer.parseInt(rgb[0])), convert_from_procent_to_color(Integer.parseInt(rgb[1])), convert_from_procent_to_color(Integer.parseInt(rgb[2]))));
        }
        String text = mSettings.getString(TEXT_PREVIEW_MESSAGE, null);
        if (text != null) {
            preview_message.setText(text);
        }
        int text_size = mSettings.getInt(TEXT_SIZE, -1);
        if (text_size != -1) {
            preview_message.setTextSize(text_size);
            /*
            switch (text_size){

            }
                case 12:
                    preview_message.setTextSize(text_size);
                    break;
                case 15:
                    preview_message.setTextSize(text_size);
                    break;
                case 18:
                    preview_message.setTextSize(text_size);
                    break;
        }

             */
        }
    }

    private int convert_from_procent_to_color(int percent)
    {
        double colorVal_before_Round = ((double) percent / 100) * 255;
        int colorVal = (int) Math.round(colorVal_before_Round);
        return colorVal;
    }
}