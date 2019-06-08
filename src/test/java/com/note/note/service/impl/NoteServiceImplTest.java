package com.note.note.service.impl;

import com.note.note.model.Note;
import com.note.note.service.NoteAnalyzer;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteServiceImplTest
{
	private NoteServiceImpl noteService;
	private NoteAnalyzer noteAnalyzer;

	public NoteServiceImplTest(){
		this.noteService = new NoteServiceImpl(null,null,null);

	}

	@Test
	public void analyzeNoteTest(){
		Note note = new Note();
		note.setTitle("Note : Molten lava cake for chilli's $9 3/26/2019 #expense #eatout");
		this.noteService.analyzeNote(note);
	}
}