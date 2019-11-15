package com.note.note.service.impl;

import com.google.gson.Gson;
import com.note.note.dto.NoteSearchDto;
import com.note.note.dto.SearchResult;
import com.note.note.exceptions.ServiceException;
import com.note.note.model.Attachment;
import com.note.note.model.Note;
import com.note.note.repository.AttachmentRepository;
import com.note.note.repository.NoteRepository;
import com.note.note.service.AttachmentService;
import com.note.note.service.ElasticSearchService;
import com.note.note.service.NoteAnalyzer;
import com.note.note.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
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
    private AttachmentRepository attachmentRepository;
    private AttachmentService attachmentService;

    public NoteServiceImpl(NoteRepository noteRepository,NoteAnalyzer noteAnalyzer, ElasticSearchService elasticSearchService, AttachmentRepository attachmentRepository,AttachmentService attachmentService){
        this.noteRepository = noteRepository;
        this.noteAnalyzer = noteAnalyzer;
        this.elasticSearchService = elasticSearchService;
        this.attachmentRepository = attachmentRepository;
        this.attachmentService = attachmentService;
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
        log.info("Processing note {} : {}",note.getId(),note.getTitle() );
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
        List<Note> notes = this.noteRepository.getHashtagNotes(hashtag.toUpperCase());
        notes.forEach(note->{
            this.loadNoteAttachments(note);
        });
        return notes;
    }                                 

    @Override
    public Note saveNote(Note note)
    {
        if(note.getId() == null) {
            note.setDateCreated(new Date());
        }
        note.setDateUpdated(new Date());
        Note savedNote = this.noteRepository.save(note);


        this.elasticSearchService.setIndex(ElasticSearchServiceImpl.BRAHMAN_INDEX);
        //TODO save the hashtags and link to the hastags

        Gson gson = new Gson();
        String noteJson = gson.toJson(note);
        try {
            this.elasticSearchService.index("note",Integer.toString(note.getId()),noteJson);
        }
        catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        this.loadNoteAttachments(savedNote);
        

        return savedNote;
    }

    @Override
    public void deleteNote(int id)
    {
        this.noteRepository.deleteById(id);
        try {
            this.elasticSearchService.setIndex(ElasticSearchServiceImpl.BRAHMAN_INDEX);
            this.elasticSearchService.remove("note",Integer.toString(id));
        }
        catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(),e);
        }
        List<Attachment> noteAttachments = this.attachmentRepository.getAllByNoteId(id);
        noteAttachments.forEach(attachment -> {
            this.attachmentService.deleteNoteAttachment(attachment,id);
        });
    }

    @Override
    public List<Note> recentNotes(int limit, int offset)
    {
        List<Note> recentNotes = this.noteRepository.getRecentNotes(limit,offset);
        recentNotes.forEach(note->{
            this.loadNoteAttachments(note);
        });
        return recentNotes;
    }

    @Override
    public SearchResult<Note> searchNote(NoteSearchDto searchDto)
    {
        String search = String.format("{\"from\" : %d, \"size\" : %d,\"query\": {\"match\": {\"title\": \"%s\"}}}",searchDto.getOffset(),searchDto.getLimit(), searchDto.getSearchString());
        try {
            this.elasticSearchService.setIndex(ElasticSearchServiceImpl.BRAHMAN_INDEX);
            return this.elasticSearchService.search("note",search,Note.class);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public void processUnprocessedNotes()
    {
        List<Note> unprocessedNotes = this.noteRepository.getUnprocessedNotesSinceLastSave();
        unprocessedNotes.forEach(note->{
            try {
                this.analyzeNote(note);
            }catch(Exception e){
                log.error("Error processing note {}",note.getId(),e);

            }
        });
    }

    private void loadNoteAttachments(Note note){
        List<Attachment> attachments = this.attachmentRepository.getAllByNoteId(note.getId());
        attachments.forEach(attachment -> {
            this.attachmentService.getSharableLink(attachment);
        });
        note.setAttachmentList(attachments);
    }


}


