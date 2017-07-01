package com.example.asus.scheduling;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

// viewholder untuk Join Grup
public class friendViewHolder extends RecyclerView.ViewHolder{

    public TextView txtEmail;
    public TextView txtName;
    public ImageView photoUrl;
    public Button Out;

    public friendViewHolder(View itemView) {
        super(itemView);
        txtEmail = (TextView)itemView.findViewById(R.id.txtEmail);
        txtName = (TextView)itemView.findViewById(R.id.txtNama);
        photoUrl = (CircleImageView)itemView.findViewById(R.id.fotouser);
      }
}

