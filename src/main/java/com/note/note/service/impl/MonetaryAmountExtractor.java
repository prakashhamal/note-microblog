package com.note.note.service.impl;

import com.note.note.model.Note;
import com.note.note.service.ValueExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

@Service
public class MonetaryAmountExtractor implements ValueExtractor
{
	private static final Logger log = LoggerFactory.getLogger(MonetaryAmountExtractor.class);
	public void extractValue(Note note){
		StringTokenizer st = new StringTokenizer(note.getTitle());
		double amount = 0.0;
		while (st.hasMoreTokens()) {
			try {
				Number number = NumberFormat.getCurrencyInstance(Locale.US).parse(st.nextToken());
				amount = number.doubleValue();
				System.out.println("Ammount  : "+number.doubleValue());
				log.error("Ammount "+number.doubleValue());
			}
			catch (ParseException e) {
				System.out.println("Not a valid currency token. "+e.getMessage());
			}
		}
	  	setValue(note,"currency_amount",amount);
	}
}
