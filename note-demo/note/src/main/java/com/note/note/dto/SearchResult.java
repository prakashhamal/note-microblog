package com.note.note.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResult<T>
{
	private Integer count;
	private List<T> result;

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public List<T> getResult()
	{
		return result;
	}

	public void setResult(List<T> result)
	{
		this.result = result;
	}

	public void addItem(T item){
		if(result == null){
			result = new ArrayList<>();
		}
		result.add(item);
	}
}
