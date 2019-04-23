package com.note.note.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Hashtag {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String hashtag;

	private Timestamp dateCreated;

	@Transient
	private List<Note> notes;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getHashtag()
	{
		return hashtag;
	}

	public void setHashtag(String hashtag)
	{
		this.hashtag = hashtag;
	}

	public Timestamp getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public List<Note> getNotes()
	{
		return notes;
	}

	public void setNotes(List<Note> notes)
	{
		this.notes = notes;
	}
}
