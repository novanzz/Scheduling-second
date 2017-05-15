package com.example.asus.scheduling.activity;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ASUS on 5/3/2017.
 */

@IgnoreExtraProperties
public class User {

    public String name;
    public String email,key;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.key = key;
    }
}
