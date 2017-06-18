package com.example.asus.scheduling.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asus.scheduling.Model.IdTanggal;
import com.example.asus.scheduling.Model.Tanggal;
import com.example.asus.scheduling.Model.User;
import com.example.asus.scheduling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEventPribadi extends AppCompatActivity {

    private CalendarView mCalenderView;
    private TextView mDisplayDate,txtTime,txtNote;
    private Calendar dateTime = Calendar.getInstance();
    private FloatingActionButton fab;
    private DatabaseReference mDatabase, mDatabaseRef;
    private FirebaseAuth mAuth;

    String GroupId,PhotoUrl,Name,uploadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_pribadi);
        mCalenderView = (CalendarView) findViewById(R.id.calenderview);
        mDisplayDate = (TextView) findViewById(R.id.date);
        txtTime = (TextView) findViewById(R.id.time);
        txtNote = (TextView) findViewById(R.id.note);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       final String jam = txtTime.getText().toString();
                                       final String tanggal = mDisplayDate.getText().toString();
                                       final String note = txtNote.getText().toString();

                                       // event yang terjadi ketika di klik
                                       FirebaseUser user = mAuth.getCurrentUser();
                                       mDatabaseRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               if (dataSnapshot.getValue() != null) {
                                                   FirebaseUser user = mAuth.getCurrentUser();

                                                   User user1 = dataSnapshot.getValue(User.class);
                                                   GroupId = user1.getGroupID();
                                                   PhotoUrl= user1.getPhotoUrl();
                                                   Name = user1.getName();
                                                   // isi dari node tanggal
                                                   uploadId = mDatabase.push().getKey();
                                                   Tanggal tglUser = new Tanggal(tanggal,jam,note,GroupId,PhotoUrl
                                                           ,Name,uploadId);

                                                   IdTanggal idTgl = new IdTanggal(uploadId);

                                                   Map<String,Object> update = new HashMap<>();
                                                   update.put("/TanggalPribadi/"+GroupId+"/"+uploadId,tglUser);
                                                   update.put("/IdTanggal/"+user.getUid()+"/"+GroupId,idTgl);

                                                   // update data yang ada bila telah selesai akan pindah act
                                                   mDatabase.updateChildren(update, new DatabaseReference.CompletionListener() {
                                                       @Override
                                                       public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                           Toast.makeText(AddEventPribadi.this,"Success",Toast.LENGTH_SHORT).show();
                                                       }
                                                   });

                                                  // mDatabase.child("TanggalPribadi").child(GroupId).setValue(tglUser);
                                                   //Toast.makeText(AddEventPribadi.this,"Success",Toast.LENGTH_SHORT).show();

                                               }

                                           }
                                           @Override
                                               public void onCancelled(DatabaseError databaseError) {

                                               }
                                           });





                                   }
                               });

        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               final String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                //Intent i = new Intent(CalenderActivity.this, MainActivity.class);
                //i.putExtra("date", date);
                updateTime();
                mDisplayDate.setText(date);
            }
        });

    }
    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            final String time = hourOfDay + ":" + minute+" WIB";
            // Intent i = new Intent(CalenderActivity.this, MainActivity.class);
            // i.putExtra("time", time);
            txtTime.setText(time);
        }
    };

    }

