package com.note.note.component;

import com.note.note.exceptions.ServiceException;
import com.note.note.service.NoteService;
import com.note.note.service.impl.NoteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduledTasks
{
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private NoteService noteService;

	public ScheduledTasks(NoteService noteService){
		this.noteService = noteService;
	}

	@Scheduled(cron = "${cron:0 * * * * *}", zone = "${crontz:America/New_York}")
	public void scheduledDedupeScrappedBids() throws ServiceException
	{
		long start = System.currentTimeMillis();
		log.info("Starting the note processing batch.");
		try {
			noteService.processUnprocessedNotes();

		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
		log.info("Finished the note processing batch. Elapsed time -> {} ms",System.currentTimeMillis()-start);
	}



}
