package com.java.backend.util;

import org.springframework.web.multipart.MultipartFile;


public interface FileUtil {
	String uploadImage(MultipartFile multipartFile, String folder);

	String uploadVideo(MultipartFile multipartFile, String folder);

	String uploadFile(MultipartFile multipartFile, String folder);
}
