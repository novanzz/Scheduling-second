package com.example.asus.scheduling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.activity.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Account extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button mBtnLogout,mbtnOutGroup;
    private GoogleApiClient mGoogleApiClient;
    private ImageView mFotoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_account, container, false);

        mBtnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        mbtnOutGroup = (Button) rootView.findViewById(R.id.btnOutGroup);
        mBtnLogout.setOnClickListener(this);
        mbtnOutGroup.setOnClickListener(this);

        mFotoUser = (ImageView) rootView.findViewById(R.id.fotouser);

        //Congfigure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                /**.enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getContext(), "Error Cuy", Toast.LENGTH_LONG).show();
                    }
                })**/
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //isi dari button sama text bentuk emaill
        TextView mTextUser = (TextView) rootView.findViewById(R.id.textEmail);
        TextView mTextUsername = (TextView) rootView.findViewById(R.id.textUserName);


        // cek user ada apa engga di firebase supaya  klo dah login lu ga ush balik lagi ke login
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        }else {
            mTextUser.setText(user.getEmail());
            mTextUsername.setText(user.getDisplayName());
            Glide.with(this)
                    .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                    .into(mFotoUser);
        }


        return rootView;
    }


 public void keluarGroup(){

     DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
     // Attach a listener to read the data at our posts reference
     ref.child("GroupUser").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                 String postkey = postSnapshot.getKey();
                 //hapus user pada groupuser
                 FirebaseUser userGroup = FirebaseAuth.getInstance().getCurrentUser();
                 DatabaseReference refrensi = FirebaseDatabase.getInstance().getReference();
                 Query GroupQuery = refrensi.child("GroupUser").child(postkey).orderByKey().equalTo(userGroup.getUid());
                 GroupQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                             appleSnapshot.getRef().removeValue();
                             // hapus user pada node user
                             FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                             DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                             Query queryUser = ref.child("User").orderByKey().equalTo(user.getUid());
                             queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                         appleSnapshot.getRef().removeValue();
                                         // hapus tanggal pada node tanggalpribadi
                                         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                         DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                         Query queryUser = ref.child("TanggalPribadi").orderByChild("").equalTo(user.getUid());
                                         queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                 for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                     appleSnapshot.getRef().removeValue();

                                                 }
                                                 Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                                                 FirebaseAuth.getInstance().signOut();
                                                 Intent b = new Intent(getContext(), LoginActivity.class);
                                                 startActivity(b);
                                                 getActivity().finish();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(getContext(), LoginActivity.class);
                startActivity(a);
                getActivity().finish();
                break;
            case R.id.btnOutGroup:
                keluarGroup();

                break;
        }
    }

}

