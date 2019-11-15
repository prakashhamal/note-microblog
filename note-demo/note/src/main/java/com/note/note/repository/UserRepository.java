package com.note.note.repository;

import com.note.note.model.Hashtag;
import com.note.note.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>
{

}