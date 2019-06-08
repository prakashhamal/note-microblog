package com.note.note.service.impl;

import com.note.note.dto.NoteSearchDto;
import com.note.note.dto.SearchResult;
import com.note.note.exceptions.ServiceException;
import com.note.note.model.Note;
import com.note.note.repository.NoteRepository;
import com.note.note.service.ElasticSearchService;
import com.note.note.service.NoteAnalyzer;
import com.note.note.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoteServiceImpl implements NoteService {


    private static final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);
    private NoteRepository noteRepository;
    private NoteAnalyzer noteAnalyzer;
    private ElasticSearchService elasticSearchService;

    public NoteServiceImpl(NoteRepository noteRepository,NoteAnalyzer noteAnalyzer, ElasticSearchService elasticSearchService){
        this.noteRepository = noteRepository;
        this.noteAnalyzer = noteAnalyzer;
        this.elasticSearchService = elasticSearchService;
    }

    public void analyzeNotes(){
       this.noteRepository.findAll().forEach(note -> {
           this.analyzeNote(note);
       });
    }

    public void analyzeNote(int id){
        Note note = this.getNoteById(id);
        this.analyzeNote(note);
    }

    public NoteAnalyzer getNoteAnalyzer()
    {
        return noteAnalyzer;
    }

    public void setNoteAnalyzer(NoteAnalyzer noteAnalyzer)
    {
        this.noteAnalyzer = noteAnalyzer;
    }

    public void analyzeNote(Note note){
        noteAnalyzer.analyzeNote(note);
        this.noteRepository.save(note);

    }

    public Note getNoteById(int id){
        Optional<Note> note = this.noteRepository.findById(id);
        return note.get();
    }

    @Override
    public void analyzeHashtagNotes(String hashtag)
    {
        List<Note> notes = this.noteRepository.getHashtagNotes(hashtag.toUpperCase());
        notes.forEach(note->{
            this.analyzeNote(note);
        });
    }

    @Override
    public List<Note> hashtagNotes(String hashtag)
    {
        return this.noteRepository.getHashtagNotes(hashtag.toUpperCase());
    }

    @Override
    public Note saveNote(Note note)
    {
        note.setDateCreated(new Date());
        Note savedNote = this.noteRepository.save(note);
        return savedNote;
    }

    @Override
    public void deleteNote(int id)
    {
        this.noteRepository.deleteById(id);
    }

    @Override
    public List<Note> recentNotes(int limit, int offset)
    {
        return this.noteRepository.getRecentNotes(limit,offset);
    }

    @Override
    public SearchResult<Note> searchNote(NoteSearchDto searchDto)
    {
        String search = String.format("{\"from\" : %d, \"size\" : %d,\"query\": {\"match\": {\"title\": \"%s\"}}}",searchDto.getOffset(),searchDto.getLimit(), searchDto.getSearchString());
        try {
            this.elasticSearchService.setIndex("brahman");
            return this.elasticSearchService.search("note",search,Note.class);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}


