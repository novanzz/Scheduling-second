package com.example.asus.scheduling;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 6/16/2017.
 */

public class tanggalViewHolder extends RecyclerView.ViewHolder{


    public TextView txtDate,txtTime;
    public TextView txtName;
    public ImageView photoUrl;
    public CardView cardView;
    public ImageView btnJoin;
    public TextView note;

    public tanggalViewHolder(View itemView) {
        super(itemView);
        txtDate = (TextView)itemView.findViewById(R.id.date);
        txtName = (TextView)itemView.findViewById(R.id.txtNama);
        photoUrl = (CircleImageView)itemView.findViewById(R.id.fotouser);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        btnJoin = (ImageView)itemView.findViewById(R.id.Join);
        note = (TextView) itemView.findViewById(R.id.note);
        txtTime = (TextView) itemView.findViewById(R.id.time);
    }

}
