package com.note.note.controller;


import com.note.note.model.Hashtag;
import com.note.note.service.HashtagService;
import com.note.note.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/hashtag") // This means URL's start with /demo (after Application path)
public class HashtagController
{

    private HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService){
        this.hashtagService = hashtagService;
    }
    @GetMapping(path="/all")
    public ResponseEntity<List>  getAllHashtags() {
       List<Hashtag> hashtags = this.hashtagService.getAllHashtags();
       return ResponseEntity.ok().body(hashtags);
    }


    @GetMapping(path="/recent")
    public @ResponseBody String getRecentHashtags() {
        this.hashtagService.getRecentHashtags();
        return "Successfully analyzed the notes";
    }


    @GetMapping(path="/lookup")
    public @ResponseBody String lookup() {
        this.hashtagService.lookupHashtag("");
        return "Successfully analyzed the notes";
    }

}
