package com.java.backend.request;

import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CourseRequest {
	@Required(fieldName = "Course name")
	private String name;
	@Required(fieldName = "Pre description")
	@MinMaxSize(min = 30, fieldName = "Pre description")
	private String preDescription;
	@Required(fieldName = "Content")
	private String content;
	@Required(fieldName = "Thumbnail")
	private MultipartFile thumbnail;
	@Required(fieldName = "Price")
	private Double price;
	@Required(fieldName = "Category")
	private Integer categoryId;
}
