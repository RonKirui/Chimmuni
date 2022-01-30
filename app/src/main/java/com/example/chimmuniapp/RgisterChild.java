package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;

public class RgisterChild extends AppCompatActivity {


    EditText childname, mothername, fathername, cbr, ancno;
    Spinner sex;

    TextView childnameerror, sexerror, mothernameerror, fathernameerror,
            cbrerror, ancnoerror, healthcentreerror;

    Spinner spinner;
    Button register;

    String userid;
    ArrayList<String> arrayList = new ArrayList<>();

    DatabaseReference reff,reffhealthcenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_child);

        childname= findViewById(R.id.childname);
        sex = findViewById(R.id.sex);
        mothername = findViewById(R.id.mothername);
        fathername = findViewById(R.id.fathername);
        cbr = findViewById(R.id.cbr);
        ancno = findViewById(R.id.ancno);
        spinner = findViewById(R.id.healthcenter);
        register = findViewById(R.id.register);

        childnameerror = findViewById(R.id.childnameerror);
        sexerror = findViewById(R.id.sexerror);
        mothernameerror = findViewById(R.id.mothernameerror);
        fathernameerror = findViewById(R.id.fathernameerror);
        cbrerror = findViewById(R.id.cbrerror);
        ancnoerror = findViewById(R.id.ancnoerror);
        healthcentreerror = findViewById(R.id.healthcentererror);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(RgisterChild.this,R.array.selectgender, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(monthAdapter);
        reff = FirebaseDatabase.getInstance().getReference();
        reffhealthcenter = FirebaseDatabase.getInstance().getReference("Admins");

        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        spinnerShowData();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //get time and date
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                @SuppressLint("SimpleDateFormat") DateFormat dff = new SimpleDateFormat("yyyy/MM/dd");

                //change date and time to strings
                String date = df.format(calendar.getTime());
                String datef = dff.format(calendar.getTime());


                calendar.add(Calendar.DAY_OF_YEAR, 42);
                Date newDate2 = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 70);
                Date newDate3 = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 98);
                Date newDate4 = calendar.getTime();

                calendar.add(Calendar.MONTH, 6);
                Date newDate5 = calendar.getTime();

                calendar.add(Calendar.MONTH, 9);
                Date newDate6 = calendar.getTime();


                String date2 = df.format(newDate2);
                String date2f = dff.format(newDate2);
                String date3 = df.format(newDate3);
                String date3f = dff.format(newDate3);
                String date4 = df.format(newDate4);
                String date4f = dff.format(newDate4);
                String date5 = df.format(newDate5);
                String date5f = dff.format(newDate5);
                String date6 = df.format(newDate6);
                String date6f = dff.format(newDate6);



                String cname = childname.getText().toString().trim();
                String csex = sex.getSelectedItem().toString().trim();
                String mmothername = mothername.getText().toString().trim();
                String ffathername = fathername.getText().toString().trim();
                String ccbr = cbr.getText().toString().trim();
                String cancno= ancno.getText().toString().trim();
                String hhealthcentre  = spinner.getSelectedItem().toString().trim();

                if (cname.isEmpty()) {
                    childnameerror.setText("Enter valid name");
                    childname.requestFocus();
                }
                else if (csex.equals("Select gender")) {
                    sexerror.setText("Select gender");
                    sex.requestFocus();
                }
                else if (mmothername.isEmpty()) {
                    mothernameerror.setText("Enter mother's valid name");
                    mothername.requestFocus();
                }
                else if (ccbr.isEmpty()) {
                    cbrerror.setText("Enter valid CBR number");
                    cbr.requestFocus();
                }
                else if (cancno.isEmpty()) {
                    ancnoerror.setText("Enter valid ANC number");
                    ancno.requestFocus();
                }
                else if (hhealthcentre.equals("Select Health Center")) {
                    healthcentreerror.setText("Please select Health center");
                    spinner.requestFocus();
                }
                else{
                    RegisterChildHelperClass registerChildHelperClass = new RegisterChildHelperClass(cname,date, csex, mmothername,ffathername, ccbr, cancno, hhealthcentre,userid);

                    ImmunizationHelperClass immunizationHelperClass = new ImmunizationHelperClass(cname, "BCG/OPV/HEP.B", "DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS",
                            "DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS", "DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS", "VIT A", "MEASLES/YELLOW FEVER",
                            date, date2, date3, date4, date5, date6, "pending", "pending", "pending", "pending", "pending", "pending");

                    ImmunizationHelperClass1 immunizationHelperClass1 = new
                            ImmunizationHelperClass1("1",cname,"BCG/OPV/HEP.B",date,"pending",userid);
                    ImmunizationHelperClass1 immunizationHelperClass2 = new
                            ImmunizationHelperClass1("2",cname,"DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS",date2,"pending",userid);
                    ImmunizationHelperClass1 immunizationHelperClass3 = new
                            ImmunizationHelperClass1("3",cname,"DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS",date3,"pending",userid);
                    ImmunizationHelperClass1 immunizationHelperClass4 = new
                            ImmunizationHelperClass1("4",cname,"DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS",date4,"pending",userid);
                    ImmunizationHelperClass1 immunizationHelperClass5 = new
                            ImmunizationHelperClass1("5",cname,"VIT A",date5,"pending",userid);
                    ImmunizationHelperClass1 immunizationHelperClass6 = new
                            ImmunizationHelperClass1("6",cname,"DPT/HIB/OPV/HEP.B/PNEUMOCOCCCAL/ROTAVIRUS",date6,"pending",userid);


                    //ENTER TO THE DATABASE
                    reff.child("AdminChildren").child(hhealthcentre).push().setValue(registerChildHelperClass);
                    reff.child("AdminChildrenSpecific").child(hhealthcentre).child(cname).push().setValue(registerChildHelperClass);
                    reff.child("Children").child(userid).push().setValue(registerChildHelperClass);
                    reff.child("Immunizations").child(userid).push().setValue(immunizationHelperClass);
                    reff.child("ImmunizationsSpecific").child(userid).child(cname).push().setValue(immunizationHelperClass);
                    reff.child("Appointments").child(hhealthcentre).child(datef).push().setValue(immunizationHelperClass1);
                    reff.child("Appointments").child(hhealthcentre).child(date2f).push().setValue(immunizationHelperClass2);
                    reff.child("Appointments").child(hhealthcentre).child(date3f).push().setValue(immunizationHelperClass3);
                    reff.child("Appointments").child(hhealthcentre).child(date4f).push().setValue(immunizationHelperClass4);
                    reff.child("Appointments").child(hhealthcentre).child(date5f).push().setValue(immunizationHelperClass5);
                    reff.child("Appointments").child(hhealthcentre).child(date6f).push().setValue(immunizationHelperClass6);

                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass1);
                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass2);
                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass3);
                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass4);
                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass5);
                    reff.child("AllAppointments").child(hhealthcentre).push().setValue(immunizationHelperClass6);

                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass1);
                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass2);
                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass3);
                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass4);
                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass5);
                    reff.child("AllAppointmentsSpecific").child(hhealthcentre).child(cname).push().setValue(immunizationHelperClass6);

                    reff.child("UserNotifications").child(userid).child(datef).push().setValue(immunizationHelperClass1);
                    reff.child("UserNotifications").child(userid).child(date2f).push().setValue(immunizationHelperClass2);
                    reff.child("UserNotifications").child(userid).child(date3f).push().setValue(immunizationHelperClass3);
                    reff.child("UserNotifications").child(userid).child(date4f).push().setValue(immunizationHelperClass4);
                    reff.child("UserNotifications").child(userid).child(date5f).push().setValue(immunizationHelperClass5);
                    reff.child("UserNotifications").child(userid).child(date6f).push().setValue(immunizationHelperClass6);


                    childname.setText("");
                    sex.setSelected(false);
                    mothername.setText("");
                    fathername.setText("");
                    cbr.setText("");
                    ancno.setText("");
                    register.setText("");

                    Toast.makeText(RgisterChild.this, "Registered Successfully", Toast.LENGTH_LONG).show();


                }


            }
        });

    }

    private void spinnerShowData() {

        reffhealthcenter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot item : snapshot.getChildren()) {
                    arrayList.add(item.child("name").getValue(String.class));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RgisterChild.this, R.layout.spinner, arrayList);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}