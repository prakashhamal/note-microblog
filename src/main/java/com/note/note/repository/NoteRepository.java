package com.note.note.repository;

import com.note.note.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NoteRepository extends CrudRepository<Note, Integer> {

	
	@Query("select n from Note n,HashtagNote hn,Hashtag h where n.id = hn.noteId and hn.hashtagId = h.id and h.hashtag = ?1 order by n.dateUpdated desc")
 	List<Note> getHashtagNotes(String hashtag);

	@Query(value = "select n.* from note n  order by date_updated desc limit ?1 offset ?2",nativeQuery=true)
	List<Note> getRecentNotes(int limit,int offset);

}