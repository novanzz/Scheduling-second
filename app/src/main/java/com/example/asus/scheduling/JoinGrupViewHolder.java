package com.example.asus.scheduling;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// viewholder untuk Join Grup
public class JoinGrupViewHolder extends RecyclerView.ViewHolder{

    public TextView txtEmail;
    public TextView txtName;
    public ImageView photoUrl;

    public JoinGrupViewHolder(View itemView) {
        super(itemView);
        txtEmail = (TextView)itemView.findViewById(R.id.txtEmail);
        txtName = (TextView)itemView.findViewById(R.id.txtNama);
        photoUrl = (ImageView)itemView.findViewById(R.id.fotouser);
    }
}

