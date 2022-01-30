package com.example.chimmuniapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChilddetailsrecFragment extends Fragment {

    RecyclerView recyclerr;
    DatabaseReference reff;
    ChildDetailsRecyclerViewAddapter adapter;

    String userid;
    public ChilddetailsrecFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_childdetailsrec, container, false);
        recyclerr = (RecyclerView) v.findViewById(R.id.recycler);

        recyclerr.setLayoutManager(new LinearLayoutManager(getContext()));
        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("Children").child(userid);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(reff, model.class)
                        .build();

        adapter=new ChildDetailsRecyclerViewAddapter(options);
        recyclerr.setAdapter(adapter);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
