package com.note.note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Attachment
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer noteId;

	private String fileName;

	private Timestamp creationDate;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getNoteId()
	{
		return noteId;
	}

	public void setNoteId(Integer noteId)
	{
		this.noteId = noteId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Timestamp getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate)
	{
		this.creationDate = creationDate;
	}
}
