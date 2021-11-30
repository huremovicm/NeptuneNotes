package com.example.neptunenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class NotesRV extends AppCompatActivity {

    private Button logoutBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_rv);

        logoutBtn = findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(NotesRV.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}