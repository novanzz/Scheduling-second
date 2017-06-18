package com.example.asus.scheduling.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.scheduling.Model.Tanggal;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.example.asus.scheduling.activity.AddEventGrup;
import com.example.asus.scheduling.activity.AddEventPribadi;
import com.example.asus.scheduling.tanggalViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Calendar extends Fragment {

    private FloatingActionButton fab;
    //coba
    private RecyclerView JoinGroupRecyclerview;
    private DatabaseReference mDatabaseRef,keyRef,dataRef;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder> adapter;
    String  GrpId;
    private CalendarView mCalenderView;
    public String idtgl1,date;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_calendar, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        mCalenderView = (CalendarView) rootView.findViewById(R.id.calenderview);


        JoinGroupRecyclerview = (RecyclerView) rootView.findViewById(R.id.user_list);
        JoinGroupRecyclerview.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(getContext());
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        JoinGroupRecyclerview.setLayoutManager(lin);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Update Jadwal Grup atau Pribadi")
                        .setCancelable(false).setPositiveButton("Grup",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent Grup = new Intent(getContext(), AddEventGrup.class);
                        Grup.addCategory(Intent.CATEGORY_HOME);
                        Grup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Grup);
                    }
                })
                        .setNegativeButton("Pribadi",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {

                                Intent Pribadi = new Intent(getContext(), AddEventPribadi.class);
                                Pribadi.addCategory(Intent.CATEGORY_HOME);
                                Pribadi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Pribadi);

                            }
                        }).show();

            }
        });

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User user1 = dataSnapshot.getValue(User.class);
                    GrpId = user1.getGroupID();

                        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                             date = (month + 1) + "/" + dayOfMonth + "/" + year;
                            setupAdapter();
                            JoinGroupRecyclerview.setAdapter(adapter);

                            Toast.makeText(getContext(),date,Toast.LENGTH_SHORT).show();
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


                    });




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
        return rootView;

    }

    private void setupAdapter() {

        //adapter untuk menampilkan data yang berada di child group
       // adapter = new FirebaseIndexRecyclerAdapter<Tanggal, tanggalViewHolder>(Tanggal.class,R.layout.fragment_calendar,tanggalViewHolder.class,key,data) {

        adapter = new FirebaseRecyclerAdapter<Tanggal, tanggalViewHolder>(
                Tanggal.class,
                R.layout.fragment_calendar,
                tanggalViewHolder.class,
                mDatabaseRef.child("TanggalPribadi").child(GrpId).orderByChild("date").equalTo(date)
                //mDatabaseRef.child("TanggalPribadi").orderByChild("date").equalTo(date)
        ) {
            @Override
            protected void populateViewHolder(tanggalViewHolder viewHolder, Tanggal model, int position) {
                viewHolder.txtDate.setText(model.getDate());
                viewHolder.txtName.setText(model.getName());
                Glide.with(getContext()).load(model.getPhotoUrl()).into(viewHolder.photoUrl);
            }
        };



    }

}
