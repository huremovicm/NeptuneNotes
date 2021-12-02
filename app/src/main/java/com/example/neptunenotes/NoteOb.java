package com.example.neptunenotes;

import java.text.SimpleDateFormat;

public class NoteOb{
    private String usrUid;
    private String dateOfNote;
    private String noteTitle;
    private String noteContent;

    NoteOb(){

    }

    public NoteOb(String usrUid, String dateOfNote, String noteTitle, String noteContent) {
        this.usrUid = usrUid;
        this.dateOfNote = dateOfNote;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public void setUsrUid(String usrUid) {
        this.usrUid = usrUid;
    }

    public void setDateOfNote(String dateOfNote) {
        this.dateOfNote = dateOfNote;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getUsrUid() {
        return usrUid;
    }

    public String getDateOfNote() {
        return dateOfNote;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }
}
