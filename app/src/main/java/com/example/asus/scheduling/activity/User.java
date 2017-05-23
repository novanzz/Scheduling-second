package com.example.asus.scheduling.activity;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ASUS on 5/3/2017.
 */

@IgnoreExtraProperties
public class User {

    public String name,email,photoUrl;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String photoUrl,String name, String email) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.email = email;

    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getName() {return name;}

    public String getEmail() {return email;}

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
