package com.note.note.service.impl;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.note.note.model.Note;
import com.note.note.service.ValueExtractor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DateExtractor  extends ValueExtractor
{

	private static final Logger log = LoggerFactory.getLogger(DateExtractor.class);


	public void extractValue(Note note)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		Long epochMilli = parseKnownDateFormat(note.getTitle());
		Date date = null;
		if (epochMilli == null) {
			Parser parser = new Parser();
			List<DateGroup> groups = parser.parse(note.getTitle());
			date = groups.get(0).getDates().get(0);
		}
		setValue(note,"date",date);
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
