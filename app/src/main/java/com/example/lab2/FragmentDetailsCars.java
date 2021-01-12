package com.example.lab2;
import android.content.Context;
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

public class FragmentDetailsCars extends Fragment {
    private String key;

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
    Button modify;

    Context context;


    public void setKey(String key){
        this.key = key;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            this.key = savedInstanceState.getString("key");
        }
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

        context = inflater.getContext();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setViewsWithDb();
    }

    public void setViewsWithDb()
    {
        SQLiteDatabase db;
        try {
            SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(context);
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
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", this.key);
    }
}