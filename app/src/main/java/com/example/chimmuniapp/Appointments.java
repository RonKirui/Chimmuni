package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Appointments extends AppCompatActivity {

    private ListView appointmentsListView;

    private List<ImmunizationHelperClass1> retrappList;

    private DatabaseReference reff,reffhealthcentre;
    private FirebaseAuth mAuth;
    String userid, healthcentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        retrappList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference("AllAppointments");
        reffhealthcentre = FirebaseDatabase.getInstance().getReference("Admins");

        appointmentsListView = findViewById(R.id.appointmentsListView);


        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get time and date
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        //change date and time to strings
        String date = df.format(calendar.getTime());

        reffhealthcentre.child(userid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                healthcentre = snapshot.getValue().toString();

                reff.child(healthcentre).orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                            ImmunizationHelperClass1 immunizationHelperClass1 = recoDatasnap.getValue(ImmunizationHelperClass1.class);
                            retrappList.add(immunizationHelperClass1);
                        }
                        AppointmentsListAdapter appointmentsListAdapter= new AppointmentsListAdapter(Appointments.this,retrappList);
                        appointmentsListView.setAdapter(appointmentsListAdapter); }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}