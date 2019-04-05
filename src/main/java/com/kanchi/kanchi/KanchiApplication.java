package com.kanchi.kanchi;

import com.kanchi.kanchi.repository.NoteRepository;
import com.kanchi.kanchi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanchiApplication {


	public static void main(String[] args) {
		SpringApplication.run(KanchiApplication.class, args);
	}

}

