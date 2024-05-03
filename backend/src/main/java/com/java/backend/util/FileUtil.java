package com.java.backend.util;

import com.cloudinary.Cloudinary;
import com.java.backend.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class FileUtil {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile multipartFile, String folder) {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString() + System.currentTimeMillis(),
                                    "folder", folder == null ? "edukon/images" : "edukon/" + folder)).get("url")
                    .toString();
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}
