package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChicldDetailsAdmin extends AppCompatActivity {

    private TextView nametitle,childnameholder,dobholder,sexholder,mothernameholder,fathernameholder,cbrholder,ancnoholder,healthcenterholder;

    private DatabaseReference reff,reffhealthcentre;

    private List <RegisterChildHelperClass> list;
    private ListView appointmentsListView;

    private List<ImmunizationHelperClass1> retrappList;
    private FirebaseAuth mAuth;
    String userid, healthcentre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicld_details_admin);

        nametitle = findViewById(R.id.childnametitle);
        childnameholder = findViewById(R.id.childnameholder);
        dobholder = findViewById(R.id.dobholder);
        sexholder = findViewById(R.id.sexholder);
        mothernameholder = findViewById(R.id.mothernameholder);
        fathernameholder = findViewById(R.id.fathernameholder);
        cbrholder = findViewById(R.id.cbrholder);
        ancnoholder = findViewById(R.id.ancnoholder);
        healthcenterholder = findViewById(R.id.healthcenterholder);

        reff = FirebaseDatabase.getInstance().getReference();
        reffhealthcentre = FirebaseDatabase.getInstance().getReference("Admins");
        retrappList = new ArrayList<>();
        appointmentsListView = findViewById(R.id.appointmentsListView);


        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        reffhealthcentre.child(userid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bundle extras = getIntent().getExtras();
                String dobb = extras.getString("dobb");
                String namee = extras.getString("childnamee");
                String userid = extras.getString("userid");

                String healthcentr = snapshot.getValue().toString();
                nametitle.setText(namee);
                dobholder.setText(dobb);
                childnameholder.setText(namee);
                healthcenterholder.setText(healthcentr);
                reff.child("AdminChildrenSpecific").child(healthcentr).child(namee).orderByChild("userid").equalTo(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            RegisterChildHelperClass registerChildHelperClass = dataSnapshot.getValue(RegisterChildHelperClass.class);

                            String mother = registerChildHelperClass.getMothername();
                            sexholder.setText(registerChildHelperClass.getSex());
                            mothernameholder.setText(mother);
                            fathernameholder.setText(registerChildHelperClass.getFathername());
                            cbrholder.setText(registerChildHelperClass.getCbr());
                            ancnoholder.setText(registerChildHelperClass.getAncno());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                reff.child("AllAppointmentsSpecific").child(healthcentr).child(namee).orderByChild("userid").equalTo(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                            ImmunizationHelperClass1 immunizationHelperClass1 = recoDatasnap.getValue(ImmunizationHelperClass1.class);
                            retrappList.add(immunizationHelperClass1);
                        }
                        AppointmentsListAdapter appointmentsListAdapter= new AppointmentsListAdapter(ChicldDetailsAdmin.this,retrappList);
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