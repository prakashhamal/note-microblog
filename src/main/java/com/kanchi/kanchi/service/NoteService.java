package com.kanchi.kanchi.service;

import com.kanchi.kanchi.model.Note;

import java.util.List;

public interface NoteService {

    void analyzeNotes();

    void analyzeNote(int id);

    Note getNoteById(int id);
}
