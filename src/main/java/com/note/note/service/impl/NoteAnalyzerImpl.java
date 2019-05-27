package com.note.note.service.impl;

import com.note.note.model.Note;
import com.note.note.service.NoteAnalyzer;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoteAnalyzerImpl implements NoteAnalyzer
{
	public DateExtractor dateExtractor;
	public MonetaryAmountExtractor monetaryAmountExtractor;

	public NoteAnalyzerImpl(DateExtractor dateExtractor,
							MonetaryAmountExtractor currencyAmmountExtractor){
		this.dateExtractor = dateExtractor;
		this.monetaryAmountExtractor = currencyAmmountExtractor;
	}

	@Override
	public void analyzeNote(Note note)
	{
		System.out.println(String.format("Note : %s",note.getTitle()));
		Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
		Matcher mat = MY_PATTERN.matcher(note.getTitle());
		while (mat.find()) {
			String hashtag =   mat.group(1).toLowerCase();
			System.out.println("-----"+hashtag);
			switch(hashtag){
				case "expense":
					dateExtractor.extractValue(note);
					monetaryAmountExtractor.extractValue(note);
					break;
				default:
					System.out.println(String.format("Nothing to do with the hashtag %s", hashtag));
					break;
			}
		}
	}
}
