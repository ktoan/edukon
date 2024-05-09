package com.java.backend.request;

import com.java.backend.annotation.Required;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryRequest {
	@Required(fieldName = "Category name")
	private String name;
	@Required(message = "Thumbnail")
	private MultipartFile thumbnail;
}
