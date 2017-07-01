package com.example.asus.scheduling.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.Tanggal;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.tanggalViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityUser extends AppCompatActivity {


    //coba
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder> adapter;
    public String  GrpId,date,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);


        JoinGroupRecyclerview = (RecyclerView) findViewById(R.id.user_list);
        JoinGroupRecyclerview.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(ActivityUser.this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        JoinGroupRecyclerview.setLayoutManager(lin);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User user1 = dataSnapshot.getValue(User.class);
                    GrpId = user1.getGroupID();
                    setupAdapter();
                    JoinGroupRecyclerview.setAdapter(adapter);

                    //Toast.makeText(getContext(),tanggal,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                           /* final FirebaseUser user = mAuth.getCurrentUser();
                            mDatabaseRef.child("IdTanggal").child(user.getUid()).child(GrpId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue()!=null){
                                        IdTanggal tgl1 = dataSnapshot.getValue(IdTanggal.class);
                                        idtgl1 = tgl1.getIdTgl();

                                        //keyRef = mDatabaseRef.child("Tanggal").child(GrpId);



                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/



                    // / setupAdapter(keyRef, dataRef);
                    //JoinGroupRecyclerview.setAdapter(adapter);

                    //ngambil atribut dri model Tanggal
                                /*mDatabaseRef.child("TanggalPribadi").child(GrpId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue()!=null){
                                        Tanggal user1 = dataSnapshot.getValue(Tanggal.class);
                                        final String tanggal = user1.getDate();
                                        if (date==tanggal){
                                            Toast.makeText(getContext(),tanggal,Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getContext(),date,Toast.LENGTH_SHORT).show();

                                        }

                                       mDatabaseRef.child("TanggalPribadi").child(GrpId).child(idtgl).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getValue()!=null){
                                                    Tanggal user1 = dataSnapshot.getValue(Tanggal.class);
                                                    tanggal = user1.getTime();
                                                    Toast.makeText(getContext(),tanggal,Toast.LENGTH_SHORT).show();

                                                   // if (tanggal == date){
                                                     //   Toast.makeText(getContext(),"sudah benar",Toast.LENGTH_SHORT).show();
                                                    //}

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
                            });*/





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        /**Intent intent = getActivity().getIntent();
         final String key = intent.getStringExtra("tglKey");
         final String tanggal = intent.getStringExtra("tanggal");*/



        //coba






        //Intent i = new Intent (getActivity(), AddEventPribadi.class);
        //startActivity(i);
        // }
        //});*/


    }


    private void setupAdapter() {

        //adapter untuk menampilkan data yang berada di child group
        // adapter = new FirebaseIndexRecyclerAdapter<Tanggal, tanggalViewHolder>(Tanggal.class,R.layout.fragment_calendar,tanggalViewHolder.class,key,data) {

        adapter = new FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder>(
                Tanggal.class,
                R.layout.fragment_daily,
                tanggalViewHolder.class,
                mDatabaseRef.child("TanggalPribadi").child(GrpId).orderByChild("userId").equalTo(userId)
                //mDatabaseRef.child("TanggalPribadi").orderByChild("date").equalTo(date)
        ) {
            @Override
            protected void populateViewHolder(tanggalViewHolder viewHolder, Tanggal model, int position) {
                viewHolder.txtDate.setText(model.getDate());
                viewHolder.txtName.setText(model.getName());
                Glide.with(ActivityUser.this).load(model.getPhotoUrl()).into(viewHolder.photoUrl);
                final DatabaseReference postRef = getRef(position);
                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUser.this);
                        builder.setMessage("Apakah anda ingin menghapus jadwal ini ?")
                                .setCancelable(false).setPositiveButton("Ya",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                // Attach a listener to read the data at our posts reference
                                ref.child("GroupUser").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            final String postkey = postSnapshot.getKey();

                                            // Set click listener for the whole post view
                                            final String key = postRef.getKey();
                                            // hapus tanggal pada node tanggalpribadi masih belum bisa
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                            Query queryUser = ref.child("TanggalPribadi").child(postkey).child(key);
                                            queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                        Toast.makeText(ActivityUser.this,"masuk",Toast.LENGTH_SHORT).show();
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
                        })
                                .setNegativeButton("Tidak",new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        dialog.dismiss();
                                    }
                                })
                                // Pencet Back dan message hancur
                                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
                                            dialog.dismiss();
                                        }
                                        return false;
                                    }
                                }).show();

                    }
                });

            }
        };


    }
}
