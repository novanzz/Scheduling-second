package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.MainActivity;
import com.example.asus.scheduling.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountAct extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button mBtnLogout,mbtnOutGroup;
    private GoogleApiClient mGoogleApiClient;
    private ImageView mFotoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mBtnLogout = (Button) findViewById(R.id.btnLogout);
        mbtnOutGroup = (Button) findViewById(R.id.btnOutGroup);
        mBtnLogout.setOnClickListener(this);
        mbtnOutGroup.setOnClickListener(this);

        mFotoUser = (ImageView) findViewById(R.id.fotouser);

        //Congfigure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(AccountAct.this.getApplicationContext())
                .enableAutoManage(AccountAct.this, new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(AccountAct.this, "Error Cuy", Toast.LENGTH_LONG).show();
                }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //isi dari button sama text bentuk emaill
        TextView mTextUser = (TextView) findViewById(R.id.textEmail);
        TextView mTextUsername = (TextView) findViewById(R.id.textUserName);


        // cek user ada apa engga di firebase supaya  klo dah login lu ga ush balik lagi ke login
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(AccountAct.this, LoginActivity.class);
            startActivity(i);
            finish();
        }else {
            mTextUser.setText(user.getEmail());
            mTextUsername.setText(user.getDisplayName());
            Glide.with(this)
                    .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                    .into(mFotoUser);
        }


    }


    public void keluarGroup(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        // Attach a listener to read the data at our posts reference
        ref.child("GroupUser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String postkey = postSnapshot.getKey();
                    //hapus user pada groupuser
                    FirebaseUser userGroup = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference refrensi = FirebaseDatabase.getInstance().getReference();
                    Query GroupQuery = refrensi.child("GroupUser").child(postkey).orderByKey().equalTo(userGroup.getUid());
                    GroupQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                // hapus tanggal pada node tanggalpribadi
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query queryUser = ref.child("TanggalPribadi").child(postkey).orderByChild("userId").equalTo(user.getUid());
                                queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //buat ancurin semua fragment yang ada di adapter
    public void onCreate() {
        MainActivity.fa.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(AccountAct.this, LoginActivity.class);
                startActivity(a);
                finish();
                onCreate();
                break;
            case R.id.btnOutGroup:
                keluarGroup();
                // hapus user pada node user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query queryUser = ref.child("User").orderByKey().equalTo(user.getUid());
                queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                            FirebaseAuth.getInstance().signOut();
                            Intent b = new Intent(AccountAct.this, LoginActivity.class);
                            startActivity(b);
                            finish();
                            onCreate();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;
        }
    }

}

