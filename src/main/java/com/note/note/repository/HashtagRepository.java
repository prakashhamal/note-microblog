package com.note.note.repository;

import com.note.note.model.Hashtag;
import com.note.note.model.Note;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface HashtagRepository extends CrudRepository<Hashtag, Integer> {

}