package com.example.chimmuniapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class descfragment extends Fragment {
    String childname, dob, sex, mothername, fathername, cbr, ancno, healthcentre;

    public descfragment() {

    }

    public descfragment(String childname, String dob, String sex, String mothername, String fathername, String cbr, String ancno, String healthcentre) {
        this.childname=childname;
        this.dob=dob;
        this.sex=sex;
        this.mothername=mothername;
        this.fathername=fathername;
        this.cbr=cbr;
        this.ancno=ancno;
        this.healthcentre=healthcentre;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_descfragment, container, false);
        TextView childnameholder=view.findViewById(R.id.childnameholder);
        TextView dobholder=view.findViewById(R.id.dobholder);
        TextView sexholder=view.findViewById(R.id.sexholder);
        TextView mothernameholder=view.findViewById(R.id.mothernameholder);
        TextView fathernameholder=view.findViewById(R.id.fathernameholder);
        TextView cbrholder=view.findViewById(R.id.cbrholder);
        TextView ancnoholder=view.findViewById(R.id.ancnoholder);
        TextView healthcenterholder=view.findViewById(R.id.healthcenterholder);

        childnameholder.setText(childname);
        dobholder.setText(dob);
        sexholder.setText(sex);
        mothernameholder.setText(mothername);
        fathernameholder.setText(fathername);
        cbrholder.setText(cbr);
        ancnoholder.setText(ancno);
        healthcenterholder.setText(healthcentre);
        return  view;

    }

    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new ChilddetailsrecFragment()).addToBackStack(null).commit();

    }
}