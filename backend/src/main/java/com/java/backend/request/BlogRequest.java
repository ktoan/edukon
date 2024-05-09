package com.java.backend.request;

import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BlogRequest {
	@Required(fieldName = "Title")
	@MinMaxSize(min = 10, fieldName = "Title")
	private String title;
	@Required(fieldName = "Pre description")
	@MinMaxSize(min = 30, fieldName = "Pre description")
	private String preDescription;
	@Required(fieldName = "Content")
	private String content;
	@Required(fieldName = "Thumbnail")
	private MultipartFile thumbnail;
	@Required(fieldName = "Category")
	private Integer categoryId;
}
