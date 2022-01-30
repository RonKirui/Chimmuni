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


public class HomeFragment extends Fragment {


    ListView notificationListView;

    List<ImmunizationHelperClass1> retrnotList;

    DatabaseReference reff;


    private CardView registerchild;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //get time and date
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //change date and time to strings
        String date = df.format(calendar.getTime());


        retrnotList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference("UserNotifications");

        notificationListView = view.findViewById(R.id.notificationListView);



        reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot recoDatasnap: snapshot.getChildren()){
                    ImmunizationHelperClass1 notificationHelperClass = recoDatasnap.getValue(ImmunizationHelperClass1.class);
                    retrnotList.add(notificationHelperClass);
                }
                if ((Activity) getContext()!=null){
                    NotificationListAdapter notificationListAdapter= new NotificationListAdapter((Activity) getContext(),retrnotList);
                    notificationListView.setAdapter(notificationListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        registerchild = view.findViewById(R.id.registerchild);
        registerchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RgisterChild.class));
            }
        });
        return view;
    }
}