package com.example.lab2;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class FragmentTextSize extends Fragment {
    RadioGroup rg;
    public static final String TEXT_SIZE = "TEXT_SIZE";
    SharedPreferences mSettings;
    public static final String PREVIEW_SETTINGS = "PREVIEW_SETTINGS";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mSettings = context.getSharedPreferences(PREVIEW_SETTINGS, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_size, container, false);
        rg = (RadioGroup) view.findViewById(R.id.radio_group_text_size);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        int id = rg.getCheckedRadioButtonId();
        SharedPreferences.Editor editor = mSettings.edit();
        switch (id){
            case -1:
                return;
            case R.id.text_size_12:
                editor.putInt(TEXT_SIZE, 12);
                break;
            case R.id.text_size_15:
                editor.putInt(TEXT_SIZE, 15);
                break;
            case R.id.text_size_18:
                editor.putInt(TEXT_SIZE, 18);
                break;
        }
        editor.apply();
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        int text_size = mSettings.getInt(TEXT_SIZE, -1);
        if (text_size != -1){
            switch (text_size){
                case 12:
                    rg.check(R.id.text_size_12);
                    break;
                case 15:
                    rg.check(R.id.text_size_15);
                    break;
                case 18:
                    rg.check(R.id.text_size_18);
                    break;
            }
        }
    }
}