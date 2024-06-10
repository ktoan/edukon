package com.java.backend.util.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itextpdf.html2pdf.HtmlConverter;
import com.java.backend.enums.CertificateRank;
import com.java.backend.exception.InternalServerException;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileUtilImpl implements FileUtil {
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

	public String uploadFile(MultipartFile file, String folder) {
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("resource_type", "auto", "folder",
							folder != null ? "edukon/" + folder : "edukon/documents"));
			return uploadResult.get("url").toString();
		} catch (IOException e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	@Override
	public String generateCertificate(String username, String course, String rank) {
		String htmlFilePath = "src\\main\\resources\\templates\\certificate.html";
		String outputFileName = System.currentTimeMillis() + "_" + username + "_" + course + ".pdf";
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
		try {
			String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)), "UTF-8");
			htmlContent = htmlContent.replace("{{username}}", username);
			htmlContent = htmlContent.replace("{{course}}", course);
			htmlContent = htmlContent.replace("{{rank}}",
					Objects.equals(rank, CertificateRank.EXCELLENT.name()) ? "Excellence" : Objects.equals(rank,
							CertificateRank.GOOD.name()) ? "Good" : "Average");
			htmlContent = htmlContent.replace("{{date}}", sdf.format(new Date()));
			HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(outputFileName));
			File toUpload = new File(outputFileName);
			Map uploadResult = cloudinary.uploader()
					.upload(toUpload, ObjectUtils.asMap("resource_type", "auto", "folder", "edukon/certificates"));
			toUpload.delete();
			return uploadResult.get("url").toString();
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println("Error creating PDF: " + e.getMessage());
			return null;
		}
	}
}
