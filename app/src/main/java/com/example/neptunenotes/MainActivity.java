package com.example.neptunenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button signupBtn, loginBtn;
    private TextInputEditText edtEmailLogin, edtTxtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupBtn = findViewById(R.id.signupBtn);
        loginBtn = findViewById(R.id.loginBtn);

        // Signup action
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        // Login action
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                edtEmailLogin = findViewById(R.id.emailLogin1);
                edtTxtPassword = findViewById(R.id.passwordLogin1);

                String email = edtEmailLogin.getText().toString().trim();
                String password = edtTxtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    TextInputLayout l = findViewById(R.id.emailLogin1);
                    l.setError("Email is required!");
                    edtEmailLogin.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    TextInputLayout l = findViewById(R.id.emailLogin1);
                    l.setError("Please provide valid email!");
                    edtEmailLogin.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    TextInputLayout l = findViewById(R.id.passwordLogin1);
                    l.setError("Password is required!");
                    edtTxtPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    TextInputLayout l = findViewById(R.id.passwordLogin1);
                    l.setError("Minimum password length should be 6 characters!");
                    edtTxtPassword.requestFocus();
                    return;
                }
                // Sign in
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(MainActivity.this, NotesRV.class));

                        } else {
                            Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }


                });


            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(Intent.ACTION_MAIN);
        backToMain.addCategory(Intent.CATEGORY_HOME);
        backToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backToMain);
    }

}