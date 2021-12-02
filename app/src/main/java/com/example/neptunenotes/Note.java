package com.example.neptunenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Note extends AppCompatActivity {
    private TextView date;
    private Button createNote;
    private TextView noteTitle, noteContent;

    FirebaseDatabase fDatabase;
    DatabaseReference dReference;
    FirebaseUser fUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        date = findViewById(R.id.noteDate);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);

        createNote = findViewById(R.id.createNote);
        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.edtNoteTxt);


        // Get Notes
        fDatabase = FirebaseDatabase.getInstance();
        dReference = fDatabase.getReference("Notes");

        // Get current user uid


        // Make Welcome note
        addWelcomeNote();




    }

    public void addWelcomeNote(){

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        String curentUserUid = fUser.getUid();

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


        dReference.child(curentUserUid).setValue(welcomeNote);

       // Map<String, NoteOb> notes = new HashMap<>();
        //notes.put(curentUserUid, welcomeNote);

        //dReference.setValue(notes);

       // dReference.child(curentUserUid).push().setValue(welcomeNote);

    }
}