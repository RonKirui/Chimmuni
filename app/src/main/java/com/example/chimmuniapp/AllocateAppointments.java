package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AllocateAppointments extends AppCompatActivity {

    TextInputLayout selectdate;

    EditText date;
    TextView child_content;

    Button send;



    DatabaseReference reff;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_appointments);

        date = findViewById(R.id.date);
        child_content = findViewById(R.id.child_content);
        send = findViewById(R.id.lsend);

        reff = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String datee = extras.getString("date");
        String type = extras.getString("type");
        String userid = extras.getString("userid");
        String recordid = extras.getString("recordid");
        child_content.setText("Enter the date "+name +"'s "+type+" vaccination appointment");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.isEnabled();
                Calendar cal=Calendar.getInstance();
                int yearselected =cal.get(Calendar.YEAR);
                int monthselected =cal.get(Calendar.MONTH);
                int dayselected =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AllocateAppointments.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,yearselected,monthselected,dayselected);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearselected, int monthselected, int dayselected) {
                monthselected = monthselected + 1;

                Log.d("dateSet: dd/mm/yy", +dayselected + "/" + monthselected + "/" + yearselected);
                String dmonth, dday;
                int dyear;

                if (monthselected < 10) {
                    dmonth = "0" + monthselected;
                } else {
                    dmonth = String.valueOf(monthselected);
                }
                if (dayselected < 10) {
                    dday = "0" + dayselected;
                } else {
                    dday = String.valueOf(dayselected);
                }
                dyear = yearselected;

                String dateselected = dyear+ "/" + dmonth + "/" + dday ;
                date.setText(dateselected);
            }
        };

        final String useridfinal = userid;
        final String typefinal = type;
        final String namefinal = name;
        final String datefinal = datee;
        final String recordidfinal = recordid;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ImmunizationHelperClass1 immunizationHelperClass1 = new ImmunizationHelperClass1(recordid,namefinal, typefinal, datefinal, "pending", useridfinal);
                reff.child("UserNotifications").child(useridfinal).child(date.getText().toString().trim()).push().setValue(immunizationHelperClass1);
                reff.child("Admins").child(adminid).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String healthcr = snapshot.getValue().toString();

                        reff.child("AllAppointments").child(healthcr).push().setValue(immunizationHelperClass1);
                        reff.child("Appointments").child(healthcr).child(date.getText().toString().trim()).push().setValue(immunizationHelperClass1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}