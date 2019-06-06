package com.note.note.service;

import com.note.note.model.Note;

import java.util.List;

public interface NoteService {

    void analyzeNotes();

    void analyzeNote(int id);

    void analyzeHashtagNotes(String hashtag);

    Note getNoteById(int id);

    Note saveNote(Note note);

    void deleteNote(int id);

    List<Note> recentNotes(int limit, int offset);


}
