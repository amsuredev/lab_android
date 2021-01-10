package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabItem textSizeItem;
    TabItem previewItem;
    TabItem colorPreviewItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        textSizeItem = findViewById(R.id.textSize);
        previewItem = findViewById(R.id.preview);
        colorPreviewItem = findViewById(R.id.color_preview);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount() {
                return 0;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new TextSizeFragment();
                    case 1:
                        return new PreviewMessageFragment();
                    case 2:
                        return new ColorPreviewFragment();
                    default:
                        return null;
                }
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}