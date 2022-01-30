package com.example.chimmuniapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

public class ImmunizationsFragment extends Fragment {


    ListView immunizationListView;

    List<ImmunizationHelperClass> retrimmunList;

    DatabaseReference reff;

    String userid;



    public ImmunizationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_immunizations, container, false);


        retrimmunList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference("Immunizations");

        immunizationListView = view.findViewById(R.id.immunizationListView);

        userid=FirebaseAuth.getInstance().getCurrentUser().
                getUid();



        reff.child(userid).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                    ImmunizationHelperClass immunizationHelperClass = recoDatasnap.getValue(ImmunizationHelperClass.class);
                    retrimmunList.add(immunizationHelperClass);
                }
                ImmunizatonListAdapter immunizatonListAdapter= new ImmunizatonListAdapter((Activity) getContext(),retrimmunList);
                immunizationListView.setAdapter(immunizatonListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}