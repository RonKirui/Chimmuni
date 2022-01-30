package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeAdmin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private String previousfrag;
    public static Activity aHomeAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoginAdmin.aLogiAdmin!=null){
            LoginAdmin.aLogiAdmin.finish();
        }
        aHomeAdmin = this;

        setContentView(R.layout.activity_home_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_admin, R.id.nav_profile_admin,R.id.nav_childrenlist)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences settings=getSharedPreferences("PREFF",0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString("adminbackpressed_id","1");
        editor.apply();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment;
                if (id==R.id.nav_logout_admin){
                    logOutAdmin();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("admin");
                    editor.apply();
                }

                if (id==R.id.nav_home_admin){
                    fragment = new HomeAdminFragment();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("adminbackpressed_id","1");
                    editor.apply();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                if (id==R.id.nav_profile_admin){
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("adminbackpressed_id");
                    editor.apply();
                    fragment = new ProfileAdminFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                if(id==R.id.nav_childrenlist){
                    fragment = new ChildListAdminFragment();
                    SharedPreferences settings=getSharedPreferences("PREFF",0);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.remove("adminbackpressed_id");
                    editor.apply();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment,fragment);
                    transaction.commit();
                }
                drawer.closeDrawer(GravityCompat.START,true);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void logOutAdmin() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginAdmin.class));
    }

    @Override
    public void onBackPressed() {
        SharedPreferences settings=getSharedPreferences("PREFF",0);
        String usermode =settings.getString("adminbackpressed_id","");
        if (usermode.equals("User")){
            new androidx.appcompat.app.AlertDialog.Builder(HomeAdmin.this)
                    .setTitle("Exit Application?")
                    .setMessage("Click yes to exit!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();



        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,new HomeAdminFragment());
            transaction.commit();
            SharedPreferences.Editor editor=settings.edit();
            editor.putString("adminbackpressed_id","1");
            editor.apply();
            //super.onBackPressed();
        }

    }
}
