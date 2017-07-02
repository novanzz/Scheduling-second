package com.example.asus.scheduling.Model;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Tanggal{

    public String date, photoUrl, name, userId;
    public String time, note, idTanggal;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Tanggal.class)
    public Tanggal() {
    }

    public Tanggal(String date, String time, String note, String photoUrl, String name,
                   String idTanggal , String userId) {
        this.date = date;
        this.time= time;
        this.note = note;

        this.photoUrl = photoUrl;
        this.name = name;
        this.idTanggal = idTanggal;
        this.userId = userId;
    }

    public String getDate() {return date;}

    public String getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }



    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getName() {return name;}

    public String getIdTanggal() {return idTanggal;}

    public String getUserId() {return userId;}

    public void setDate(String date) {this.date= date;}

    public void setTime(String time) {
        this.time= time;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl;}

    public void setName(String name) {this.name = name;}

    public void setIdTanggal(String date) { this.idTanggal = idTanggal;}

    public void setUserId(String userId) {this.userId = userId;}
}