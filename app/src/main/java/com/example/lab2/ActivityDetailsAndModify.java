package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityDetailsAndModify extends AppCompatActivity implements View.OnClickListener {

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

    String key;

    Button cancel;
    Button modify;
    Button details_phone_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_and_modify);

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


        modify.setOnClickListener(this);

        setViewsWithDb();

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


    public void setViewsWithDb()
    {
        SQLiteDatabase db;
        try {
                SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(this);
                db = carDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("CAR", new String[]{"BRAND", "MODEL", "PRICE", "MILEAGE", "ASSESSMENT", "R_COLOR", "G_COLOR", "B_COLOR", "BLUETOOTH", "ABS", "LEATHER"},
                                        "_id = ?", new String[] {this.key},
                                        null, null, null);
                if (cursor.moveToFirst()){
                    car_brand.setText(cursor.getString(0));
                    car_model.setText(cursor.getString(1));
                    car_price.setText(cursor.getString(2));
                    if (cursor.getString(3) != null) {
                        if (cursor.getString(3).equals("zero_hund")) {
                            this.zero_hundr.setChecked(true);
                        } else if (cursor.getString(3).equals("hund_two_hund")) {
                            this.hund_two_hund.setChecked(true);
                        } else if (cursor.getString(3).equals("two_hund_plus")) {
                            two_hund_plus.setChecked(true);
                        }
                    }
                    float rating_num = cursor.getFloat(4);
                    rating.setRating(cursor.getFloat(4));
                    this.details_color.setBackgroundColor(Color.rgb(cursor.getInt(5), cursor.getInt(6), cursor.getInt(7)));
                    this.bluetooth.setChecked(cursor.getInt(8) == 1);
                    this.ABS.setChecked(cursor.getInt(9) == 1);
                    this.if_leather.setChecked(cursor.getInt(10) == 1);
                }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void update_car_cal(){
        ContentValues newCarVals = new ContentValues();
        newCarVals.put("BRAND", this.car_brand.getText().toString());
        newCarVals.put("MODEL", this.car_model.getText().toString());
        newCarVals.put("PRICE", this.car_price.getText().toString());
        String mileage = null;
        if (this.zero_hundr.isChecked()) {
            mileage = "zero_hund";
        } else if (this.hund_two_hund.isChecked()){
            mileage = "hund_two_hund";
        } else if (this.two_hund_plus.isChecked())
        {
            mileage = "two_hund_plus";
        }
        if (mileage != null) {
            newCarVals.put("MILEAGE", mileage);
        }
        float rating = this.rating.getRating();
        newCarVals.put("ASSESSMENT", this.rating.getRating());
        newCarVals.put("BLUETOOTH", this.bluetooth.isChecked());
        newCarVals.put("ABS", this.ABS.isChecked());
        newCarVals.put("LEATHER", this.if_leather.isChecked());
        //color not changed
        CarDatabaseHelper myHelper = new CarDatabaseHelper(this);
        try {
            SQLiteDatabase db = myHelper.getWritableDatabase();
            db.update("CAR", newCarVals,
                    "_id = ?", new String[] {this.key});
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.details_cancel:
                onBackPressed();

            case R.id.details_modify:
                update_car_cal();
                onBackPressed();
        }
    }
}