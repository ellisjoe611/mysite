package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static final String SAVE_PATH = "/mysite-uploads";
//	private static final String URL = "/images";

	public String restore(MultipartFile multipartFile) {
		String profile = "";

		try {
			if (multipartFile.isEmpty()) {
				return "pika.jpg";
			}

			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);
			profile = generateSaveFilename(extName);
			long fileSize = multipartFile.getSize();

			System.out.println("######### " + originFilename);
			System.out.println("######### " + profile);
			System.out.println("######### " + fileSize + " bytes");

			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + profile);
			os.write(fileData);
			os.close();

		} catch (IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}

		return profile;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}
}
