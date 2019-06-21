package com.note.note.repository;

import com.note.note.model.HashtagNote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface HashtagNoteRepository extends CrudRepository<HashtagNote, Integer>
{
	@Modifying
	@Query("delete  from HashtagNote  where noteId = ?1")
	@Transactional
	void deleteNoteHashtagLinks(int noteId);
}
