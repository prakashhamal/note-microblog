package com.note.note.utils;

import com.note.note.model.Note;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public interface AnalyzerUtils
{
	public default void setValue(Note note, String key, Object value){

		JSONObject descJson = new JSONObject();
		if(!StringUtils.isEmpty(note.getDescription())){
			descJson = new JSONObject(note.getDescription());
		}
		descJson.put(key,value);
		note.setDescription(descJson.toString());

	}
}
