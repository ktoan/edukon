package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BlogRequest {
	@Required(fieldName = "Title")
	@MinMaxSize(min = 10, fieldName = "Title")
	@JsonProperty("title")
	private String title;
	@Required(fieldName = "Pre description")
	@MinMaxSize(min = 30, fieldName = "Pre description")
	@JsonProperty("pre_description")
	private String preDescription;
	@Required(fieldName = "Content")
	@JsonProperty("content")
	private String content;
	@Required(fieldName = "Thumbnail")
	@JsonProperty("thumbnail")
	private MultipartFile thumbnail;
	@Required(fieldName = "Category")
	@JsonProperty("category_id")
	private Integer categoryId;
}
