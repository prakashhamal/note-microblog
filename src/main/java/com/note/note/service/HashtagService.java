package com.note.note.service;

import com.note.note.model.Hashtag;

import java.util.List;

public interface HashtagService
{
	List<Hashtag> getRecentHashtags();

	List<Hashtag> getAllHashtags();

	List<String> lookupHashtag(String lookupString);

	void buildHashtagsCache(int userId);

	void destroyHashtagsCache(int userId);
}
