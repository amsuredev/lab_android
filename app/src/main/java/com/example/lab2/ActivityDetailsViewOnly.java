package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailsViewOnlyActivity extends AppCompatActivity implements View.OnClickListener {
    private String key;
    private Button back;
    private Button modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view_only);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        DetailFragmentCars detailFragmentCars = new DetailFragmentCars();
        detailFragmentCars.setKey(key);
        FragmentTransaction fg = getFragmentManager().beginTransaction();
        fg.replace(R.id.frag_container, detailFragmentCars);
        //fg.addToBackStack(null);
        fg.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fg.commit();

        back = findViewById(R.id.back);
        modify = findViewById(R.id.modify);

        back.setOnClickListener(this);
        modify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.modify:
                Intent intent = new Intent(this, Details.class);
                intent.putExtra("key", key);
                startActivity(intent);
        }
    }
}