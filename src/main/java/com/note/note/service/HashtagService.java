package com.note.note.service;

import com.note.note.model.Hashtag;

import java.util.List;

public interface HashtagService
{
	List<Hashtag> getRecentHashtags();

	List<Hashtag> getAllHashtags();

	List<Hashtag> lookupHashtag(String lookupString);
}
