package com.note.note.service;

import com.note.note.model.Note;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public interface ValueExtractor
{
	public void extractValue(Note note);



}
