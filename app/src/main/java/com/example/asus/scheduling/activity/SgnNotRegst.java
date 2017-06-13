package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.scheduling.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SgnNotRegst extends AppCompatActivity implements View.OnClickListener{
    private Button Out;
    private Button mkGroup;
    private Button jnGroup;
// buat realtime db
    private DatabaseReference mFirebaseDatabase,userRef;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgn_not_regst);

        Out = (Button) findViewById(R.id.Out);
        mkGroup = (Button) findViewById(R.id.mkGroup);
        jnGroup = (Button) findViewById(R.id.jnGroup);

        Out.setOnClickListener(this);
        mkGroup.setOnClickListener(this);
        jnGroup.setOnClickListener(this);

        //Congfigure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(SgnNotRegst.this.getApplicationContext())
                .enableAutoManage(SgnNotRegst.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SgnNotRegst.this, "Error Cuy", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }
    private void out(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        FirebaseAuth.getInstance().signOut();
        Intent a = new Intent(SgnNotRegst.this, LoginActivity.class);
        startActivity(a);
        finish();
    }
    private void MkGroup(){
        startActivity(new Intent(SgnNotRegst.this,MakeGroup.class));
    }

    private void JnGroup(){

                startActivity(new Intent(SgnNotRegst.this,JoinGrupActivity.class));
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.Out:
                    out();
                    break;
                case  R.id.mkGroup:
                    MkGroup();
                    break;
                case R.id.jnGroup:
                    JnGroup();
                    break;

            }
    }
}
