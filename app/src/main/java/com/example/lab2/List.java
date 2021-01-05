package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.example.lab2.Add.APP_PREFERENCES;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class List extends AppCompatActivity implements View.OnClickListener {
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

    ArrayAdapter<String> adapter;

    TextView title;
    LinearLayout buttons;
    Button add;
    Button back;

    public final int id_add = View.generateViewId();
    public final int id_back = View.generateViewId();

    ListView listView;
    SharedPreferences mSettings;
    private String[] names = new String[] {"John", "Bob", "Alex", "Roman"};
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listView = findViewById(R.id.list);

        title = new TextView(this);
        title.setText("Available cars");
        title.setGravity(Gravity.CENTER);
        listView.addHeaderView(title);


        buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.CENTER);

        add = new Button(this);
        add.setText("Add");
        add.setId(id_add);
        back = new Button(this);
        back.setText("back");
        back.setId(id_back);

        add.setOnClickListener(this);
        back.setOnClickListener(this);

        buttons.addView(add);
        buttons.addView(back);

        listView.addFooterView(buttons);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Details.class);
                String key = listView.getItemAtPosition(position).toString().split("\n")[0];
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        //getListToDisplay();
        getListToDisplayNew();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Set<String> my_set_before = mSettings.getAll().keySet();
                int size_before = mSettings.getAll().size();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.remove(String.valueOf(position - 1));
                editor.apply();
                adapter.notifyDataSetChanged();
                getListToDisplay();
                int size_after = mSettings.getAll().size();
                Set<String> my_set_after = mSettings.getAll().keySet();
                return true;

                 */
                SharedPreferences.Editor editor = mSettings.edit();
                editor.remove(listView.getItemAtPosition(position).toString().split("\n")[0]);
                editor.apply();
                adapter.notifyDataSetChanged();
                getListToDisplayNew();
                return true;
            }
        });

        getSupportActionBar().setTitle("Car list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    protected void onResume() {
        super.onResume();
        getListToDisplayNew();
    }

    public void getListToDisplay()
    {

        int size = mSettings.getAll().size();
        String[] indexes = new String[size];
        String[] brand = new String[size];
        String[] model = new String[size];




        String[] cars = new String[size];
        for (int i = 0; i < size; i++)
        {
            Set<String> setElems = mSettings.getStringSet(String.valueOf(i), new HashSet<String>());
            for (String word: setElems)
            {
                //cars[i] += "\n" + word;
                if (word.contains(CAR_BRAND))
                {
                    String[] arr = word.split(this.SPLIT);
                    if (arr.length > 1) {
                        brand[i] = arr[1];
                    }else
                        {
                            brand[i] = "Not Given";
                        }
                }
                if (word.contains(CAR_MODEL))
                {
                    String[] arr = word.split(this.SPLIT);
                    if (arr.length > 1) {
                        model[i] = arr[1];
                    }else
                        {
                            model[i] = "Not Given";
                        }
                }
            }

            //list[i] = String.valueOf(i) + " " + mSettings.getStringSet(String.valueOf(i))
        }
        for (int i = 0; i < size; i++) {
            cars[i] = "Brand: " + brand[i] + "\n";
            cars[i] += "Model: " + model[i];
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cars);
        listView.setAdapter(adapter);
    }
    public void list()
    {
        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_list, names);
        listView.setAdapter(adapter);
        /*list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String val = (String)  list.getItemAtPosition(position);
                        Toast.makeText(parent.getContext(), val, Toast.LENGTH_LONG).show();
                    }
                }
        );
         */
    }

    void getListToDisplayNew()
    {
        int size = mSettings.getAll().size();
        String[] cars = new String[size];
        String[] keys = new String[size];
        String[] brand = new String[size];
        String[] model = new String[size];

        int i = 0;//sztuczny index

        for (String key: mSettings.getAll().keySet())
        {
            Set<String> setElems = mSettings.getStringSet(key,  new HashSet<String>());
            keys[i] = key;
            for (String word: setElems)
            {
                //cars[i] += "\n" + word;
                if (word.contains(CAR_BRAND))
                {
                    String[] arr = word.split(this.SPLIT);
                    if (arr.length > 1) {
                        brand[i] = arr[1];
                    }else
                    {
                        brand[i] = "Not Given";
                    }
                }
                if (word.contains(CAR_MODEL))
                {
                    String[] arr = word.split(this.SPLIT);
                    if (arr.length > 1) {
                        model[i] = arr[1];
                    }else
                    {
                        model[i] = "Not Given";
                    }
                }
            }
            i++;
        }
        for (int j = 0; j < size; j++) {
            cars[j] = keys[j] + "\n";
            cars[j] += "Brand: " + brand[j] + "\n";
            cars[j] += "Model: " + model[j];
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cars);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.id_back)
        {
            onBackPressed();
        }else if(v.getId() == this.id_add)
                {
                    Intent intent = new Intent(this, Add.class);
                    startActivity(intent);
                }
            }

}