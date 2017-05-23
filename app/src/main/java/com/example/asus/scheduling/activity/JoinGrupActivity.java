package com.example.asus.scheduling.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.JoinGrupViewHolder;
import com.example.asus.scheduling.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinGrupActivity extends AppCompatActivity {

    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<User,JoinGrupViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);

        JoinGroupRecyclerview = (RecyclerView) findViewById(R.id.user_list);
        JoinGroupRecyclerview.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        JoinGroupRecyclerview.setLayoutManager(lin);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        setupAdapter();
        JoinGroupRecyclerview.setAdapter(adapter);


    }

    private void setupAdapter() {
        adapter = new FirebaseRecyclerAdapter<User, JoinGrupViewHolder>(
               User.class,
                R.layout.activity_join_group,
                JoinGrupViewHolder.class,
                mDatabaseRef.child("User")
        ) {

            @Override
            protected void populateViewHolder(JoinGrupViewHolder viewHolder, User model, int position) {
                viewHolder.txtEmail.setText(model.getEmail());
                viewHolder.txtName.setText(model.getName());
                Glide.with(JoinGrupActivity.this).load(model.getPhotoUrl()).into(viewHolder.photoUrl);
            }
        };

    }
}





