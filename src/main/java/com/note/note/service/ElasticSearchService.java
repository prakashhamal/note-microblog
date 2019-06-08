package com.note.note.service;

import com.note.note.dto.SearchResult;
import com.note.note.exceptions.ServiceException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ElasticSearchService
{
	String index(String docType, String id, String json) throws IOException, URISyntaxException;

	String settings(String index, String settingJSON)throws IOException, URISyntaxException;

	String close(String index)throws IOException, URISyntaxException;
	
	String open(String index)throws IOException, URISyntaxException;

	String settings(String index)throws IOException, URISyntaxException;

	String search(String docType, String searchJson) throws IOException, URISyntaxException;

	String remove(String docType, String id) throws IOException, URISyntaxException;

	String deleteByQuery(String docType, String query) throws IOException, URISyntaxException;

	String get(String docType, String id) throws IOException, URISyntaxException;

	String analyze(String index) throws IOException, URISyntaxException;

	<T> SearchResult<T> search(String docType, String searchJson, Class<T> classType) throws ServiceException;

	String getHost();

	String getIndex();

	void setIndex(String index);

}
