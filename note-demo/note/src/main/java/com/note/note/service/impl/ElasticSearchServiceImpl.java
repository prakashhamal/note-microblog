package com.note.note.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.note.note.dto.SearchResult;
import com.note.note.exceptions.ServiceException;
import com.note.note.service.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService
{
	public static final String BRAHMAN_INDEX = "brahman";
	private static final Logger log = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
	@Value("${elasticsearch.host}")
	private String host;

	private String index;
	private RestTemplate restTemplate;

	public ElasticSearchServiceImpl(){
		this.restTemplate = new RestTemplate();
	}

	@Override
	public String getHost()
	{
		return this.host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public void setIndex(String index)
	{
		this.index = index;
	}

	@Override
	public String getIndex()
	{
		return this.index;
	}




	@Override
	public String index(String docType, String id, String json) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/" + docType + "/" + id;
		return makeRequest(HttpMethod.PUT, endpoint, json);
	}


	@Override
	public String close(String index) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/_close";
		return makeRequest(HttpMethod.POST, endpoint, "{}");
	}

	@Override
	public String open(String index) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/_open";
		return makeRequest(HttpMethod.POST, endpoint, "{}");
	}

	@Override
	public String settings(String index, String settingJSON) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/_settings";
		return makeRequest(HttpMethod.PUT, endpoint, settingJSON);
	}

	@Override
	public String settings(String index) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/_settings";
		return makeRequest(HttpMethod.GET, endpoint, null);
	}

	@Override
	public String search(String docType, String searchJson) 
	{
		String endpoint = host + "/" + index + "/" + docType + "/_search";
		return makeRequest(HttpMethod.POST, endpoint, searchJson);
	}

	@Override
	public String remove(String docType, String id) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/" + docType + "/" + id;
		return makeRequest(HttpMethod.DELETE, endpoint, null);
	}

	@Override
	public String deleteByQuery(String docType, String query) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/" + docType + "/_delete_by_query";
		return makeRequest(HttpMethod.POST, endpoint, query);
	}

	@Override
	public String get(String docType, String id) throws IOException, URISyntaxException
	{
		String endpoint = host + "/" + index + "/" + docType + "/" + id;
		return makeRequest(HttpMethod.GET, endpoint, null);
	}

	@Override
	public String analyze(String index) 
	{
		String endpoint = host + "/" + index + "/_analyze";
		return makeRequest(HttpMethod.GET, endpoint, null);
	}

	String makeRequest(HttpMethod httpMethod, String endpoint, String json)
	{
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
		ResponseEntity<String> response = restTemplate.exchange(endpoint, httpMethod, entity, String.class);
		return response.getBody();
	}

	@Override
	public <T> SearchResult<T> search(String docType, String searchJson, Class<T> classType) throws ServiceException
	{
		return parseElasticSearchResponse(search(docType, searchJson), classType);
	}

	public <T> SearchResult<T> parseElasticSearchResponse(String response, Class<T> classType) throws ServiceException
	{
		SearchResult<T> searchResult = new SearchResult<>();
		try {
			if (!StringUtils.isEmpty(response)) {
				JsonNode rootNode = new ObjectMapper().readTree(response);
				JsonNode hits = rootNode.findValue("hits").findValue("hits");
				if (hits != null) {
					List<T> results = new ArrayList<>();
					for (JsonNode hit : hits) {
						String sourceString = hit.findValue("_source").toString();
						Gson gson = new Gson();
						T item = gson.fromJson(sourceString, classType);
						results.add(item);
					}
					Integer count = rootNode.findValue("hits").findValue("total").intValue();
					searchResult.setResult(results);
					searchResult.setCount(count);
				}

			}
		} catch (IOException e) {
			log.error("Parsing elastic search result failed for this response: " + response + " " + e.getMessage(), e);
			throw new ServiceException("Exception occurred parsing the elasticsearch response");
		}
		return searchResult;
	}
}
