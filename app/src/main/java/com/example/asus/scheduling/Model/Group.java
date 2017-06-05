package com.example.asus.scheduling.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ASUS on 5/28/2017.
 */

@IgnoreExtraProperties
public class Group{

    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Group() {
    }

    public Group(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}