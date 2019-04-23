package com.note.note.service;

import com.note.note.model.Note;

public interface NoteService {

    void analyzeNotes();

    void analyzeNote(int id);

    Note getNoteById(int id);
}
