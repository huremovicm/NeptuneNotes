package com.example.neptunenotes;

import static com.example.neptunenotes.noteAdapter.currentPos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EditNote extends AppCompatActivity {

    private EditText noteEditTitle;
    private TextView noteDate;
    private EditText noteEditText;
    private String cp = Integer.toString(currentPos);
    private ArrayList<String> l = new ArrayList<String>();
    private ArrayList<String> keys = new ArrayList<>();
    private String getNoteKey;




    private Button deleteBtn, saveBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteEditTitle = findViewById(R.id.noteTitleEdit);
        noteDate = findViewById(R.id.noteDateEdit);
        noteEditText = findViewById(R.id.edtNoteTxt);

        // Get a reference to our posts
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Notes").child(uid);
        // Attach a listener to read the data at our posts reference

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(mAuth.getCurrentUser().getUid());


        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 ArrayList<NoteOb> n = new ArrayList<NoteOb>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                     n.add(dataSnapshot.getValue(NoteOb.class));

                }
                Integer s = n.size();

                Log.d("TAG", s.toString());

                NoteOb noteToSave = n.get(currentPos);
                noteEditTitle.setText(noteToSave.getNoteTitle());
                noteDate.setText(noteToSave.getDateOfNote());
                noteEditText.setText(noteToSave.getNoteContent());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NoteOb note = dataSnapshot.getValue(NoteOb.class);
                    l.add(note.getTimeStamp());
                }
                //Integer s = l.size();
                // Log.d("TAG", s.toString());

                // Collections.reverse(l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        deleteBtn = findViewById(R.id.delete);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            NoteOb note = data.getValue(NoteOb.class);
                            String ts = note.getTimeStamp();
                            if (l.get(currentPos) == ts) {
                                db.child(data.getKey()).removeValue();

                            } else {
                                //Log.d("TAG", l.get(currentPos)+" "+note.getTimeStamp());
                            }

                        }

                        startActivity(new Intent(EditNote.this, NotesRV.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        });

        saveBtn = findViewById(R.id.save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    String currentNote;

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                               String k =  data.getKey();
                               keys.add(k);

                        }
                        currentNote = keys.get(currentPos);

                        String newTitle = noteEditTitle.getText().toString();
                        String newTitleContent = noteEditText.getText().toString();



                        db.child(currentNote).child("noteTitle").setValue(newTitle);
                        db.child(currentNote).child("noteContent").setValue(newTitleContent);

                        startActivity(new Intent(EditNote.this, NotesRV.class));


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });



            }
        });


    }




}