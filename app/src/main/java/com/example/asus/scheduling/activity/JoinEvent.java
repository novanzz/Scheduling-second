package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.friendViewHolder;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinEvent extends AppCompatActivity {
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef,keyRef,dataRef;
    private FirebaseAuth mAuth;
    private FirebaseIndexRecyclerAdapter<User,friendViewHolder> adapter;
    String postKey;
    private Button btnOut;
    //public String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_join_event);

        Intent intent = JoinEvent.this.getIntent();
        final String key = intent.getStringExtra("key");

        btnOut = (Button) findViewById(R.id.btnOut);

        JoinGroupRecyclerview = (RecyclerView) findViewById(R.id.user_list);
        JoinGroupRecyclerview.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(JoinEvent.this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        JoinGroupRecyclerview.setLayoutManager(lin);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    User user1 = dataSnapshot.getValue(User.class);
                    postKey = user1.getGroupID();
                    keyRef = mDatabaseRef.child("JoinEvent").child(postKey).child(key);
                    dataRef = mDatabaseRef.child("User");
                    setupAdapter(keyRef, dataRef);
                    JoinGroupRecyclerview.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //btn Out untuk keluar event
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();

                mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            User user1 = dataSnapshot.getValue(User.class);
                            String GrpId = user1.getGroupID();
                            // Set click listener for the whole post view

                            DatabaseReference refrensi = FirebaseDatabase.getInstance().getReference();
                            Query GroupQuery = refrensi.child("JoinEvent").child(GrpId).child(key).orderByKey().equalTo(user.getUid());
                            Toast.makeText(JoinEvent.this,"Anda Berhasil Keluar",Toast.LENGTH_SHORT).show();
                            GroupQuery.addListenerForSingleValueEvent
                                    (new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });


    }

    private void setupAdapter(DatabaseReference key, DatabaseReference data) {
        adapter = new FirebaseIndexRecyclerAdapter<User, friendViewHolder>(
                User.class,
                R.layout.fragment_joint_event,
                friendViewHolder.class,
                key,data
        ) {
            @Override
            protected void populateViewHolder(friendViewHolder viewHolder, User model, final int position) {
                viewHolder.txtEmail.setText(model.getEmail());
                viewHolder.txtName.setText(model.getName());
                Glide.with(JoinEvent.this).load(model.getPhotoUrl()).into(viewHolder.photoUrl);
                //mendapatkan key pada posisi sekarang
                //final DatabaseReference postRef = getRef(position);
                //final String postKey = postRef.getKey();

                //Intent intent = JoinEvent.this.getIntent();
                //final String key = intent.getStringExtra("key");

               /** viewHolder.Out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth = FirebaseAuth.getInstance();
                        final FirebaseUser user = mAuth.getCurrentUser();

                        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    User user1 = dataSnapshot.getValue(User.class);
                                    String GrpId = user1.getGroupID();
                                    // Set click listener for the whole post view

                                    DatabaseReference refrensi = FirebaseDatabase.getInstance().getReference();
                                    Query GroupQuery = refrensi.child("JoinEvent").child(GrpId).child(key).orderByKey().equalTo(user.getUid());
                                    Toast.makeText(JoinEvent.this,"Tidak Di Izinkan",Toast.LENGTH_SHORT).show();
                                    GroupQuery.addListenerForSingleValueEvent
                                            (new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                        appleSnapshot.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }

                                            });
                                }
                                }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                });**/


            }
        };

    }

}



