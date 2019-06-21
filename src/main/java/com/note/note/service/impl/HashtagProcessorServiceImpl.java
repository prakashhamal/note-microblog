package com.note.note.service.impl;

import com.note.note.model.Hashtag;
import com.note.note.model.HashtagNote;
import com.note.note.model.Note;
import com.note.note.repository.HashtagNoteRepository;
import com.note.note.repository.HashtagRepository;
import com.note.note.service.HashtagProcessorService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HashtagProcessorServiceImpl implements HashtagProcessorService
{
	private static final Logger log = LoggerFactory.getLogger(HashtagProcessorServiceImpl.class);

	private HashtagRepository hashtagRepository;
    private HashtagNoteRepository hashtagNoteRepository;
    
	public HashtagProcessorServiceImpl(HashtagRepository hashtagRepository,HashtagNoteRepository hashtagNoteRepository){
		this.hashtagRepository = hashtagRepository;
		this.hashtagNoteRepository = hashtagNoteRepository;
		
	}
	public void processHashtagsOfNote(Note note){
		
		JSONObject detailJson = new JSONObject(note.getDescription());
		JSONArray hashtags = detailJson.getJSONArray("hashtags");

		hashtagNoteRepository.deleteNoteHashtagLinks(note.getId());
		for(int i=0;i<hashtags.length();i++){
			String hashtag = hashtags.getString(i);
			Hashtag hashtagDB = hashtagRepository.findFirstByHashtagEquals(hashtag);
			if(hashtagDB == null){
				hashtagDB = new Hashtag();
				hashtagDB.setHashtag(hashtag.toUpperCase());
				hashtagDB = hashtagRepository.save(hashtagDB);
				log.info("Added new hashtag "+hashtag);
				
			}
			HashtagNote hashtagNote = new HashtagNote();
			hashtagNote.setHashtagId(hashtagDB.getId());
			hashtagNote.setNoteId(note.getId());
			this.hashtagNoteRepository.save(hashtagNote);
		};
		
	}
}
