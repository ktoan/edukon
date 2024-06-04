package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryDto extends AbstractDto {
	private String name;
	private String thumbnail;
}
