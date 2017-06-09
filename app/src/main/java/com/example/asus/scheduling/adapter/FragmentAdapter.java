package com.example.asus.scheduling.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.scheduling.fragment.AccountActivity;
import com.example.asus.scheduling.fragment.BlankFragment;
import com.example.asus.scheduling.fragment.ListFriend;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(Context context, FragmentManager fm) {
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
            return new BlankFragment();
        }
        if (position == 1) {
            return new AccountActivity();
        }else{
            return new ListFriend();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Calendar";
        } if(position ==1) {
            return "Profil";
        }else {
            return "Friend";
        }
    }
}