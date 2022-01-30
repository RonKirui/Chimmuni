package com.example.chimmuniapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {


DatabaseReference reffimmunization, reff;
    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    public static Activity aHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Login.aLogin!=null){
            Login.aLogin.finish();
        }
        aHome=this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_immunization, R.id.nav_details, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences settings=getSharedPreferences("PREFF",0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString("userbackpressed_id","1");
        editor.apply();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                if (id==R.id.nav_logout_user){
                    logOut();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("user");
                    editor.apply();
                }
                if (id==R.id.nav_home){
                    fragment = new HomeFragment();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("userbackpressed_id","1");
                    editor.apply();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                if (id==R.id.nav_immunization){
                    fragment = new ImmunizationsFragment();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("userbackpressed_id");
                    editor.apply();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                if (id==R.id.nav_details){
                    fragment = new ChilddetailsrecFragment();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("userbackpressed_id");
                    editor.apply();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                if(id==R.id.nav_profile){
                    fragment = new ProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("userbackpressed_id");
                    editor.apply();
                }
                drawer.closeDrawer(GravityCompat.START,true);
                return true;
            }
        });
        //get time and date
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

        //change date and time to strings
        String date = df.format(calendar.getTime());


        //to send appointments
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void logOut() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        SharedPreferences settings=getSharedPreferences("PREFF",0);
        String usermode =settings.getString("userbackpressed_id","");
        if (usermode.equals("1")){
            new AlertDialog.Builder(Home.this)
                    .setTitle("Exit Application?")
                    .setCancelable(false)
                    .setMessage("Click yes to exit!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,new HomeFragment());
            transaction.commit();
            SharedPreferences.Editor editor=settings.edit();
            editor.putString("userbackpressed_id","1");
            editor.apply();
            //super.onBackPressed();
        }


    }
}