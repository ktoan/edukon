package com.java.backend.request;

import com.java.backend.annotation.Required;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class VideoRequest {
	@NotNull(message = "Title is required!")
	@NotBlank(message = "Title is required!")
	private String title;
	@NotNull(message = "Pre description is required!")
	@NotBlank(message = "Pre description is required!")
	private String preDescription;
	@Required(fieldName = "Video")
	private MultipartFile video;
	@NotNull(message = "Course is required!")
	private Integer courseId;
}
