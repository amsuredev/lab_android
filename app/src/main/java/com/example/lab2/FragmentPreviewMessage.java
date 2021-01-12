package com.example.lab2;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentPreviewMessage extends Fragment {
    EditText preview_message;
    SharedPreferences mSettings;
    public static final String PREVIEW_SETTINGS = "PREVIEW_SETTINGS";
    public static final String TEXT_PREVIEW_MESSAGE = "TEXT_PREVIEW_MESSAGE";

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
        View view =  inflater.inflate(R.layout.fragment_preview_message, container, false);
        preview_message = view.findViewById(R.id.preview_message);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(TEXT_PREVIEW_MESSAGE, preview_message.getText().toString());
        editor.apply();
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        String previewMessageText = mSettings.getString(TEXT_PREVIEW_MESSAGE, null);
        if (previewMessageText != null) {
            preview_message.setText(previewMessageText);
        }
    }
}