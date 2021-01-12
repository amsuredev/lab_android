package com.example.lab2;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentListCars extends Fragment {
    static interface CarListListener{
        void itemClicked(String key);
        void longClick(String key);
    }

    private CarListListener listener;


    private ListView list_cars;

    Activity activity;
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
        this.listener = (CarListListener)activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_cars, container, false);
        list_cars = view.findViewById(R.id.list_cars);
        setCursorToActivity();

        list_cars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView)view;
                //String key = item.getText().toString().split("\n")[0];
                String key = String.valueOf(id);
                listener.itemClicked(key);
            }


        });
        list_cars.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //db.delete("CAR", "_id=" + String.valueOf(id), null);
                listener.longClick(String.valueOf(id));
                setCursorToActivity();
                return true;
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        setCursorToActivity();
    }

    private void setCursorToActivity()
    {
        try{
            SQLiteOpenHelper carDatabaseHelper = new CarDatabaseHelper(activity);
            db = carDatabaseHelper.getWritableDatabase();
            cursor = db.query("CAR",
                    new String[]{"_id", "BRAND"},
                    null, null, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(activity,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"BRAND"},
                    new int[]{android.R.id.text1},
                    0);
            list_cars.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(activity, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}