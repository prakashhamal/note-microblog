package com.note.note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HashtagNote
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer hashtagId;

	private Integer noteId;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getHashtagId()
	{
		return hashtagId;
	}

	public void setHashtagId(Integer hashtagId)
	{
		this.hashtagId = hashtagId;
	}

	public Integer getNoteId()
	{
		return noteId;
	}

	public void setNoteId(Integer noteId)
	{
		this.noteId = noteId;
	}
}
