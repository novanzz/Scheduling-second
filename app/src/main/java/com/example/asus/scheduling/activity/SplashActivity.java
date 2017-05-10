package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.scheduling.MainActivity;
import com.example.asus.scheduling.R;

public class SplashActivity extends AppCompatActivity {

    private static int splashInterval = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // munculin splashscreen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // menghubungkan activity splashscren ke main activity dengan intent
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);


                //jeda selesai Splashscreen
                finish();
            }



        }, splashInterval);

    };
}

