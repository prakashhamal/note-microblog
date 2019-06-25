package com.note.note.repository;

import com.note.note.model.Attachment;
import com.note.note.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer>
{
	List<Attachment> getAllByNoteId(int noteId);
}
