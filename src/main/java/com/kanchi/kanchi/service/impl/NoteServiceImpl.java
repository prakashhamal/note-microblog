package com.kanchi.kanchi.service.impl;

import com.kanchi.kanchi.model.Note;
import com.kanchi.kanchi.repository.NoteRepository;
import com.kanchi.kanchi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void analyzeNotes(){
       this.noteRepository.findAll().forEach(note -> {
           this.analyzeNote(note);
       });
    }

    public void analyzeNote(int id){
        Note note = this.getNoteById(id);
        this.analyzeNote(note);
    }

    public void analyzeNote(Note note){
        System.out.println(String.format("Note : %s",note.getTitle()));

    }

    public Note getNoteById(int id){
        Optional<Note> note = this.noteRepository.findById(id);
        return note.get();
    }
}
