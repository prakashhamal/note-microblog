package com.note.note.service;

import com.note.note.model.Note;

public interface NoteService {

    void analyzeNotes();

    void analyzeNote(int id);

    void analyzeHashtagNotes(String hashtag);

    Note getNoteById(int id);

    Note saveNote(Note note);

    void deleteNote(int id);


}
