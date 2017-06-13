package com.example.asus.scheduling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.activity.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button mBtnLogout;
    private GoogleApiClient mGoogleApiClient;
    private ImageView mFotoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_account, container, false);

        mFotoUser = (ImageView) rootView.findViewById(R.id.fotouser);

        //Congfigure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getContext(), "Error Cuy", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //isi dari button sama text bentuk emaill
        TextView mTextUser = (TextView) rootView.findViewById(R.id.textEmail);
        TextView mTextUsername = (TextView) rootView.findViewById(R.id.textUserName);


        // cek user ada apa engga di firebase supaya  klo dah login lu ga ush balik lagi ke login
        mAuth = FirebaseAuth.getInstance();
        mBtnLogout = (Button) rootView.findViewById(R.id.btnLogout);
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
        //button lakuin aksi di akfity ini
        mBtnLogout.setOnClickListener(this);
        // set text supaya dia nge get nama dari firebase

        return rootView;
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
            case R.id.btnLeftGrp:
                break;
        }
    }


}

