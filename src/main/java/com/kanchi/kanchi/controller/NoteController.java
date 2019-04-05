package com.kanchi.kanchi.controller;


import com.kanchi.kanchi.model.Note;
import com.kanchi.kanchi.repository.NoteRepository;
import com.kanchi.kanchi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/note") // This means URL's start with /demo (after Application path)
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping(path="/analyzeNotes")
    public @ResponseBody String getAllUsers() {
        noteService.analyzeNotes();
        return "Successfully analyzed the notes";
    }
}
