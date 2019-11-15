package com.note.note.model;

import javax.persistence.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Attachment
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer noteId;

	private String fileName;

	private Date creationDate;

	@Transient
	private String sharableUrl;

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

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getSharableUrl()
	{
		return sharableUrl;
	}

	public void setSharableUrl(String sharableUrl)
	{
		this.sharableUrl = sharableUrl;
	}
}
