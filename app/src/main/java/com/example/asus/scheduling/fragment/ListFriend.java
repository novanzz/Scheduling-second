package com.example.asus.scheduling.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.Group;
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
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFriend extends Fragment {
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef,keyRef,dataRef;
    private FirebaseAuth mAuth;
    private FirebaseIndexRecyclerAdapter<User,friendViewHolder> adapter;
    String postKey;
    String photoUrl;
    String namaGrup;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //uji coba putExtra
        //Bundle args = getArguments();
        //key = args.getString("postKey");
        //Intent intent = getActivity().getIntent();
        //key = intent.getStringExtra("postKey");

        View rootView = inflater.inflate(R.layout.listview_friend_fragment, container, false);


        JoinGroupRecyclerview = (RecyclerView) rootView.findViewById(R.id.user_list);
            JoinGroupRecyclerview.setHasFixedSize(true);
            LinearLayoutManager lin = new LinearLayoutManager(getContext());
            lin.setOrientation(LinearLayoutManager.VERTICAL);
            JoinGroupRecyclerview.setLayoutManager(lin);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        final TextView mTxtNamaGrup = (TextView) rootView.findViewById(R.id.txtNamaGrup);
        final ImageView mPhotoGrup = (CircleImageView) rootView.findViewById(R.id.fotogroup);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    User user1 = dataSnapshot.getValue(User.class);
                    postKey = user1.getGroupID();
                    keyRef = mDatabaseRef.child("GroupUser").child(postKey);
                    dataRef = mDatabaseRef.child("User");
                    setupAdapter(keyRef, dataRef);
                    JoinGroupRecyclerview.setAdapter(adapter);

                    mDatabaseRef.child("Group").child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()!=null){
                                Group group1 = dataSnapshot.getValue(Group.class);
                                mTxtNamaGrup.setText(group1.getName());
                                Glide.with(getActivity())
                                        .load(group1.getUrl())
                                        .into(mPhotoGrup);
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



        return rootView;
        }

    private void setupAdapter(DatabaseReference key, DatabaseReference data) {
            adapter = new FirebaseIndexRecyclerAdapter<User, friendViewHolder>(User.class,R.layout.fragment_friend,friendViewHolder.class,key,data) {
                @Override
                protected void populateViewHolder(friendViewHolder viewHolder, User model, int position) {
                    viewHolder.txtEmail.setText(model.getEmail());
                    viewHolder.txtName.setText(model.getName());
                    Glide.with(getContext()).load(model.getPhotoUrl()).into(viewHolder.photoUrl);
                }
            };

    }

}



