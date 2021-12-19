package com.example.neptunenotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.NoteViewHolder>{
    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<NoteOb> list) {
        this.list = list;
    }

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
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteOb noteOb = list.get(position);
        holder.noteTitle.setText(noteOb.getNoteTitle());
        holder.date.setText(noteOb.getDateOfNote());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{

         TextView noteTitle, date;

         public NoteViewHolder(@NonNull View itemView){
             super(itemView);

             noteTitle = itemView.findViewById(R.id.titleNoteList);
             date = itemView.findViewById(R.id.DateNoteList);
         }

    }
}
