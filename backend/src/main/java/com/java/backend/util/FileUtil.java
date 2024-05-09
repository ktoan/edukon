package com.java.backend.util;

import com.cloudinary.Cloudinary;
import com.java.backend.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUtil {
	private final Cloudinary cloudinary;

	public String uploadImage(MultipartFile multipartFile, String folder) {
		try {
			return cloudinary.uploader().upload(multipartFile.getBytes(),
					Map.of("public_id", UUID.randomUUID().toString() + System.currentTimeMillis(), "folder",
							folder == null ? "edukon/images" : "edukon/" + folder)).get("url").toString();
		} catch (IOException e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public String uploadVideo(MultipartFile multipartFile, String folder) {
		try {
			Map<Object, Object> params = new HashMap<>();
			params.put("resource_type", "video");
			params.put("folder", folder != null ? "edukon/" + folder : "edukon/videos");
			params.put("public_id", UUID.randomUUID().toString() + System.currentTimeMillis());

			// Using input stream instead of getBytes to avoid loading large files into memory
			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
			multipartFile.transferTo(convFile);
			Map uploadResult = cloudinary.uploader().upload(convFile, params);
			return uploadResult.get("url").toString();
		} catch (IOException e) {
			throw new InternalServerException(e.getMessage());
		}
	}
}
