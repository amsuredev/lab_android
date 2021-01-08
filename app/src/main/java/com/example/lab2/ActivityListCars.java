package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityListCars extends AppCompatActivity implements ListFragmentCars.CarListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
    }

    @Override
    public void itemClicked(String key) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null)
        {
            DetailFragmentCars details = new DetailFragmentCars();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            details.setKey(key);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent(this, DetailsViewOnlyActivity.class);
            intent.putExtra("key", key);
            startActivity(intent);
        }
    }
}