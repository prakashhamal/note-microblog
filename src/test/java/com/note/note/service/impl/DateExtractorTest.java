package com.note.note.service.impl;

import com.note.note.model.Note;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateExtractorTest
{
	public DateExtractor dateExtractor;

	@Before
	public void init(){
		this.dateExtractor = new DateExtractor();
	}

	@Test
	public void dateExtractor(){
		Note note = new Note();
		note.setTitle("Golf at Mulligans $16 02/07/2019 ");
		this.dateExtractor.extractValue(note);
	}
}