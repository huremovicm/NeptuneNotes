package com.example.neptunenotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.NoteViewHolder> {

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<NoteOb> list) {
        this.list = list;
    }

    public static Integer currentPos;
    Context context;
    ArrayList<NoteOb> list;

    public noteAdapter(Context context, ArrayList<NoteOb> list) {
        this.context = context;
        this.list = list;


    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NoteOb noteOb = list.get(position);
        holder.noteTitle.setText(noteOb.getNoteTitle());
        holder.date.setText(noteOb.getDateOfNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPos = position;
                // Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), EditNote.class);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, date;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.titleNoteList);
            date = itemView.findViewById(R.id.DateNoteList);


        }

    }



}
