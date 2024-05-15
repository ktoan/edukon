package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryRequest {
	@Required(fieldName = "Category name")
	@JsonProperty("name")
	private String name;
	@Required(fieldName = "Thumbnail")
	@JsonProperty("thumbnail")
	private MultipartFile thumbnail;
}
