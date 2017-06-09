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

import java.util.HashMap;
import java.util.Map;

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
        setContentView(R.layout.fragment_list_friend);

        //deklarasi pembuatan firebase recycler
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
        //adapter untuk menampilkan data yang berada di child group
        adapter = new FirebaseRecyclerAdapter<Group, groupViewHolder>(
                Group.class,
                R.layout.activity_join_group,
                groupViewHolder.class,
                mDatabaseRef.child("Group")
        ) {

            @Override
            protected void populateViewHolder(groupViewHolder viewHolder, Group model, int position) {
                //untuk mengindikasikan bahwa yang akan muncul itu ada txtname dan foto menggunakan glide
                viewHolder.txtName.setText(model.getName());
                Glide.with(JoinGrupActivity.this).load(model.getUrl()).into(viewHolder.photoUrl);

                //mendapatkan key pada posisi sekarang
                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                viewHolder.btnClick.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        User newU = new User(user.getPhotoUrl().toString(),user.getDisplayName(),user.getEmail(),postKey);
                        //Digunakan untuk push data ke dalam 2 node
                        Map<String,Object> update = new HashMap<>();
                        update.put("/User/"+user.getUid(),newU);
                        update.put("/GroupUser/"+postKey+"/"+user.getUid(),true);
                        // update data yang ada bila telah selesai akan pindah act
                        mDatabaseRef.updateChildren(update, new DatabaseReference.CompletionListener() {
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



