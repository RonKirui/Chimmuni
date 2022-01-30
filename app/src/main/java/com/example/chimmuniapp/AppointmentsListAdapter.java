package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentsListAdapter extends ArrayAdapter {

    private Activity context;
    List<ImmunizationHelperClass1> retrappList;
    public AppointmentsListAdapter(Activity context, List<ImmunizationHelperClass1> retrappList) {
        super(context, R.layout.appointments_list,retrappList);
        this.context=context;
        this.retrappList=retrappList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.appointments_list, null, true);


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
        TextView date, childname, vaccination;


        ImageView statusdone, reallocate;

        date = listViewItem.findViewById(R.id.appointmentdate);
        childname = listViewItem.findViewById(R.id.appointmentchildname);
        vaccination = listViewItem.findViewById(R.id.appointmentvaccination);

        CheckBox statusbox = listViewItem.findViewById(R.id.appointmentstatus);

        statusdone = listViewItem.findViewById(R.id.appointmentdone);
        reallocate = listViewItem.findViewById(R.id.appoinmentreallocate);


        ImmunizationHelperClass1 immunizationHelperClass1 = retrappList.get(position);


        String datee = immunizationHelperClass1.getDate();
        String name = immunizationHelperClass1.getName();
        String type = immunizationHelperClass1.getType();
        String recordid = immunizationHelperClass1.getRecordid();
        String userid = immunizationHelperClass1.getUserid();

        date.setText(datee);
        childname.setText(name);
        vaccination.setText(type);

        final CheckBox statusfinal = statusbox;
        final String namefinal = name;
        final String dateefinal = datee;

        final ImageView statusdonefinal = statusdone;

        //for checking the status

        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        Date fdate = null;
        try {
            fdate = dt.parse(dateefinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy/MM/dd");
        String pathdate = dt1.format(fdate);
        final String pathdatefinal = pathdate;


        final ImageView reallocatefinal = reallocate;

        String adminid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reff.child("Admins").child(adminid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String healthcr = snapshot.getValue().toString();
                reff.child("Appointments").child(healthcr).child(pathdate).orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot namesnapshot : snapshot.getChildren()) {
                            String namekey = namesnapshot.getKey();
                            final String namekeyfinal = namekey;

                            reff.child("Appointments").child(healthcr).child(pathdatefinal).child(namekeyfinal).child("status").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String statuss = snapshot.getValue().toString();

                                    if (statuss.equals("done")) {
                                        statusbox.setChecked(true);
                                        statusbox.setEnabled(false);
                                    } else {

                                        final String typefinal = type;
                                        final String useridfinal = userid;
                                        final String datefinal = datee;

                                        statusdonefinal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

                                                try {
                                                    Date fdate = dt.parse(dateefinal);
                                                    SimpleDateFormat dt1 = new SimpleDateFormat("yyyy/MM/dd");
                                                    String pathdate = dt1.format(fdate);
                                                    reff.child("Appointments").child(healthcr).child(pathdate).orderByChild("name").equalTo(namefinal).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot namesnapshot : snapshot.getChildren()) {
                                                                String namekey = namesnapshot.getKey();

                                                                final String namekeyfinal = namekey;
                                                                final String pathdatefinal = pathdate;
                                                                new AlertDialog.Builder(getContext())
                                                                        .setTitle("Corfirmation message")
                                                                        .setMessage("You are about to sign "+namefinal+"'s appointment as done. If you continue, the action can never be undone.")
                                                                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                        reff.child("Appointments").child(healthcr).child(pathdatefinal).child(namekeyfinal).child("status").setValue("done");


                                                                                        switch (recordid){
                                                                                            case "1":
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date1").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status1").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                                break;
                                                                                            case "2":
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date2").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status2").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                                break;
                                                                                            case "3":
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date3").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status3").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                                break;
                                                                                            case "4":
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date4").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status4").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                                break;
                                                                                            case "5":
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date5").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status5").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                                break;
                                                                                            default:
                                                                                                reff.child("ImmunizationsSpecific").child(userid).child(namefinal).orderByChild("date6").equalTo(datee).addValueEventListener(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                                                                            String status1key = dataSnapshot.getKey();
                                                                                                            reff.child("ImmunizationsSpecific").child(userid).child(namefinal).child(status1key).child("status6").setValue("done");                            }

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                                    }
                                                                                                });
                                                                                        }
                                                                                        Toast.makeText(getContext(), namefinal + "'s vaccination done", Toast.LENGTH_LONG).show();
                                                                                        statusfinal.setChecked(true);
                                                                                        statusfinal.setEnabled(false);
                                                                                    }
                                                                                }
                                                                        )
                                                                        .setNegativeButton("Cancel",null)
                                                                        .show();

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });



                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                        reallocatefinal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intent = new Intent(getContext(), AllocateAppointments.class);

                                                intent.putExtra("date", datefinal);
                                                intent.putExtra("name", namefinal);
                                                intent.putExtra("type", typefinal);
                                                intent.putExtra("userid", useridfinal);
                                                intent.putExtra("recordid", recordid);
                                                getContext().startActivity(intent);
                                            }
                                        });
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return listViewItem;

    }
    
}
