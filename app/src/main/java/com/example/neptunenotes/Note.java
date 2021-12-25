package com.example.neptunenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Note extends AppCompatActivity {

    private TextView date;
    private Button createNote;
    private TextView noteTitle, noteContent;
    private Integer numOfNotes;


    FirebaseDatabase fDatabase;
    DatabaseReference dReference;
    FirebaseUser fUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Auto set date on note
        date = findViewById(R.id.noteDate);
        String dateIn = date();
        date.setText(dateIn);

        // Grab button to make note
        createNote = findViewById(R.id.makeN);


        // Get Notes
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("Notes");


        // Make Welcome note
        int noteCounter = 0;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Notes").child(uid);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usrUid = dataSnapshot.child("usrUid").getValue(String.class);
                numOfNotes = (int) dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        uidRef.addListenerForSingleValueEvent(eventListener);


        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();

                startActivity(new Intent(Note.this, NotesRV.class));


            }
        });


    }


    public String date() {


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;

    }

    public void createNote() {

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        String curentUserUid = fUser.getUid();


        // createNote = findViewById(R.id.createNote);
        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.edtNoteTxt);

        String title = noteTitle.getText().toString();
        String date = date();
        String content = noteContent.getText().toString();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        if (!title.isEmpty() && !content.isEmpty()) {
            NoteOb noteToCreate = new NoteOb();
            noteToCreate.setUsrUid(curentUserUid);
            noteToCreate.setNoteTitle(title);
            noteToCreate.setDateOfNote(date);
            noteToCreate.setNoteContent(content);
            noteToCreate.setTimeStamp(timeStamp.replace(".",""));



            dReference.child(curentUserUid).push().setValue(noteToCreate);

            sortNotes();

        } else {
            Toast.makeText(Note.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }




    }
    public void sortNotes () {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Notes").child(uid);

        //uidRef.ord




    }
}

