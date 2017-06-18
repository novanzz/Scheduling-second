package com.example.asus.scheduling.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ASUS on 6/17/2017.
 */

@IgnoreExtraProperties
public class IdTanggal{


    private String IdTgl;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public IdTanggal() {
    }

    public IdTanggal(String IdTgl) {
        this.IdTgl = IdTgl;


    }

    public String getIdTgl() {
        return IdTgl;
    }

    public void setIdTgl(String IdTgl) {this.IdTgl = IdTgl;}



}