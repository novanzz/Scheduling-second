package com.example.asus.scheduling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.asus.scheduling.MainActivity;
import com.example.asus.scheduling.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton mGoogleBtn;
    // ngasih id buat sign google
    private static final int RC_SIGN_IN = 1;
    // make apiClient
    private GoogleApiClient mGoogleApiClient;
    //make fbauth
    private FirebaseAuth mAuth;

    private static final String TAG ="LoginAct";

    // Write a message to the database
    private DatabaseReference mFirebaseDatabase, userRef;
    private FirebaseDatabase mFirebaseInstance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisasi mendapatkan auth dari firebase
        mAuth = FirebaseAuth.getInstance();
        //dia nerima klo sign in/out, klo misal sign in dia lari ke login act

        // definisi button google signin
        mGoogleBtn = (SignInButton)findViewById(R.id.sign_in_button);

        //Congfigure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // buat objek apiclient google klo lu mau pake api yang lain tinggal add
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

//nyoba read and write Realtime DB
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference();
        // ni tuh buat ngasih jalan ke realtime db
        userRef= mFirebaseDatabase;
// samapai sini

        }

    public String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    // klo mau mulai dia balikin authlistener sama connect
    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    private void SignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // cek kamar dia tuh berasalh dari mana trus nanti dia bawa hasil klo dia klo dia di posisi auth google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            //disini dia minta user yang sedang login saat itu
                            final FirebaseUser user = mAuth.getCurrentUser();
                            //trus dengan node childe Group lalu sekali narik data
                            mFirebaseDatabase.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                //method data berubah
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue()!= null){
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                    }else{
                                        startActivity(new Intent(getApplicationContext(),SgnNotRegst.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }else {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }


            @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
