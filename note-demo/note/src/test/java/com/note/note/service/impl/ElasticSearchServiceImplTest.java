package com.note.note.service.impl;

import com.note.note.dto.SearchResult;
import com.note.note.exceptions.ServiceException;
import com.note.note.model.Note;
import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.SearchControls;

import static org.junit.Assert.*;

public class ElasticSearchServiceImplTest
{
	ElasticSearchServiceImpl elasticSearchService;

	@Before
	public void init()
	{
	  this.elasticSearchService = new ElasticSearchServiceImpl();
	}


	@Test
	public void testSearch(){
		this.elasticSearchService.setHost("http://47.254.91.56:9200");
		this.elasticSearchService.setIndex("brahman");
		String search = "{\"from\" : 0, \"size\" : 10,\"query\": {\"match\": {\"title\": \"Java job\"}}}";
		try {
			SearchResult<Note> searchResult = this.elasticSearchService.search("note", search, Note.class);
			System.out.println(searchResult.getCount());
		}
		catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}