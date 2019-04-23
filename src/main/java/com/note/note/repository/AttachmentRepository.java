package com.note.note.repository;

import com.note.note.model.Attachment;
import com.note.note.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer>
{

}
