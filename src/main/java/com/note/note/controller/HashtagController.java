package com.note.note.controller;


import com.note.note.model.Hashtag;
import com.note.note.service.HashtagService;
import com.note.note.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<Hashtag>> getRecentHashtags() {
        List<Hashtag> hashtags =this.hashtagService.getRecentHashtags();
        return ResponseEntity.ok().body(hashtags);
    }


    @GetMapping(path="/lookup/{input}")
    public ResponseEntity<List<String>> lookup(@PathVariable("input") String input) {
        List<String> hashtags = this.hashtagService.lookupHashtag(input);
        return ResponseEntity.ok().body(hashtags);
    }

    @GetMapping(path="/buildCache")
    public ResponseEntity<String> buildCache() {
       this.hashtagService.buildHashtagsCache(1);
       
       return ResponseEntity.ok("Success");
    }

    @GetMapping(path="/destroyCache")
    public ResponseEntity<String> destroyCache() {
        this.hashtagService.destroyHashtagsCache(1);
        return ResponseEntity.ok("Success");
    }

}
