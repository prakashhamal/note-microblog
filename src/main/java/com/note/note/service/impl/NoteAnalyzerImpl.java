package com.note.note.service.impl;

import com.note.note.model.Note;
import com.note.note.service.NoteAnalyzer;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoteAnalyzerImpl implements NoteAnalyzer
{
	public DateExtractor dateExtractor;
	public CurrencyAmmountExtractor currencyAmmountExtractor;

	public NoteAnalyzerImpl(DateExtractor dateExtractor,
							CurrencyAmmountExtractor currencyAmmountExtractor){
		this.dateExtractor = dateExtractor;
		this.currencyAmmountExtractor = currencyAmmountExtractor;
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
					currencyAmmountExtractor.extractValue(note);
					break;
				default:
					System.out.println(String.format("Nothing to do with the hashtag %s", hashtag));
					break;
			}
		}
	}
}
