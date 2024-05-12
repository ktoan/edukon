package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.MinMaxSize;
import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CourseRequest {
	@Required(fieldName = "Course name")
	@JsonProperty("name")
	private String name;
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
	@Required(fieldName = "Price")
	@JsonProperty("price")
	private Double price;
	@Required(fieldName = "Category")
	@JsonProperty("category_id")
	private Integer categoryId;
}
