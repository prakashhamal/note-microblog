package com.note.note.controller;


import com.note.note.model.Note;
import com.note.note.service.NoteService;
import com.note.note.service.impl.HashtagServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/note") // This means URL's start with /demo (after Application path)
public class NoteController
{
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	private NoteService noteService;

	public NoteController(NoteService noteService)
	{
		this.noteService = noteService;
	}

	@GetMapping(path = "/analyzeNotes")
	public @ResponseBody
	String getAllUsers()
	{
		noteService.analyzeNotes();
		return "Successfully analyzed the notes";
	}


	@GetMapping(path = "/analyzehashtagnotes/{hashtag}")
	public @ResponseBody
	String getHashtagNotes(@PathVariable("hashtag") String hashtag)
	{
		noteService.analyzeHashtagNotes(hashtag);
		return "Successfully analyzed the notes";
	}

	@GetMapping(path = "/analyze/{noteId}")
	public @ResponseBody
	String analyzeNote(@PathVariable("noteId") int noteId)
	{
		noteService.analyzeNote(noteId);
		return "Successfully analyzed the notes";
	}

	@PostMapping(path = "/save")
	public @ResponseBody
	ResponseEntity<Note> saveNote(@RequestBody Note note)
	{
		Note savedNote = noteService.saveNote(note);
		return ResponseEntity.ok().body(savedNote);
	}

	@GetMapping(path = "/{noteId}")
	public @ResponseBody
	ResponseEntity<Note> getNote(@PathVariable("noteId") int noteId)
	{
		Note note = noteService.getNoteById(noteId);
		return ResponseEntity.ok().body(note);
	}

	@DeleteMapping(path = "/{noteId}")
	public @ResponseBody
	ResponseEntity<String> deleteNote(@PathVariable("noteId") int noteId)
	{
		noteService.deleteNote(noteId);
		return ResponseEntity.ok("Successfully deleted");
	}

	@GetMapping(path = "/recentnotes")
	public @ResponseBody
	ResponseEntity<List<Note>> recentNotes(@RequestParam("limit") int limit,@RequestParam("offset") int offset)
	{
		List<Note> notes = noteService.recentNotes(limit, offset);
		return ResponseEntity.ok().body(notes);
	}
}
