package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.MainActivity;
import com.example.asus.scheduling.Model.Group;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.groupViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinGrupActivity extends AppCompatActivity {

    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<Group, groupViewHolder> adapter;
    private DatabaseReference mFirebaseDatabase, userRef;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;


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

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        userRef = mFirebaseDatabase;

        mAuth = FirebaseAuth.getInstance();


    }

    private void setupAdapter() {
        adapter = new FirebaseRecyclerAdapter<Group, groupViewHolder>(
                Group.class,
                R.layout.activity_join_group,
                groupViewHolder.class,
                mDatabaseRef.child("Group")
        ) {

            @Override
            protected void populateViewHolder(groupViewHolder viewHolder, Group model, int position) {
                viewHolder.txtName.setText(model.getName());
                Glide.with(JoinGrupActivity.this).load(model.getUrl()).into(viewHolder.photoUrl);

                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                viewHolder.btnClick.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        User newU = new User(user.getPhotoUrl().toString(),user.getDisplayName(),user.getEmail(),postKey);
                        mDatabaseRef.child("User").child(user.getUid()).setValue(newU, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                Intent i = new Intent(JoinGrupActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
                    }

                });


            }
        };
    }
}



