package com.note.note.controller;


import com.note.note.dto.NoteSearchDto;
import com.note.note.dto.RestResponse;
import com.note.note.dto.SearchResult;
import com.note.note.model.Note;
import com.note.note.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/blog") // This means URL's start with /demo (after Application path)
public class BlogController
{
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);

	private NoteService noteService;

	public BlogController(NoteService noteService)
	{
		this.noteService = noteService;
	}

	
	@GetMapping
	public @ResponseBody
	ResponseEntity<List<Note>> hashtagNotes()
	{
		List<Note> notes = noteService.hashtagNotes("blog");
		return ResponseEntity.ok().body(notes);
	}
}
