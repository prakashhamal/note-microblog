package com.note.note.service.impl;

import com.note.note.model.Hashtag;
import com.note.note.repository.HashtagRepository;
import com.note.note.service.HashtagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class HashtagServiceImpl implements HashtagService
{
	private static final Logger log = LoggerFactory.getLogger(HashtagServiceImpl.class);
	private static final String KEY = "hashtags";
	private HashtagRepository hashtagRepository;

	private RedisTemplate<String,Object> redisTemplate;

	private HashOperations hashOperations;


	@Autowired
	public HashtagServiceImpl(HashtagRepository hashtagRepository,RedisTemplate<String,Object> redisTemplate){
		this.hashtagRepository = hashtagRepository;
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	public void init(){
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public List<Hashtag> getRecentHashtags()
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hashtag> getAllHashtags()
	{
		List<Hashtag> hashtags = new ArrayList<>();
		this.hashtagRepository.allHashtags().forEach(item->{hashtags.add(item);});
		return hashtags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> lookupHashtag(String lookupString)
	{
		log.debug("Lookup String : "+lookupString);
		List<String> hashtags = (List<String>)this.hashOperations.get(KEY,1);

		List<String> lookupResult = new ArrayList<>();

		final String lookupStringUC = lookupString.toUpperCase();
		hashtags.forEach(hashtag->{
			if(hashtag.startsWith(lookupStringUC)){
				lookupResult.add(hashtag);
			}
		});

		return lookupResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildHashtagsCache(int userId)
	{
		List<String> hashtags = new ArrayList<>();
		this.hashtagRepository.findAll().forEach(hashtag->{
			hashtags.add(hashtag.getHashtag());
		});
		this.hashOperations.put(KEY,userId,hashtags);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void destroyHashtagsCache(int userId)
	{
		this.hashOperations.delete(KEY,userId);
	}
}
