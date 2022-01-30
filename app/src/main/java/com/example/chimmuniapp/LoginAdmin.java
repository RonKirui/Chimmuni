package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdmin extends AppCompatActivity {

    TextView errorpass, erroremail, create, forgotpass,passerror;
    EditText emaill, passwordd;
    Button login;
    FirebaseAuth mAuth;

    public static Activity aLogiAdmin;
    DatabaseReference reff;

    LinearLayout admin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        if (HomeAdmin.aHomeAdmin!=null){
            HomeAdmin.aHomeAdmin.finish();
        }
        aLogiAdmin = this;
        errorpass = findViewById(R.id.errorpass);
        erroremail = findViewById(R.id.emailerror);
        create = findViewById(R.id.create);
        forgotpass = findViewById(R.id.forgotpassword);
        emaill = findViewById(R.id.email);
        passwordd = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        admin = findViewById(R.id.admin);
        mAuth=FirebaseAuth.getInstance();

        reff = FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaill.getText().toString().trim().toLowerCase();
                String password = passwordd.getText().toString().trim();
                if (email.isEmpty()) {
                    erroremail.setText("Enter valid email");
                    emaill.requestFocus();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    erroremail.setText("Please enter a valid email");
                    emaill.requestFocus();
                }

                else if (password.isEmpty()) {
                    passerror.setText("Enter valid password");
                    passwordd.requestFocus();
                }

                else if (password.length() < 6) {
                    errorpass.setText("Minimum password length is 6 characters");
                    passwordd.requestFocus();
                }

                else {

                    reff.child("Admins").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                Toast.makeText(LoginAdmin.this, "You are not an Admin", Toast.LENGTH_LONG).show();
                            }
                            else {

                                progressDialog = new ProgressDialog(LoginAdmin.this);
                                progressDialog.setMessage("Please wait...");
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.setIndeterminate(true);
                                progressDialog.show();
                                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            SharedPreferences settings=getSharedPreferences("PREFF",0);
                                            SharedPreferences.Editor editor=settings.edit();
                                            editor.putString("admin","Admin");
                                            editor.apply();
                                            progressDialog.dismiss();
                                            //load to home
                                            Intent intent = new Intent(LoginAdmin.this, HomeAdmin.class);
                                            //put your values in the putExtra here
                                            startActivity(intent);
                                            LoginAdmin.this.finish();
                                        }
                                        else{
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginAdmin.this,"Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    /*
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (!snapshot.exists()){
                                Toast.makeText(LoginAdmin.this, "You are not an Admin", Toast.LENGTH_LONG).show();
                            }
                            else {

                                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            Toast.makeText(LoginAdmin.this,"Logged successfully", Toast.LENGTH_LONG).show();
                                            //load to home
                                            Intent intent = new Intent(LoginAdmin.this, HomeAdmin.class);
                                            //put your values in the putExtra here
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(LoginAdmin.this,"Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginAdmin.this,"Database Error", Toast.LENGTH_LONG).show();

                        }
                    };
                    reff.child("Admin").orderByChild(email).addListenerForSingleValueEvent(eventListener);*/
                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetPasswordAdmin resetPasswordAdmin = new ResetPasswordAdmin();

                resetPasswordAdmin.show(getSupportFragmentManager(),"Reset Password");

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAdmin.this, CrerateAccountAdmin.class));
            }
        });
    }}