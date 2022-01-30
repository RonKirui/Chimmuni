package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class Login extends AppCompatActivity {

        TextView errorpass, erroremail, create, forgotpass,passerror;
        EditText emaill, passwordd;
        Button login;
        FirebaseAuth mAuth;
        ProgressDialog progressDialog;
    public static Activity aLogin;
        DatabaseReference reff;
        LinearLayout admin;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            if (MainActivity.aMain!=null){
                MainActivity.aMain.finish();
            }
            aLogin=this;
            if (Home.aHome!=null){
                Home.aHome.finish();
            }

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
                        progressDialog = new ProgressDialog(Login.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                        reff.child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.exists()){
                                    Toast.makeText(Login.this, "Not yet registered as a user", Toast.LENGTH_LONG).show();
                                }
                                else {


                                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){

                                                SharedPreferences settings=getSharedPreferences("PREFF",0);
                                                SharedPreferences.Editor editor=settings.edit();
                                                editor.putString("user","User");
                                                editor.apply();

                                                progressDialog.dismiss();
                                                Intent intent = new Intent(Login.this, Home.class);
                                                startActivity(intent);
                                                Login.this.finish();
                                                /*NotificationCompat.Builder builder = new NotificationCompat.Builder(Login.this,Utils.CHANNEL_ID)
                                                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                                                        .setContentTitle(Utils.NOTI_TITLE)
                                                        .setContentText(Utils.NOTI_DESC)
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setAutoCancel(true);


                                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Login.this);
                                                notificationManagerCompat.notify(Utils.NOTI_ID,builder.build());

                                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(Login.this);
                                                stackBuilder.addParentStack(Home.class);

                                                stackBuilder.addNextIntent(intent);

                                                //load to home
                                                PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                                                builder.setContentIntent(pendingIntent);

                                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                notificationManager.notify(0,builder.build());*/
                                            }
                                            else{
                                                progressDialog.dismiss();
                                                Toast.makeText(Login.this,"Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                                            }
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
            });

            admin.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    startActivity(new Intent(Login.this, LoginAdmin.class));
                }
            });
            forgotpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ResetPassword resetPassword = new ResetPassword();

                    resetPassword.show(getSupportFragmentManager(),"Reset Password");

                }
            });
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,CreateAccount.class));
                }
            });
        }

    @Override
    public void onBackPressed() {

        new androidx.appcompat.app.AlertDialog.Builder(Login.this)
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

        //super.onBackPressed();
    }
}