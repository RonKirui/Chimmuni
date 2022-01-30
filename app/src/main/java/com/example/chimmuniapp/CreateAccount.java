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
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private EditText namee, phonee, emaill, pass, cpass;
    private TextView nameerror, phoneerror, emailerror, passerror, cpasserror;
    private Button register;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        namee = findViewById(R.id.name);
        phonee = findViewById(R.id.phone);
        emaill = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);
        nameerror = findViewById(R.id.nameerror);
        phoneerror = findViewById(R.id.phoneerror);
        emailerror = findViewById(R.id.emailerror);
        passerror = findViewById(R.id.passerror);
        cpasserror = findViewById(R.id.cpasserror);
        register = findViewById(R.id.register);
        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namee.getText().toString().trim();
                String phone = phonee.getText().toString().trim();
                String email = emaill.getText().toString().trim().toLowerCase();
                String password = pass.getText().toString().trim();
                String cpassword = cpass.getText().toString().trim();

                if (name.isEmpty()) {
                    nameerror.setText("Enter valid name");
                    namee.requestFocus();
                }
                else if (phone.isEmpty()) {
                    phoneerror.setText("Enter valid phone number");
                    phonee.requestFocus();
                }
                else if (!(email.isEmpty()) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailerror.setText("Please enter a valid email");
                    emaill.requestFocus();
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
                                            Users users = new Users(name, phone, email);

                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        namee.setText("");
                                                        phonee.setText("");
                                                        emaill.setText("");
                                                        pass.setText("");
                                                        cpass.setText("");
                                                        Toast.makeText(CreateAccount.this, "You have created your account successfully", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(CreateAccount.this, Login.class);
                                                        startActivity(intent);

                                                    } else {
                                                        Toast.makeText(CreateAccount.this, "Account not created. Check your details and try again!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });


                                        } else {

                                            Toast.makeText(CreateAccount.this, "Failed to register the user", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                });

                    } else {

                        Toast.makeText(CreateAccount.this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}