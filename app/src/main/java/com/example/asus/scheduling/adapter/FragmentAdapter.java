package com.example.asus.scheduling.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.scheduling.fragment.AccountActivity;
import com.example.asus.scheduling.fragment.BlankFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(Context context, FragmentManager fm) {
        // Required empty public constructor
        super(fm);
    }

    @Override
    public int getCount() {

        return 2;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BlankFragment();
        }else{
            return new AccountActivity();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Blank";
        } else {
            return "Blank3";
        }
    }
}