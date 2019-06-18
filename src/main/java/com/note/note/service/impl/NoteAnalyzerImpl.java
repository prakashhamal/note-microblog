package com.note.note.service.impl;

import com.note.note.model.Note;
import com.note.note.service.HashtagProcessorService;
import com.note.note.service.NoteAnalyzer;
import com.note.note.service.ValueExtractor;
import com.note.note.utils.AnalyzerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoteAnalyzerImpl implements NoteAnalyzer, AnalyzerUtils
{
	private static final Logger log = LoggerFactory.getLogger(NoteAnalyzerImpl.class);

	public DateExtractor dateExtractor;
	public MonetaryAmountExtractor monetaryAmountExtractor;
	public HashtagProcessorService hashtagProcessorService;

	public NoteAnalyzerImpl(DateExtractor dateExtractor,
							MonetaryAmountExtractor currencyAmmountExtractor,
							HashtagProcessorService hashtagProcessorService){
		this.dateExtractor = dateExtractor;
		this.monetaryAmountExtractor = currencyAmmountExtractor;
		this.hashtagProcessorService = hashtagProcessorService;
	}

	@Override
	public void analyzeNote(Note note)
	{
		log.info("Analyzing note {} {}",note.getId(),note.getTitle());
		Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
		Matcher mat = MY_PATTERN.matcher(note.getTitle());
		List<String> hashtags = new ArrayList();
		while (mat.find()) {
			hashtags.add(mat.group(1).toLowerCase());
		}
		setValue(note,"hashtags",hashtags);

		hashtags.forEach(hashtag->{
			switch(hashtag){
				case "expense":
					dateExtractor.extractValue(note);
					monetaryAmountExtractor.extractValue(note);
					break;
				default:
					log.info(String.format("Nothing to do with the hashtag %s", hashtag));
					break;
			}
		});
		this.hashtagProcessorService.processHashtagsOfNote(note);
		note.setDateProcessed(new Date());
		//Save and link hashtags
		//Index the note to elasticsearch.
	}
}
