package com.example.asus.scheduling;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class groupViewHolder extends RecyclerView.ViewHolder{

    public TextView txtName;
    public ImageView photoUrl;
    public Button btnClick;

    public groupViewHolder(View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.txtNama);
        photoUrl = (CircleImageView)itemView.findViewById(R.id.fotogroup);
        btnClick = (Button) itemView.findViewById(R.id.button2);

    }

    }

