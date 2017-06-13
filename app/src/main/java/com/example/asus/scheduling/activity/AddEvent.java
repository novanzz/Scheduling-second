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

import com.example.asus.scheduling.R;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    private CalendarView mCalenderView;
    private TextView mDisplayDate,txtTime;
    private Calendar dateTime = Calendar.getInstance();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        mCalenderView = (CalendarView) findViewById(R.id.calenderview);
        mDisplayDate = (TextView) findViewById(R.id.date);
        txtTime = (TextView) findViewById(R.id.time);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                     // event yang terjadi ketika di klik
                                       Toast.makeText(AddEvent.this,"Success",Toast.LENGTH_SHORT).show();
                                   }
                               });

        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
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
            String time = hourOfDay + ":" + minute+" WIB";
            // Intent i = new Intent(CalenderActivity.this, MainActivity.class);
            // i.putExtra("time", time);
            txtTime.setText(time);
        }
    };

    }

