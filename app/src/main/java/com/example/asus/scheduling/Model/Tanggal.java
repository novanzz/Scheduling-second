package com.example.asus.scheduling.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ASUS on 6/16/2017.
 */
@IgnoreExtraProperties
public class Tanggal{

    public String date, photoUrl, name;
    public String time, note,GroupId, idTanggal;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Tanggal.class)
    public Tanggal() {
    }

    public Tanggal(String date, String time, String note, String GroupId, String photoUrl, String name, String idTanggal) {
        this.date = date;
        this.time= time;
        this.note = note;
        this.GroupId = GroupId;
        this.photoUrl = photoUrl;
        this.name = name;
        this.idTanggal = idTanggal;
    }

    public String getDate() {return date;}

    public String getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public String getUserId() {
        return GroupId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getName() {return name;}

    public void setDate(String date) {this.date= date;}

    public void setTime(String time) {
        this.time= time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setUserId(String GroupId) { this.GroupId = GroupId;}

    public String getIdTanggal() {return idTanggal;}

    public void setIdTanggal(String date) { this.idTanggal = idTanggal;}




}