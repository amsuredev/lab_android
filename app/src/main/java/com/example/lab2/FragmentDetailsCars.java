package com.example.lab2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class FragmentDetailsCars extends Fragment {
    private String key;
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

    Button cancel;
    Button modify;
    Button details_phone_button;

    Activity activity;

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";

    public void setKey(String key){
        this.key = key;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
        mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_cars, container, false);
        car_brand = view.findViewById(R.id.details_car_brand);
        car_model = view.findViewById(R.id.details_car_model);
        car_price = view.findViewById(R.id.details_car_price);
        zero_hundr = view.findViewById(R.id.details_zero_hund);
        hund_two_hund = view.findViewById(R.id.details_hund_two_hund);
        two_hund_plus = view.findViewById(R.id.details_two_hun_plus);
        rating = view.findViewById(R.id.details_condition);
        bluetooth = view.findViewById(R.id.details_bluetooth);
        ABS = view.findViewById(R.id.details_ABS);
        if_leather = view.findViewById(R.id.details_leather_upholstery);
        modify = view.findViewById(R.id.details_modify);
        details_color = view.findViewById(R.id.details_color);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //setViews();
        setViewsWithDb();
    }

    public void setViewsWithDb()
    {
        SQLiteDatabase db;
        try {
            SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(activity);
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
                rating.setRating(cursor.getFloat(4));
                this.details_color.setBackgroundColor(Color.rgb(cursor.getInt(5), cursor.getInt(6), cursor.getInt(7)));
                this.bluetooth.setChecked(cursor.getInt(8) == 1);
                this.ABS.setChecked(cursor.getInt(9) == 1);
                this.if_leather.setChecked(cursor.getInt(10) == 1);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(activity, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
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
}