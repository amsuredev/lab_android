package com.example.lab2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class ActivityListCars extends AppCompatActivity implements FragmentListCars.CarListListener{
    private String selected_key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void itemClicked(String key) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null)
        {
            FragmentDetailsCars details = new FragmentDetailsCars();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setKey(key);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            selected_key = key;
        } else {
            Intent intent = new Intent(this, ActivityDetailsViewOnly.class);
            intent.putExtra("key", key);
            startActivity(intent);
        }
    }

    @Override
    public void longClick(String key) {
        SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(this);
        SQLiteDatabase db = carDatabaseHelper.getReadableDatabase();
        db.delete("CAR", "_id=" + String.valueOf(key), null);
        if (selected_key != null) {
            if (selected_key.equals(key)) {
                FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
                fragmentContainer.removeAllViews();
                selected_key = null;
            }
        }
    }

}