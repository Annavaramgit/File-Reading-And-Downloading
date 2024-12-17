package com.file_manager.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

	public static final String STORE = "C:\\Users\\Sreenivas Bandaru\\Desktop";

	/* It is for save the file in the specified location */
	public void saveFile(MultipartFile file) throws IOException {
		if (file == null) {
			throw new NullPointerException("File is null !!!!");

		}

		// checks the user given file name,that target file parent (path) should STORE path
		 
		File targetFile = new File(STORE + File.separator + file.getOriginalFilename());
		if (!Objects.equals(targetFile.getParent(), STORE)) {
			throw new SecurityException("File name not correct!!!");

		}
		Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}

	/* download the file */
	public File downloadFile(String fileName) throws Exception {
		log.info("downloadFile method entered: "+fileName);
		if (fileName == null) {
			
			throw new NullPointerException("file name is null!!");
		}
		File downloadFile = new File(STORE + File.separator + fileName);
		if (!Objects.equals(downloadFile.getParent(), STORE)) {
			log.info("file path checking");
			throw new SecurityException("File name not correct!!!");

		}
		if (!downloadFile.exists()) {
			log.info("file is there or not  checking");
			throw new FileNotFoundException("File is not found please once again !!!");

		}
		return downloadFile;
	}

}
