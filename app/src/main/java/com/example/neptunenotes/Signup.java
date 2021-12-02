package com.example.neptunenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private TextView createBtn;
    private FirebaseAuth mAuth;
    private TextInputEditText editTextEmail, editTextUsername, editTextPassword, confPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        createBtn = (Button) findViewById(R.id.createAccount);

        editTextEmail = (TextInputEditText) findViewById(R.id.emailSignup1);
        editTextUsername = (TextInputEditText) findViewById(R.id.usernameSignup1);
        editTextPassword = (TextInputEditText) findViewById(R.id.passwordSignup1);
        confPassword = (TextInputEditText) findViewById(R.id.passwordSignupMatch1);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  email = editTextEmail.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordconf = confPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    editTextUsername.setError("Username is requried!");
                    editTextUsername.requestFocus();
                    return;

                }
                if (email.isEmpty()) {
                    editTextEmail.setError("Email is requried!");
                    editTextEmail.requestFocus();
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                   editTextEmail.setError("Please provide valid email!");
                    editTextEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    TextInputLayout  l = findViewById(R.id.passwordSignup);
                    l.setError("Password is requried!");
                    editTextPassword.requestFocus();
                    return;

                }
                if (password.length() < 6) {
                    TextInputLayout  l = findViewById(R.id.passwordSignup);
                    l.setError("Minimum password length should be 6 characters!");
                    editTextPassword.requestFocus();
                    return;
                }
                if (passwordconf.isEmpty()) {
                    TextInputLayout  l = findViewById(R.id.passwordSignupMatch);
                    l.setError("Password is requried!");
                    confPassword.requestFocus();
                    return;

                }
                if (passwordconf.length() < 6) {
                    TextInputLayout  l = findViewById(R.id.passwordSignupMatch);
                    l.setError("Minimum password length should be 6 characters!");
                    confPassword.requestFocus();
                    return;
                }

                String passwd1 = password;
                String passwd2 = passwordconf;
                if (!passwd1.equals(passwd2)) {
                    TextInputLayout  l = findViewById(R.id.passwordSignupMatch);
                    l.setError("Password mismatch!");
                    confPassword.requestFocus();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           User user_ = new User(email, username);

                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user_).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(Signup.this, "User has been signed up!", Toast.LENGTH_SHORT).show();
                                       startActivity(new Intent(Signup.this, MainActivity.class));

                                   }else{
                                       Toast.makeText(Signup.this, "Faild to sign up user!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                       }else{
                           Toast.makeText(Signup.this, "Faild to sign up user!", Toast.LENGTH_SHORT).show();

                       }
                    }
                });

            }
        });
    }
}