package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.lab2.Add.APP_PREFERENCES;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout main_linear;

    SharedPreferences mSettings;
    Button add_button;
    Button list_button;

    Spinner spinner;
    TextView car_brand;
    TextView car_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_linear = findViewById(R.id.main_linear);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();


        add_button = (Button) findViewById(R.id.main_add_button);
        list_button = (Button) findViewById(R.id.main_list_button);
        add_button.setOnClickListener(this);
        list_button.setOnClickListener(this);

        car_brand = findViewById(R.id.main_car_brand);
        car_model = findViewById(R.id.main_car_model);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = parent.getItemAtPosition(position).toString();
                if (color.equals("green"))
                {
                    main_linear.setBackgroundColor(Color.GREEN);
                } else if (color.equals("white"))
                {
                    main_linear.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_add_button:
                //this code need to be deleted after testing
                Intent addActivity = new Intent(this, Add.class);
                startActivityForResult(addActivity, 1);
                break;
            case R.id.main_list_button:
                //Intent intent_list = new Intent(this, List.class);
                //startActivity(intent_list);
                Intent intent = new Intent(this, ActivityListCars.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1)
        {
            this.car_brand.setText(data.getStringExtra("car_brand"));
            this.car_model.setText(data.getStringExtra("car_model"));
        }
    }
}