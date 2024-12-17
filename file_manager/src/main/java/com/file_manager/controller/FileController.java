package com.file_manager.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file_manager.service.FileService;


import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileController {
	
	public FileController(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	private FileService fileService;

	/*save the folder in specified path*/
	@PostMapping("/upload-file")
	public boolean uploadFile(@RequestParam MultipartFile file) {
		try {
			fileService.saveFile(file);
			return true;
		} catch (Exception e) {
			log.info("Error is: " + e.toString());
		}
		return false;

	}
   /*get the folder in the path*/
	@GetMapping("/download-file")
	public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("filename")String filename) {
		try {
			File downloadedFile = fileService.downloadFile(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"")
					.contentLength(downloadedFile.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(downloadedFile));

		} catch (Exception e) {
			 return ResponseEntity.notFound().
					 build();
		}
	}

}
