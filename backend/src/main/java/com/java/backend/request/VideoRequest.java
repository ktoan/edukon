package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty("title")
	private String title;
	@NotNull(message = "Pre description is required!")
	@NotBlank(message = "Pre description is required!")
	@JsonProperty("pre_description")
	private String preDescription;
	@Required(fieldName = "Video")
	@JsonProperty("video")
	private MultipartFile video;
	@NotNull(message = "Course is required!")
	@JsonProperty("course_id")
	private Integer courseId;
}
