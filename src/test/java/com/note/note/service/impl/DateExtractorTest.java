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
		note.setTitle("Had six inch philly cheese steak and fries from moochies for $9 on 03/02/2019 #expense #eatout");
		this.dateExtractor.extractValue(note);
		System.out.println(note.getDescription());
	}
}