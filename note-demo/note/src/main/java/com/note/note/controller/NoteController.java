package com.note.note.controller;


import com.note.note.dto.NoteSearchDto;
import com.note.note.dto.RestResponse;
import com.note.note.dto.SearchResult;
import com.note.note.model.Attachment;
import com.note.note.model.Note;
import com.note.note.service.AttachmentService;
import com.note.note.service.NoteService;
import com.note.note.service.impl.AttachmentServiceImpl;
import com.note.note.service.impl.HashtagServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/note") // This means URL's start with /demo (after Application path)
public class NoteController
{
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	private NoteService noteService;
	private AttachmentService attachmentService;

	public NoteController(NoteService noteService, AttachmentService attachmentService)
	{
		this.noteService = noteService;
		this.attachmentService = attachmentService;
	}

	@GetMapping(path = "/processNotes")
	public @ResponseBody
	String getAllUsers()
	{
		noteService.processUnprocessedNotes();
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
		log.info("Analyzing note {}",noteId);
		noteService.analyzeNote(noteId);
		return "Successfully analyzed the notes";
	}

	@PostMapping(path = "/save")
	public @ResponseBody
	ResponseEntity<Note> saveNote(@RequestBody Note note)
	{
		log.debug("Saving note ",note.toString());
		Note savedNote = noteService.saveNote(note);
		return ResponseEntity.ok().body(savedNote);
	}

	@RequestMapping(
			value = ("/uploadfile"),
			headers = "content-type=multipart/form-data",
			method = RequestMethod.POST)
	ResponseEntity<RestResponse> saveNote(@RequestParam("files") MultipartFile[] files,@RequestParam("noteId") int noteId)
	{
		for(int i=0;i<files.length;i++) {
			this.attachmentService.attachNoteFile(files[i],noteId);
		}
		RestResponse restResponse = new RestResponse();
		restResponse.setResponseMessage("Successfull uploaded the file");
		return ResponseEntity.ok().body(restResponse);
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
	ResponseEntity<RestResponse> deleteNote(@PathVariable("noteId") int noteId)
	{
		noteService.deleteNote(noteId);
		RestResponse restResponse = new RestResponse();
		restResponse.setResponseMessage("Successfully deleted the note.");
		return ResponseEntity.ok().body(restResponse);
	}

	@GetMapping(path = "/recentnotes")
	public @ResponseBody
	ResponseEntity<List<Note>> recentNotes(@RequestParam("limit") int limit,@RequestParam("offset") int offset)
	{
		List<Note> notes = noteService.recentNotes(limit, offset);
		return ResponseEntity.ok().body(notes);
	}

	@GetMapping(path = "/hashtagnotes/{hashtag}")
	public @ResponseBody
	ResponseEntity<List<Note>> hashtagNotes(@PathVariable("hashtag") String hashtag)
	{
		List<Note> notes = noteService.hashtagNotes(hashtag);
		return ResponseEntity.ok().body(notes);
	}
	
	@PostMapping(path = "/search")
	public @ResponseBody
	ResponseEntity<SearchResult<Note>> searchNote(@RequestBody NoteSearchDto searchDto)
	{
		SearchResult<Note> searchResult = noteService.searchNote(searchDto);
		return ResponseEntity.ok().body(searchResult);
	}



	@GetMapping(path = "/blog")
	public @ResponseBody
	ResponseEntity<List<Note>> hashtagNotes()
	{
		List<Note> notes = noteService.hashtagNotes("blog");
		return ResponseEntity.ok().body(notes);
	}

	@GetMapping(path = "/test")
	public @ResponseBody
	ResponseEntity<RestResponse> test()
	{
		this.attachmentService.uploadFile("asdf");
		RestResponse restResponse = new RestResponse();
		return ResponseEntity.ok().body(restResponse);
	}
}
