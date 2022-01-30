package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NotificationAdminListAdapter extends ArrayAdapter {

    private Activity context;
    List<ImmunizationHelperClass1> retrnotList;
    public NotificationAdminListAdapter(Activity context, List<ImmunizationHelperClass1> retrnotList) {
        super(context, R.layout.notificationslist,retrnotList);
        this.context=context;
        this.retrnotList=retrnotList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.notificationslist,null,true);

        TextView time,message;

        time=listViewItem.findViewById(R.id.date);
        message=listViewItem.findViewById(R.id.task);

        ImmunizationHelperClass1 notificationHelperClass=retrnotList.get(position);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        //change date and time to strings
        String date = df.format(calendar.getTime());

        if (notificationHelperClass.getDate().equals(date)){

            time.setText(notificationHelperClass.getDate());
            message.setText("Hello, Registered child, "+notificationHelperClass.getName() +" has " +notificationHelperClass.getType()+" immunization today on " +notificationHelperClass.getDate()
                    +". Please check the appointments..");
        }else {
            time.setText(date);
            message.setText("Hello, Registered child, "+notificationHelperClass.getName() +" has " +notificationHelperClass.getType()+" immunization today on " +date
                    +". Which supposed to be taken on "+notificationHelperClass.getDate() +". Please check the appointments..");
        }

        return listViewItem;

    }


}
