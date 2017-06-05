package com.example.asus.scheduling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.asus.scheduling.activity.JoinGrupActivity;
import com.example.asus.scheduling.activity.LoginActivity;
import com.example.asus.scheduling.adapter.FragmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends JoinGrupActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //uji coba putExtra
        // intent = getIntent();
        //String value = intent.getStringExtra("postKey");

        //Bundle bundle = new Bundle();
        //bundle.putString("postKey", value);
        //intent.putExtras(bundle);

        //Bundle bundle = new Bundle();
        //bundle.putString("postKey",value);
        //ListFriend myFrag = new ListFriend();
        //myFrag.setArguments(bundle);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            //MainActivity.this.finish();
        }
            // untuk membuat viewpager pakai adapter

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);



    }
}

