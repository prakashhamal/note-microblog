package com.note.note.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Note {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private Date  dateCreated;

    private Date dateUpdated;

    private Date dateProcessed;

    private Boolean archived;

    @Transient
    private List<Attachment> attachmentList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated()
    {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    public Date getDateProcessed()
    {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed)
    {
        this.dateProcessed = dateProcessed;
    }

    public Boolean getArchived()
    {
        return archived;
    }

    public void setArchived(Boolean archived)
    {
        this.archived = archived;
    }

    public List<Attachment> getAttachmentList()
    {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList)
    {
        this.attachmentList = attachmentList;
    }

    @Override
    public String toString()
    {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", dateProcessed=" + dateProcessed +
                ", archived=" + archived +
                '}';
    }
}
