package com.note.note.service.impl;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.note.note.model.Note;
import com.note.note.service.ValueExtractor;
import com.note.note.utils.AnalyzerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import net.rationalminds.LocalDateModel;

@Service
public class DateExtractor  implements ValueExtractor, AnalyzerUtils
{

	private static final Logger log = LoggerFactory.getLogger(DateExtractor.class);
	private static final String DATE = "date";

	public void extractValue(Note note)
	{
		if(!extractValueUsingNatty(note)){
			log.error("Natty lib was not able to parse date of noteid :  "+note.getId());
			try {
				extractDateUsingRationalminds(note);
			}
			catch (ParseException e) {
				log.error("Error while extracting date using rationalminds."+e.getMessage(),e);
			}
		}
	}

	private boolean extractValueUsingNatty(Note note){
		boolean success = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		Long epochMilli = parseKnownDateFormat(note.getTitle());
		Date date = null;
		if (epochMilli == null) {
			com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
			List<DateGroup> groups = parser.parse(note.getTitle());
			if(!groups.isEmpty() && !groups.get(0).getDates().isEmpty()) {
				date = groups.get(0).getDates().get(0);
				setValue(note, DATE, simpleDateFormat.format(date));
				success = true;
			}
		}
		return success;
	}

	private void extractDateUsingRationalminds(Note note) throws ParseException
	{
		SimpleDateFormat standardDF = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		net.rationalminds.Parser parser=new net.rationalminds.Parser();
		List<LocalDateModel> dates=parser.parse(note.getTitle());
		if(dates != null && !dates.isEmpty()){
			LocalDateModel localDateModel = dates.get(0);
			localDateModel.getDateTimeString();
			SimpleDateFormat localDF = new SimpleDateFormat(localDateModel.getConDateFormat());
			Date date = localDF.parse(localDateModel.getDateTimeString());
			setValue(note,DATE,standardDF.format(date));
		}
	}


	private Long parseKnownDateFormat(String dateStr)
	{
		Map<String, String> dateFormats = new HashMap<>();
		dateFormats.put("yyyy-MM-dd'T'HH:mm:ss.SSSz", "[0-9]{4}-[0-9]{2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z");
		dateFormats.put("yyyy-MM-dd'T'HH:mm:ssz", "[0-9]{4}-[0-9]{2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z");
		dateFormats.put("yyyy-MM-dd'T'HH:mm:ss z", "[0-9]{4}-[0-9]{2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2} UTC");
		dateFormats.put("dd-MMM-yyyy HH:mm", "[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4} [0-9]{2}:[0-9]{2}");
		dateFormats.put("dd-MMM-yy", "[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{2}");
		dateFormats.put("dd-MMMM-yy", "[0-9]{1,2}-[a-zA-Z]{3,9}-[0-9]{2}");
		dateFormats.put("dd-MMMM-yy", "[0-9]{1,2}-[a-zA-Z]{3,9}-[0-9]{2}");
		dateFormats.put("dd-MMM-yyyy", "[0-9]{1,2}-[0-9]{3}-[0-9]{4}");

		for (Map.Entry<String, String> keyVal : dateFormats.entrySet()) {
			if (dateStr.matches(keyVal.getValue())) {
				long epochMilli = ZonedDateTime.parse(dateStr, DateTimeFormatter.ofPattern(keyVal.getKey()))
						.toInstant()
						.toEpochMilli();
				return epochMilli;
			}
		}
		return null;
	}
}
