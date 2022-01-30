package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class ChildListAdminFragment extends Fragment {

    private ListView childrenListView;

    private List<RegisterChildHelperClass> retrappList;

    private DatabaseReference reff,reffhealthcentre;


    String userid, healthcentre;

    public ChildListAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_child_list_admin, container, false);

        retrappList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference("AdminChildren");
        reffhealthcentre = FirebaseDatabase.getInstance().getReference("Admins");

        childrenListView = v.findViewById(R.id.childrenListView);


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

                reff.child(healthcentre).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                            RegisterChildHelperClass registerChildHelperClass = recoDatasnap.getValue(RegisterChildHelperClass.class);
                            retrappList.add(registerChildHelperClass);
                        }
                        ChildAdminListAdapter childAdminListAdapter= new ChildAdminListAdapter((Activity) getContext(),retrappList);
                        childrenListView.setAdapter(childAdminListAdapter); }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }
}