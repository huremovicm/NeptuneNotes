package com.example.neptunenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.WidgetContainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotesRV extends AppCompatActivity {

    private Button logoutBtn, createNote;
    private TextView greetingNotesRV, date;
    private FirebaseAuth mAuth;
    FirebaseUser fUser;
    private ProgressDialog TempDialog;
    private CountDownTimer CDT;
    int i =5;
    private Integer numOfNotes = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_rv);



        logoutBtn = findViewById(R.id.logout);

        greetingNotesRV = (TextView) findViewById(R.id.greetingId);

        mAuth = FirebaseAuth.getInstance();


        DatabaseReference databaseUsers;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseUsers = database.getReference("Users");
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference username = databaseUsers.child(id).child("username");


        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue().toString();
                greetingNotesRV.append(" " + username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUser firebaseUser =  mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Notes");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(uid).exists()) {
                    addWelcomeNote();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        uidRef.addListenerForSingleValueEvent(eventListener);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(NotesRV.this, MainActivity.class);
                startActivity(intent);
            }
        });


        date = findViewById(R.id.dateTxt);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);

        createNote = findViewById(R.id.createNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotesRV.this, Note.class));
            }
        });



        TempDialog = new ProgressDialog(NotesRV.this);
        TempDialog.setMessage("Please wait...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.show();

        CDT = new CountDownTimer(3000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                TempDialog.setMessage("Please wait..");
                i--;
            }

            public void onFinish()
            {
                TempDialog.dismiss();

            }
        }.start();



    }

    public void addWelcomeNote(){

        fUser = mAuth.getCurrentUser();
        String curentUserUid = fUser.getUid();

        FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dReference = fDatabase.getReference("Notes");


        NoteOb welcomeNote = new NoteOb();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);


        String welcomeTitle = "Welcome to Neptune Notes"; // need to be placed in strings.xml
        String  welcomeDate = df.toString();
        String welcomeContent = "Free feel to write anything.";

        welcomeNote.setUsrUid(curentUserUid);
        welcomeNote.setDateOfNote(welcomeDate);
        welcomeNote.setNoteTitle(welcomeTitle);
        welcomeNote.setNoteContent(welcomeContent);


        String noteNo = "Note" + (numOfNotes.toString());

        dReference.child(curentUserUid).child(noteNo).setValue(welcomeNote);


    }




    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(Intent.ACTION_MAIN);
        backToMain.addCategory(Intent.CATEGORY_HOME);
        backToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backToMain);
    }





}