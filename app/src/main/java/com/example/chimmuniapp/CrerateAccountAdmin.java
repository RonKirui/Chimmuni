package com.example.chimmuniapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrerateAccountAdmin extends AppCompatActivity {

    private EditText hcnamee, hcregnoo, emaill, pass, cpass;
    private TextView hcnameerror,  emailerror, hcregnoerror,passerror, cpasserror;
    private Button register;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crerate_account_admin);

        hcnamee = findViewById(R.id.hcname);
        emaill = findViewById(R.id.email);
        hcregnoo = findViewById(R.id.hcregno);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);
        hcnameerror = findViewById(R.id.nameerror);
        emailerror = findViewById(R.id.emailerror);
        hcregnoerror = findViewById(R.id.phoneerror);
        passerror = findViewById(R.id.passerror);
        cpasserror = findViewById(R.id.cpasserror);
        register = findViewById(R.id.register);
        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hcname = hcnamee.getText().toString().trim();
                String hcregno = hcregnoo.getText().toString().trim();
                String email = emaill.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String cpassword = cpass.getText().toString().trim();

                if (hcname.isEmpty()) {
                    hcnameerror.setText("Enter valid name");
                    hcnamee.requestFocus();
                }
                else if (!(email.isEmpty()) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailerror.setText("Please enter a valid email");
                    emaill.requestFocus();
                }
                else if (hcregno.isEmpty()) {
                    hcregnoerror.setText("Enter valid phone number");
                    hcregnoo.requestFocus();
                }
                else if (password.isEmpty()) {
                    passerror.setText("Enter valid password");
                    pass.requestFocus();
                }
                else if (cpassword.isEmpty()) {
                    cpasserror.setText("Confirm password");
                    cpass.requestFocus();
                }
                else if (password.length() < 6) {
                    cpasserror.setText("Minimum password length is 6 characters");
                    pass.requestFocus();
                }
                else {
                    if (cpassword.equals(password)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Users users = new Users(hcname, hcregno, email);

                                            FirebaseDatabase.getInstance().getReference("Admins")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        hcnamee.setText("");
                                                        emaill.setText("");
                                                        hcregnoo.setText("");
                                                        pass.setText("");
                                                        cpass.setText("");
                                                        Toast.makeText(CrerateAccountAdmin.this, "You have created your account successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CrerateAccountAdmin.this, Login.class);
                                                        startActivity(intent);
                                                        Toast.makeText(CrerateAccountAdmin.this, "User has been registered successfully", Toast.LENGTH_LONG).show();

                                                    } else {
                                                        Toast.makeText(CrerateAccountAdmin.this, "Account not created. Check your details and try again!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });


                                        } else {

                                            Toast.makeText(CrerateAccountAdmin.this, "Failed to register the user", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                });

                    } else {

                        Toast.makeText(CrerateAccountAdmin.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

}