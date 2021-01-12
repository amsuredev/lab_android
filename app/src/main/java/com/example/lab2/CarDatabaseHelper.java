package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cars"; // Имя базы данных
    private static final int DB_VERSION = 2; // Версия базы данных

    public CarDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CAR (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "BRAND TEXT, "
                    + "MODEL TEXT, "
                    + "PRICE INTEGER, "
                    + "MILEAGE TEXT, "
                    + "ASSESSMENT REAL, "
                    + "R_COLOR INTEGER, "
                    + "G_COLOR INTEGER, "
                    + "B_COLOR INTEGER, "
                    + "BLUETOOTH NUMERIC, "
                    + "ABS NUMERIC, "
                    + "LEATHER NUMERIC);");
        /*
        ContentValues volvo = new ContentValues();
        volvo.put("BRAND", "Volvo");
        volvo.put("MODEL", "XC90");
        db.insert("CAR", null, volvo);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1)
        {
            db.execSQL("ALTER TABLE CAR ADD COLUMN PRICE INTEGER");
        }
    }

}
