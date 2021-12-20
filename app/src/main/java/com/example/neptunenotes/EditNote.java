package com.example.neptunenotes;

import static com.example.neptunenotes.noteAdapter.currentPos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class EditNote extends AppCompatActivity {

    private EditText noteEditTitle;
    private TextView noteDate;
    private EditText noteEditText;
    private String cp = Integer.toString(currentPos);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteEditTitle = findViewById(R.id.noteTitleEdit);
        noteDate = findViewById(R.id.noteDateEdit);
        noteEditText = findViewById(R.id.edtNoteTxt);

        // Get a reference to our posts
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =  mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Notes");
        // Attach a listener to read the data at our posts reference
        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NoteOb note = dataSnapshot.child(uid).child("Note"+cp).getValue(NoteOb.class);
                noteEditTitle.setText(note.getNoteTitle());
                noteDate.setText(note.getDateOfNote());
                noteEditText.setText(note.getNoteContent());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }
}