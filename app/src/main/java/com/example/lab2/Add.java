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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class Add extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_TEXT_COLOR = "text_color"; // color
    SharedPreferences mSettings;

    EditText car_brand;
    EditText car_model;
    EditText car_price;

    RadioButton zero_hund;
    RadioButton hund_two_hund;
    RadioButton two_hund_plus;
    RatingBar rating;

    CheckBox bluetooth;
    CheckBox ABS;
    CheckBox if_leather;

    SeekBar sk_R;
    SeekBar sk_G;
    SeekBar sk_B;

    TextView color;

    Button back;
    Button add;

    TextView phone_num;
    Button phone_button;

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
    final String INDEX = "index_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //SharedPreferences mySharedPrefernces = getSharedPreferences(APP_PREFERENCES)
        /*
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_TEXT_COLOR, "yellow");
        editor.apply();

         */


        sk_R = (SeekBar) findViewById(R.id.settings_seekBarR);
        sk_G = (SeekBar) findViewById(R.id.settings_seekBarG);
        sk_B = (SeekBar) findViewById(R.id.settings_seekBarB);

        sk_R.setOnSeekBarChangeListener(this);
        sk_G.setOnSeekBarChangeListener(this);
        sk_B.setOnSeekBarChangeListener(this);

        color = (TextView) findViewById(R.id.settings_color);

        back = (Button) findViewById(R.id.settings_cancel);
        add = (Button) findViewById(R.id.settings_add);

        back.setOnClickListener(this);
        add.setOnClickListener(this);

        car_brand = findViewById(R.id.settings_car_brand);
        car_model = findViewById(R.id.settings_car_model);
        car_price = findViewById(R.id.settings_car_price);

        zero_hund = findViewById(R.id.settings_zero_hund);
        hund_two_hund = findViewById(R.id.settings_hund_two_hund);
        two_hund_plus = findViewById(R.id.settings_two_hun_plus);
        rating = findViewById(R.id.settings_condition);

        bluetooth = findViewById(R.id.settings_bluetooth);
        ABS = findViewById(R.id.settings_ABS);
        if_leather = findViewById(R.id.settings_leather_upholstery);

        getSupportActionBar().setTitle("Add car");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone_num = findViewById(R.id.add_phone_text);
        phone_button = findViewById(R.id.add_phone_button);

        final AlertDialog.Builder callDialog = new AlertDialog.Builder(this);

        phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.setTitle("Call");
                callDialog.setMessage(phone_num.getText().toString());
                callDialog.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                callDialog.setNegativeButton("Cancel", null);
                callDialog.show();
            }
        });
    }

    int convertToColorVal(int percent)
    {
        double colorVal_before_Round = ((double) percent / 100) * 255;
        int colorVal = (int) Math.round(colorVal_before_Round);
        return colorVal;
    }


    //seekbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId())
        {
            case R.id.settings_seekBarR:
                color.setBackgroundColor(Color.rgb(convertToColorVal(progress), convertToColorVal(sk_G.getProgress()), convertToColorVal(sk_B.getProgress())));
                break;
            case R.id.settings_seekBarG:
                color.setBackgroundColor(Color.rgb(convertToColorVal(sk_R.getProgress()), convertToColorVal(progress), convertToColorVal(sk_B.getProgress())));
                break;
            case R.id.settings_seekBarB:
                color.setBackgroundColor(Color.rgb(convertToColorVal(sk_R.getProgress()), convertToColorVal(sk_G.getProgress()), convertToColorVal(progress)));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.settings_cancel:
                onBackPressed();
                break;

            case R.id.settings_add:
                mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSettings.edit();
                Set<String> lines = new HashSet<String>();
                lines.add(this.CAR_BRAND + car_brand.getText().toString());
                lines.add(this.CAR_MODEL + car_model.getText().toString());
                lines.add(this.CAR_PRICE + car_price.getText().toString());
                if (this.zero_hund.isChecked()) {
                    lines.add(this.CAR_MILEAGE + "zeroHund");
                } else if (this.hund_two_hund.isChecked())
                {
                    lines.add(this.CAR_MILEAGE + "hundTwoHund");
                }
                else
                    {
                        lines.add(this.CAR_MILEAGE + "twoHundPlus");
                    }
                lines.add(String.valueOf(this.CAR_CONDITION + this.rating.getRating()));
                lines.add(String.valueOf(this.CAR_R + convertToColorVal(this.sk_R.getProgress())));
                lines.add(String.valueOf(this.CAR_G + convertToColorVal(this.sk_G.getProgress())));
                lines.add(String.valueOf(this.CAR_B + convertToColorVal(this.sk_B.getProgress())));
                if (this.bluetooth.isChecked())
                {
                    lines.add(this.CAR_BLUETOOTH + "true");
                } else
                    {
                        lines.add(this.CAR_BLUETOOTH + "false");
                    }
                if (this.ABS.isChecked())
                {
                    lines.add(this.CAR_ABS + "true");
                } else
                {
                    lines.add(CAR_ABS + "false");
                }
                if (this.if_leather.isChecked())
                {
                    lines.add(this.CAR_LEATHER + "true");
                } else
                {
                    lines.add(this.CAR_LEATHER + "false");
                }
                String key = getNewKey();

                lines.add(this.INDEX + key);
                editor.putStringSet(key, lines);
                editor.apply();

                //extras to main

                Intent to_main = new Intent();
                to_main.putExtra("car_brand", car_brand.getText().toString());
                to_main.putExtra("car_model", car_model.getText().toString());
                setResult(RESULT_OK, to_main);
                finish();
        }
    }

    String getNewKey()
    {
        Set<String> keys = mSettings.getAll().keySet();
        if (mSettings.getAll().size() == 0)
        {
            return "1";
        } else {
            int last_index = -1;
            for (String key: keys)
            {
                if (Integer.parseInt(key) > last_index)
                {
                    last_index = Integer.parseInt(key);
                }
            }
            String newKey = String.valueOf(++last_index);
            return newKey;
        }
    }
}