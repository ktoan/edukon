package com.java.backend.request;

import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class AssignmentRequest {
	@Required(fieldName = "Title")
	private String title;
	@Required(fieldName = "Requirement")
	private String requirement;
	@Required(fieldName = "Content")
	private String content;
	@Required(fieldName = "File")
	private MultipartFile file;
	@Required(fieldName = "Deadline")
	private LocalDate deadline;
	@Required(fieldName = "Video")
	private Integer videoId;
}
