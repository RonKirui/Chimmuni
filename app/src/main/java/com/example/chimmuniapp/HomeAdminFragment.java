package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class HomeAdminFragment extends Fragment {


    ListView notificationListView;

    List<ImmunizationHelperClass1> retrnotList;

    DatabaseReference reff;

    CardView checkappointments;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_admin, container, false);

        //get time and date
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //change date and time to strings
        String date = df.format(calendar.getTime());

        retrnotList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference();

        notificationListView = v.findViewById(R.id.notificationListView);

        reff.child("Admins").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String healthcentre = snapshot.getValue().toString();
                reff.child("Appointments").child(healthcentre).child(date).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                            ImmunizationHelperClass1 notificationHelperClass = recoDatasnap.getValue(ImmunizationHelperClass1.class);
                            retrnotList.add(notificationHelperClass);
                        }
                        if ((Activity)getActivity()!=null){
                            NotificationAdminListAdapter notificationAdminListAdapter =
                                    new NotificationAdminListAdapter((Activity)getActivity(),retrnotList);
                            notificationListView.setAdapter(notificationAdminListAdapter);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        checkappointments = v.findViewById(R.id.chechappointments);

        checkappointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), Appointments.class));
            }
        });

        return v;
    }

}