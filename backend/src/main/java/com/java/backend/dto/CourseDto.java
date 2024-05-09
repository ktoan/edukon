package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDto extends AbstractDto {
	private String name;
	private String preDescription;
	private String content;
	private String thumbnail;
	private double price;
	private boolean isApproved;
	private UserDto instructor;
	private CategoryDto category;
	private List<VideoDto> videos;
}