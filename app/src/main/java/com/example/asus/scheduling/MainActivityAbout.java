package com.example.asus.scheduling;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.scheduling.adapter.AdapterAbout;

public class MainActivityAbout extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);


        // untuk membuat viewpager pakai adapter

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager2);

        AdapterAbout adapter = new AdapterAbout(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);



    }
}

