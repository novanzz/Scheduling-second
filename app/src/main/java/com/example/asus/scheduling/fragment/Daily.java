package com.example.asus.scheduling.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.Tanggal;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.activity.ActivityUser;
import com.example.asus.scheduling.activity.JoinEvent;
import com.example.asus.scheduling.tanggalViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Daily extends Fragment {

    private FloatingActionButton fab;

    //coba
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder> adapter;
    public String  GrpId;
    public String date, grpKey;
    private TextView note;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_daily, container, false);


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        JoinGroupRecyclerview = (RecyclerView) rootView.findViewById(R.id.user_list);
        JoinGroupRecyclerview.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(getContext());
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        JoinGroupRecyclerview.setLayoutManager(lin);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Apakah anda ingin melihat jadwal pribadi anda")
                        .setCancelable(false).setPositiveButton("Ya",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent a = new Intent(getContext(), ActivityUser.class);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
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



        /**Intent intent = getActivity().getIntent();
         final String key = intent.getStringExtra("tglKey");
         final String tanggal = intent.getStringExtra("tanggal");*/



        //coba






        //Intent i = new Intent (getActivity(), AddEventPribadi.class);
        //startActivity(i);
        // }
        //});*/
        return rootView;

    }


    private void setupAdapter() {

        //adapter untuk menampilkan data yang berada di child group
        // adapter = new FirebaseIndexRecyclerAdapter<Tanggal, tanggalViewHolder>(Tanggal.class,R.layout.fragment_calendar,tanggalViewHolder.class,key,data) {

        adapter = new FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder>(
                Tanggal.class,
                R.layout.fragment_daily,
                tanggalViewHolder.class,
                mDatabaseRef.child("TanggalGroup").child(GrpId)
                //mDatabaseRef.child("TanggalPribadi").orderByChild("date").equalTo(date)
        ) {
            @Override
            protected void populateViewHolder(tanggalViewHolder viewHolder, Tanggal model, int position) {
                viewHolder.txtTime.setText(model.getTime());
                viewHolder.txtName.setText(model.getName());
                viewHolder.txtDate.setText(model.getDate());
                viewHolder.note.setText(model.getNote());
                Glide.with(getContext()).load(model.getPhotoUrl()).into(viewHolder.photoUrl);

                //mendapatkan key pada posisi sekarang
                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Anda ingin melihat yang ikut dalam event ini?")
                                .setCancelable(false).setPositiveButton("Ya",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                Intent i = new Intent(getActivity(), JoinEvent.class);
                                i.putExtra("key", postKey);
                                startActivity(i);

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
                viewHolder.btnJoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    User user1 = dataSnapshot.getValue(User.class);
                                    grpKey = user1.getGroupID();
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //coba
                                    mDatabaseRef.child("TanggalGroup").child(grpKey).child(postKey)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    Tanggal user1 = dataSnapshot.getValue(Tanggal.class);
                                                    date = user1.getDate();
                                                    String time = user1.getTime();
                                                    String note = user1.getNote();
                                                    String photoUrl = user1.getPhotoUrl();
                                                    String idTanggal = user1.getIdTanggal();
                                                    String name = user1.getName();

                                                    Tanggal tglUser = new Tanggal(date,time,note,photoUrl,name,idTanggal,user.getUid());


                                                    Map<String, Object> update = new HashMap<>();
                                                    update.put("/JoinEvent/"+grpKey+"/"+postKey+"/"+user.getUid(),true);
                                                    update.put("/TanggalPribadi/"+grpKey+"/"+postKey+"/",tglUser);
                                                    //update.put("/TanggalPribadi/"+grpKey+"/"+postKey+"/"+);
                                                    // update data yang ada bila telah selesai akan pindah act
                                                    mDatabaseRef.updateChildren(update, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                            Intent i = new Intent(getActivity(), JoinEvent.class);
                                                            i.putExtra("key", postKey);
                                                            startActivity(i);



                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        //coba





                                    /*Map<String, Object> update = new HashMap<>();
                                     update.put("/JoinEvent/"+grpKey+"/"+postKey+"/"+user.getUid(),true);
                                     //update.put("/TanggalPribadi/"+grpKey+"/"+postKey+"/"+);
                                    // update data yang ada bila telah selesai akan pindah act
                                    mDatabaseRef.updateChildren(update, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            Intent i = new Intent(getActivity(), JoinEvent.class);
                                            i.putExtra("key", postKey);
                                            startActivity(i);



                                        }
                                    });*/


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                    }


                });

            }
        };



    }

}
