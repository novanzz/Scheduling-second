package com.example.asus.scheduling.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.scheduling.fragment.About1;
import com.example.asus.scheduling.fragment.About2;
import com.example.asus.scheduling.fragment.About3;

public class AdapterAbout extends FragmentPagerAdapter {

    public AdapterAbout(Context context, FragmentManager fm) {
        // Required empty public constructor
        super(fm);
    }

    @Override
    public int getCount() {

        return 3;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new About1();
        }
        if (position == 1) {
            return new About2();
        } else {
            return new About3();
        }

    }
}