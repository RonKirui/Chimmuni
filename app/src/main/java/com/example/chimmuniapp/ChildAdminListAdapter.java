package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ChildAdminListAdapter extends ArrayAdapter {

    private Activity context;
    List<RegisterChildHelperClass> retrappList;
    public ChildAdminListAdapter(Activity context, List<RegisterChildHelperClass> retrappList) {
        super(context, R.layout.childadmin_list,retrappList);
        this.context=context;
        this.retrappList=retrappList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.childadmin_list,null,true);

        TextView dob,childname;
        LinearLayout linearLayout = listViewItem.findViewById(R.id.linearLayout);

        dob=listViewItem.findViewById(R.id.childdob);
        childname=listViewItem.findViewById(R.id.childname);

        RegisterChildHelperClass registerChildHelperClass=retrappList.get(position);



        String doobb = registerChildHelperClass.getDob();
        String childnameee = registerChildHelperClass.getChildname();
        String userid = registerChildHelperClass.getUserid();

        dob.setText(doobb);
        childname.setText(childnameee);

        final String doobbfinal = doobb;
        final String childnameeefinal = childnameee;
        final String useridfinal = userid;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),ChicldDetailsAdmin.class);
                intent.putExtra("dobb",doobbfinal);
                intent.putExtra("childnamee",childnameeefinal);
                intent.putExtra("userid",useridfinal);
                getContext().startActivity(intent);
            }
        });

        return listViewItem;

    }

}
