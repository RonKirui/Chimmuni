package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ImmunizatonListAdapter extends ArrayAdapter {
    private Activity context;
    List<ImmunizationHelperClass> retrimmunList;
    public ImmunizatonListAdapter(Activity context, List<ImmunizationHelperClass> retrimmunList) {
        super(context, R.layout.immunization_list,retrimmunList);
        this.context=context;
        this.retrimmunList=retrimmunList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.immunization_list,null,true);


        TextView name, date1, date2, date3, date4, date5, date6;
        CheckBox status1,status2,status3,status4,status5,status6;

        name=listViewItem.findViewById(R.id.name);
        date1=listViewItem.findViewById(R.id.date1);
        date2=listViewItem.findViewById(R.id.date2);
        date3=listViewItem.findViewById(R.id.date3);
        date4=listViewItem.findViewById(R.id.date4);
        date5=listViewItem.findViewById(R.id.date5);
        date6=listViewItem.findViewById(R.id.date6);
        status1=listViewItem.findViewById(R.id.status1);
        status2=listViewItem.findViewById(R.id.status2);
        status3=listViewItem.findViewById(R.id.status3);
        status4=listViewItem.findViewById(R.id.status4);
        status5=listViewItem.findViewById(R.id.status5);
        status6=listViewItem.findViewById(R.id.status6);

        ImmunizationHelperClass immunizationHelperClass=retrimmunList.get(position);

        String nameee = immunizationHelperClass.getName();
        String datee1 = immunizationHelperClass.getDate1();
        String datee2 = immunizationHelperClass.getDate2();
        String datee3 = immunizationHelperClass.getDate3();
        String datee4 = immunizationHelperClass.getDate4();
        String datee5 = immunizationHelperClass.getDate5();
        String datee6 = immunizationHelperClass.getDate6();
        name.setText(nameee);
        date1.setText(datee1);
        date2.setText(datee2);
        date3.setText(datee3);
        date4.setText(datee4);
        date5.setText(datee5);
        date6.setText(datee6);

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final  String useridfinal = userid;
        final String nameefinal = nameee;
        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date1").equalTo(datee1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();
                    reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String statuss1 = snapshot.getValue().toString();
                            if (statuss1.equals("done")){
                                status1.setChecked(true);
                                status1.setEnabled(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }

        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date2").equalTo(datee2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();

                        reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String statuss1 = snapshot.getValue().toString();
                                if (statuss1.equals("done")){
                                    status2.setChecked(true);
                                    status2.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }
        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date3").equalTo(datee3).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();

                        reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String statuss1 = snapshot.getValue().toString();
                                if (statuss1.equals("done")){
                                    status3.setChecked(true);
                                    status3.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }

        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date4").equalTo(datee4).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();

                        reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String statuss1 = snapshot.getValue().toString();
                                if (statuss1.equals("done")){
                                    status4.setChecked(true);
                                    status4.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }

        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date5").equalTo(datee5).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();

                        reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status5").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String statuss1 = snapshot.getValue().toString();
                                if (statuss1.equals("done")){
                                    status5.setChecked(true);
                                    status5.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }
        try {
            reff.child("ImmunizationsSpecific").child(userid).child(nameee).orderByChild("date6").equalTo(datee6).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();

                        reff.child("ImmunizationsSpecific").child(useridfinal).child(nameefinal).child(key).child("status6").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String statuss1 = snapshot.getValue().toString();
                                if (statuss1.equals("done")){
                                    status6.setChecked(true);
                                    status6.setEnabled(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }



        return listViewItem;

    }


}
