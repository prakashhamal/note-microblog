package com.note.note.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.OSSSymlink;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.note.note.model.Attachment;
import com.note.note.repository.AttachmentRepository;
import com.note.note.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class AttachmentServiceImpl  implements AttachmentService
{
	private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Value("${oss.bucketName}")
	private String bucketName;

	@Value("${oss.endPoint}")
	private String endpoint;

	@Value("${oss.keyId}")
	private String accessKeyId;

	@Value("${oss.keySecret}")
	private String accessKeySecret;

	private AttachmentRepository attachmentRepository;

	public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
		this.attachmentRepository = attachmentRepository;
	}

	

	@Override
	public  void uploadFile(String file)
	{
		OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, file);
			// The local file to upload---it must exist.
			uploadFileRequest.setUploadFile(file);
			// Sets the concurrent upload task number to 5.
			uploadFileRequest.setTaskNum(5);
			// Sets the part size to 1MB.
			uploadFileRequest.setPartSize(1024 * 1024 * 1);
			// Enables the checkpoint file. By default it's off.
			uploadFileRequest.setEnableCheckpoint(true);

			UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);

			CompleteMultipartUploadResult multipartUploadResult =
					uploadResult.getMultipartUploadResult();
			log.debug(multipartUploadResult.getETag());

		} catch (OSSException oe) {
			log.error("Caught an OSSException, which means your request made it to OSS, "
									   + "but was rejected with an error response for some reason.");
			log.error("Error Message: " + oe.getErrorMessage());
			log.error("Error Code:       " + oe.getErrorCode());
			log.error("Request ID:      " + oe.getRequestId());
			log.error("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			log.error("Caught an ClientException, which means the client encountered "
									   + "a serious internal problem while trying to communicate with OSS, "
									   + "such as not being able to access the network.");
			log.error("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		} finally {
			ossClient.shutdown();
		}

	}

	public  void attachNoteFile(MultipartFile file,int noteId)
	{
		OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			String fileName = file.getOriginalFilename();
			ossClient.putObject(bucketName,"note-attachments/"+noteId+"_"+fileName, new BufferedInputStream(file.getInputStream()));

			Attachment attachment = new Attachment();
			attachment.setFileName(fileName);
			attachment.setNoteId(noteId);
			attachment.setCreationDate(new Date());
			this.attachmentRepository.save(attachment);

		} catch (OSSException oe) {
			log.error("Caught an OSSException, which means your request made it to OSS, "
									   + "but was rejected with an error response for some reason.");
			log.error("Error Message: " + oe.getErrorMessage());
			log.error("Error Code:       " + oe.getErrorCode());
			log.error("Request ID:      " + oe.getRequestId());
			log.error("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			log.error("Caught an ClientException, which means the client encountered "
									   + "a serious internal problem while trying to communicate with OSS, "
									   + "such as not being able to access the network.");
			log.error("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		} finally {
			ossClient.shutdown();
		}

	}


	public  void deleteNoteAttachment(Attachment attachment,int noteId)
	{
		OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			ossClient.deleteObject(bucketName,"note-attachments/"+noteId+"_"+attachment.getFileName());
			this.attachmentRepository.deleteById(attachment.getId());

		} catch (OSSException oe) {
			log.error("Caught an OSSException, which means your request made it to OSS, "
							  + "but was rejected with an error response for some reason.");
			log.error("Error Message: " + oe.getErrorMessage());
			log.error("Error Code:       " + oe.getErrorCode());
			log.error("Request ID:      " + oe.getRequestId());
			log.error("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			log.error("Caught an ClientException, which means the client encountered "
							  + "a serious internal problem while trying to communicate with OSS, "
							  + "such as not being able to access the network.");
			log.error("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		} finally {
			ossClient.shutdown();
		}

	}


	public void getSharableLink(Attachment attachment){
		OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
			URL sharableUrl = ossClient.generatePresignedUrl(bucketName, "note-attachments/"+attachment.getNoteId()+"_"+attachment.getFileName(), expiration);
			attachment.setSharableUrl(sharableUrl.toURI().toString());

		} catch (OSSException oe) {
			log.error("Caught an OSSException, which means your request made it to OSS, "
							  + "but was rejected with an error response for some reason.");
			log.error("Error Message: " + oe.getErrorMessage());
			log.error("Error Code:       " + oe.getErrorCode());
			log.error("Request ID:      " + oe.getRequestId());
			log.error("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			log.error("Caught an ClientException, which means the client encountered "
							  + "a serious internal problem while trying to communicate with OSS, "
							  + "such as not being able to access the network.");
			log.error("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		} finally {
			ossClient.shutdown();
		}

	}
}
