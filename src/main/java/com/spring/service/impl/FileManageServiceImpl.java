package com.spring.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.exception.FileStorageException;
import com.spring.exception.Response;
import com.spring.exception.ResponseBuilder;


public class FileManageServiceImpl {
	public static String uploadFile(MultipartFile file, String category, String productName, String defaultFilePath)
			throws Exception {
		String filePath = "";
		if (file.isEmpty()) {
			throw new FileStorageException("File not Found, Please Select your file " + file.getOriginalFilename());
		}
		try {
			String message = createFolder(defaultFilePath, category, productName);
			if (!(StringUtils.isEmpty(message) && message.equalsIgnoreCase("folder created successfulluy"))
					|| (StringUtils.isEmpty(message))) {
				byte[] bytes = file.getBytes();
				if (file.getOriginalFilename() != null) {
					filePath = defaultFilePath + "\\" + category + "\\" + productName + "\\"
							+ file.getOriginalFilename();
					Path path = Paths.get(filePath);
					Files.write(path, bytes);
				}
			} else {
				throw new FileStorageException("Failed to store file " + file.getOriginalFilename());
			}
		} catch (IOException e) {
			throw new FileStorageException("File can not store " + file.getOriginalFilename(), e);
		}
		return filePath;
	}

	private static String createFolder(String folderpath, String category, String productName) {

		String message = "";
		File folder = new File(folderpath + "/" + category);

		if (!folder.exists()) {
			if (folder.mkdir()) {
				message = "category folder created successfulluy";
			} else {
				message = "category folder alredy existed";
			}
		}

		File file = new File(folderpath + "/" + category + "/" + productName);
		if (!file.exists()) {
			if (file.mkdir()) {
				message = "subCategory folder created successfulluy";
			} else {
				message = "subCategory folder alredy existed";
			}
		}
		return message;
	}


}
