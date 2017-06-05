package com.example.asus.scheduling.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.friendViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListFriend extends Fragment {
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<User,friendViewHolder> adapter;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //uji coba putExtra
        //Bundle args = getArguments();
        //key = args.getString("postKey");
        //Intent intent = getActivity().getIntent();
        //key = intent.getStringExtra("postKey");

        View rootView = inflater.inflate(R.layout.listview_activity, container, false);


        JoinGroupRecyclerview = (RecyclerView) rootView.findViewById(R.id.user_list);
            JoinGroupRecyclerview.setHasFixedSize(true);
            LinearLayoutManager lin = new LinearLayoutManager(getContext());
            lin.setOrientation(LinearLayoutManager.VERTICAL);
            JoinGroupRecyclerview.setLayoutManager(lin);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        setupAdapter();
        JoinGroupRecyclerview.setAdapter(adapter);



        return rootView;
        }

    private void setupAdapter() {

        adapter = new FirebaseRecyclerAdapter<User, friendViewHolder>(
                User.class,
                R.layout.fragment_friend,
                friendViewHolder.class,
                mDatabaseRef.child("User")
        ) {

            @Override
            public void populateViewHolder(friendViewHolder viewHolder, User model, int position) {
                viewHolder.txtEmail.setText(model.getEmail());
                viewHolder.txtName.setText(model.getName());
                Glide.with(getContext()).load(model.getPhotoUrl()).into(viewHolder.photoUrl);

            }
        };

    }

}



