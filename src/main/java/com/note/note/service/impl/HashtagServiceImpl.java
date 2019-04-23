package com.note.note.service.impl;

import com.note.note.model.Hashtag;
import com.note.note.repository.HashtagRepository;
import com.note.note.repository.NoteRepository;
import com.note.note.service.HashtagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HashtagServiceImpl implements HashtagService
{
	private HashtagRepository hashtagRepository;

	public HashtagServiceImpl(HashtagRepository hashtagRepository){
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	public List<Hashtag> getRecentHashtags()
	{
		return null;
	}

	@Override
	public List<Hashtag> getAllHashtags()
	{
		List<Hashtag> hashtags = new ArrayList<>();
		this.hashtagRepository.findAll().forEach(item->{hashtags.add(item);});
		return hashtags;
	}

	@Override
	public List<Hashtag> lookupHashtag(String lookupString)
	{
		return null;
	}
}
