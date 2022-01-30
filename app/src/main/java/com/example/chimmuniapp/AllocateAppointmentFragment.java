package com.example.chimmuniapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class AllocateAppointmentFragment extends DialogFragment {

    TextInputLayout selectdate;

    TextView date;

    Button send;



    DatabaseReference reff;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    public AllocateAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allocate_appointment, container, false);

        selectdate = view.findViewById(R.id.selectdate);
        date = view.findViewById(R.id.date);
        send = view.findViewById(R.id.lsend);

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectdate.isEnabled();
                Calendar cal=Calendar.getInstance();
                int yearselected =cal.get(Calendar.YEAR);
                int monthselected =cal.get(Calendar.MONTH);
                int dayselected =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,yearselected,monthselected,dayselected);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearselected, int monthselected, int dayselected) {
                monthselected = monthselected + 1;

                Log.d("dateSet: dd/mm/yy", +dayselected + "/" + monthselected + "/" + yearselected);
                String dmonth, dday;
                int dyear;

                if (monthselected < 10) {
                    dmonth = "0" + monthselected;
                } else {
                    dmonth = String.valueOf(monthselected);
                }
                if (dayselected < 10) {
                    dday = "0" + dayselected;
                } else {
                    dday = String.valueOf(dayselected);
                }
                dyear = yearselected;

                String dateselected = dyear+ "/" + dmonth + "/" + dday ;
                date.setText(dateselected);
            }
        };

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datee = date.getText().toString().trim();
            }
        });


        return view;
    }
}