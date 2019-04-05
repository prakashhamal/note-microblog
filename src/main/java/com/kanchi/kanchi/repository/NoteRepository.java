package com.kanchi.kanchi.repository;

import com.kanchi.kanchi.model.Note;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NoteRepository extends CrudRepository<Note, Integer> {

}