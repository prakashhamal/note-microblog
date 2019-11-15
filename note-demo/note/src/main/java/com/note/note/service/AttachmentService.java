package com.note.note.service;

import com.note.note.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService
{
	void uploadFile(String fileName);

	void attachNoteFile(MultipartFile file,int noteId);

	void deleteNoteAttachment(Attachment attachment, int noteId);

	void getSharableLink(Attachment attachment);
}
