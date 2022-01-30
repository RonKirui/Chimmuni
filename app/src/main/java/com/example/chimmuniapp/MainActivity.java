package com.example.chimmuniapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView welcome,txviewname;

    public static Activity aMain;
    Animation topAnim, bottomAnim;
    ImageView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aMain=this;
        welcome=findViewById(R.id.welcome);
        txviewname=findViewById(R.id.txviewname);
        cardView=findViewById(R.id.cardView);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        cardView.setAnimation(topAnim);
        welcome.setAnimation(topAnim);
        txviewname.setAnimation(bottomAnim);

        SharedPreferences settings=getSharedPreferences("PREFF",0);

        String usermode =settings.getString("user","");
        String adminmode =settings.getString("admin","");

        if (usermode.equals("User")){
            startActivity(new Intent(MainActivity.this,Home.class));
            MainActivity.this.finish();
        }
        else if (adminmode.equals("Admin")){
            startActivity(new Intent(MainActivity.this,HomeAdmin.class));
            MainActivity.this.finish();
        }
        else {
            Handler handler=new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

                    //check internet connection if its true
                    if (networkInfo!=null && networkInfo.isConnected()){

                        startActivity(new Intent(MainActivity.this,Login.class));
                    }
                    else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Network Alert")
                                .setMessage("You don't have internet connection!")
                                .setPositiveButton("Ok",null)
                                .show();
                    }
                }
            },3000);

       }


    }
}