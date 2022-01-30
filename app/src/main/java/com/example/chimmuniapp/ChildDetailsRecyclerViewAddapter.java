package com.example.chimmuniapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChildDetailsRecyclerViewAddapter extends FirebaseRecyclerAdapter<model,ChildDetailsRecyclerViewAddapter.myviewholder>
{

    public ChildDetailsRecyclerViewAddapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final model model) {
        holder.childname.setText(model.getChildname());
        holder.dob.setText(model.getDob());
        holder.sex.setText(model.getSex());


        holder.relativenavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,
                        new descfragment(model.getChildname(),model.getDob(),model.getSex(),model.getMothername(),
                                model.getFathername(),model.getCbr(),model.getAncno(),model.getHealthcentre())).addToBackStack(null).commit();
            }
        });

    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_details_recyclerview_layout,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {

        TextView childname, dob, sex;

        RelativeLayout relativenavigation;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            childname=itemView.findViewById(R.id.childname);
            dob=itemView.findViewById(R.id.dob);
            sex=itemView.findViewById(R.id.sex);

            relativenavigation=itemView.findViewById(R.id.relativenavigate);
        }
    }

}