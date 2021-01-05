package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import static com.example.lab2.Add.APP_PREFERENCES;

public class Details extends AppCompatActivity implements View.OnClickListener {

    final String SPLIT = "_";

    final String CAR_BRAND = "carBrand_";
    final String CAR_MODEL = "carModel_";
    final String CAR_PRICE = "carPrice_";
    final String CAR_MILEAGE = "carMileage_";
    final String CAR_CONDITION = "carCondition_";
    final String CAR_R = "caR_";
    final String CAR_G = "carG_";
    final String CAR_B = "carB_";
    final String CAR_BLUETOOTH = "carBluetooth_";
    final String CAR_ABS = "carAbs_";
    final String CAR_LEATHER = "carLeather_";

    TextView car_brand;
    TextView car_model;
    TextView car_price;
    TextView details_color;

    RadioButton zero_hundr;
    RadioButton hund_two_hund;
    RadioButton two_hund_plus;

    RatingBar rating;

    CheckBox bluetooth;
    CheckBox ABS;
    CheckBox if_leather;
    SharedPreferences mSettings;

    String key;

    Button cancel;
    Button modify;
    Button details_phone_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        car_brand = findViewById(R.id.details_car_brand);
        car_model = findViewById(R.id.details_car_model);
        car_price = findViewById(R.id.details_car_price);
        zero_hundr = findViewById(R.id.details_zero_hund);
        hund_two_hund = findViewById(R.id.details_hund_two_hund);
        two_hund_plus = findViewById(R.id.details_two_hun_plus);
        rating = findViewById(R.id.details_condition);
        bluetooth = findViewById(R.id.details_bluetooth);
        ABS = findViewById(R.id.details_ABS);
        if_leather = findViewById(R.id.details_leather_upholstery);
        modify = findViewById(R.id.details_modify);
        details_color = findViewById(R.id.details_color);


        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        modify.setOnClickListener(this);

        setViews();

        cancel = findViewById(R.id.details_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        details_phone_button = findViewById(R.id.details_phone_button);
        details_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder callDialog = new AlertDialog.Builder(v.getContext());
                callDialog.setTitle("Call");
                callDialog.setMessage(details_phone_button.getText().toString());
                callDialog.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                callDialog.setNegativeButton("Cancel", null);
                callDialog.show();
            }
        });

    }
    public void setViews()
    {
        Set<String> setElems = mSettings.getStringSet(this.key, new HashSet<String>());
        int red = 0;
        int green = 0;
        int blue = 0;
        for (String elem: setElems)
        {
            if (elem.contains(CAR_R))
            {
                String[] arr = elem.split(SPLIT);
                red = Integer.parseInt(arr[1]);
            }
            if (elem.contains(CAR_G))
            {
                String[] arr = elem.split(SPLIT);
                green = Integer.parseInt(arr[1]);
            }
            if (elem.contains(CAR_B))
            {
                String[] arr = elem.split(SPLIT);
                blue = Integer.parseInt(arr[1]);
            }
            if (elem.contains(CAR_BRAND))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    this.car_brand.setText(arr[1]);
                }else
                    {
                        this.car_brand.setText("Not Given");
                    }
            }
            if (elem.contains(CAR_MODEL))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    this.car_model.setText(arr[1]);
                }else
                {
                    this.car_model.setText("Not Given");
                }
            }
            if (elem.contains(CAR_PRICE))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    this.car_price.setText(arr[1]);
                }else
                {
                    this.car_price.setText("Not Given");
                }
            }
            if (elem.contains(CAR_MILEAGE))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    if(arr[1].equals("zeroHund"))
                    {
                        this.zero_hundr.setChecked(true);
                    }
                        else if(arr[1].equals("hundTwoHund"))
                    {
                        this.hund_two_hund.setChecked(true);
                    }
                        else {
                        this.two_hund_plus.setChecked(true);
                    }
                }
            }

            if (elem.contains(CAR_CONDITION))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    this.rating.setRating(Float.parseFloat(arr[1]));
                }
            }
            if (elem.contains(CAR_BLUETOOTH))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    if (arr[1].equals("true")) {
                        this.bluetooth.setChecked(true);
                    }
                }
            }

            if (elem.contains(CAR_ABS))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    if (arr[1].equals("true")) {
                        this.ABS.setChecked(true);
                    }
                }
            }

            if (elem.contains(CAR_LEATHER))
            {
                String[] arr = elem.split(SPLIT);
                if (arr.length > 1)
                {
                    if (arr[1].equals("true")) {
                        this.if_leather.setChecked(true);
                    }
                }
            }
        }
        this.details_color.setBackgroundColor(Color.rgb(red, green, blue));
    }

    private void replace_sets(Set<String> was, Set<String> changed)
    {
        for (String change_item: changed)
        {
            if (change_item.contains(CAR_BRAND))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_BRAND))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_MODEL))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_MODEL))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_PRICE))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_PRICE))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_MILEAGE))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_MILEAGE))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_CONDITION))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_CONDITION))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_BLUETOOTH))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_BLUETOOTH))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_ABS))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_ABS))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }

        for (String change_item: changed)
        {
            if (change_item.contains(CAR_LEATHER))
            {
                for (String was_item: was)
                {
                    if (was_item.contains(CAR_LEATHER))
                    {
                        was.remove(was_item);
                        was.add(change_item);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.details_cancel:
                onBackPressed();

            case R.id.details_modify:
                Set<String> setElems = mSettings.getStringSet(this.key, new HashSet<String>());
                Set<String> changed = new HashSet<>();
                changed.add(CAR_BRAND + car_brand.getText().toString());
                changed.add(CAR_MODEL + car_model.getText().toString());
                if (this.zero_hundr.isChecked()) {
                    changed.add(this.CAR_MILEAGE + "zeroHund");
                } else if (this.hund_two_hund.isChecked())
                {
                    changed.add(this.CAR_MILEAGE + "hundTwoHund");
                }
                else
                {
                    changed.add(this.CAR_MILEAGE + "twoHundPlus");
                }
                changed.add(this.CAR_CONDITION + this.rating.getRating());

                if (this.bluetooth.isChecked())
                {
                    changed.add(this.CAR_BLUETOOTH + "true");
                } else
                {
                    changed.add(this.CAR_BLUETOOTH + "false");
                }
                if (this.ABS.isChecked())
                {
                    changed.add(this.CAR_ABS + "true");
                } else
                {
                    changed.add(CAR_ABS + "false");
                }
                if (this.if_leather.isChecked())
                {
                    changed.add(this.CAR_LEATHER + "true");
                } else
                {
                    changed.add(this.CAR_LEATHER + "false");
                }

                replace_sets(setElems, changed);
                SharedPreferences.Editor editor = mSettings.edit();
                editor.remove(key);
                editor.putStringSet(key, setElems);
                editor.apply();
                editor.commit();
                Set<String> setElemsAfter = mSettings.getStringSet(this.key, new HashSet<String>());
                onBackPressed();
        }
    }
}