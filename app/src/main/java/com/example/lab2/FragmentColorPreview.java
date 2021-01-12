package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class FragmentColorPreview extends Fragment {
    class SeekBarListen implements SeekBar.OnSeekBarChangeListener {

        //convert from procent to color
        private int convert_from_procent_to_color(int percent)
        {
            double colorVal_before_Round = ((double) percent / 100) * 255;
            int colorVal = (int) Math.round(colorVal_before_Round);
            return colorVal;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId())
            {
                case R.id.settings_seekBarR:
                    color_display.setBackgroundColor(Color.rgb(convert_from_procent_to_color(progress), convert_from_procent_to_color(sk_G.getProgress()), convert_from_procent_to_color(sk_B.getProgress())));
                    break;
                case R.id.settings_seekBarG:
                    color_display.setBackgroundColor(Color.rgb(convert_from_procent_to_color(sk_R.getProgress()), convert_from_procent_to_color(progress), convert_from_procent_to_color(sk_B.getProgress())));
                    break;
                case R.id.settings_seekBarB:
                    color_display.setBackgroundColor(Color.rgb(convert_from_procent_to_color(sk_R.getProgress()), convert_from_procent_to_color(sk_G.getProgress()), convert_from_procent_to_color(progress)));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    SeekBar sk_R;
    SeekBar sk_G;
    SeekBar sk_B;
    View color_display;
    SharedPreferences mSettings;
    public static final String PREVIEW_SETTINGS = "PREVIEW_SETTINGS";
    public static final String COLOR_DISPLAY = "COLOR_DISPLAY";

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
        View view =  inflater.inflate(R.layout.fragment_color_preview, container, false);
        sk_R = (SeekBar) view.findViewById(R.id.settings_seekBarR);
        sk_G = (SeekBar) view.findViewById(R.id.settings_seekBarG);
        sk_B = (SeekBar) view.findViewById(R.id.settings_seekBarB);
        color_display = (TextView) view.findViewById(R.id.color_display);

        SeekBarListen colorListener = new SeekBarListen();

        sk_R.setOnSeekBarChangeListener(colorListener);
        sk_G.setOnSeekBarChangeListener(colorListener);
        sk_B.setOnSeekBarChangeListener(colorListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String color_coded = mSettings.getString(COLOR_DISPLAY, null);
        if (color_coded != null) {
            String[] rgb = color_coded.split(";");
            sk_R.setProgress(Integer.parseInt(rgb[0]));
            sk_G.setProgress(Integer.parseInt(rgb[1]));
            sk_B.setProgress(Integer.parseInt(rgb[2]));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(COLOR_DISPLAY, String.valueOf(sk_R.getProgress()) + ";" + String.valueOf(sk_G.getProgress()) + ";" + String.valueOf(sk_B.getProgress()) + ";");
        editor.apply();
        editor.commit();
    }
}