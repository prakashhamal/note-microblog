package com.note.note.controller;


import com.note.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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


    @GetMapping(path="/analyzehashtagnotes/{hashtag}")
    public @ResponseBody String getHashtagNotes(@PathVariable("hashtag") String hashtag) {
        noteService.analyzeHashtagNotes(hashtag);
        return "Successfully analyzed the notes";
    }

    @GetMapping(path="/analyze/{noteId}")
    public @ResponseBody String analyzeNote(@PathVariable("noteId") int noteId) {
        noteService.analyzeNote(noteId);
        return "Successfully analyzed the notes";
    }

    public @ResponseBody String saveNote(){
        
    }
}
