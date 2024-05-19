package com.java.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	private UserDto instructor;
	private CategoryDto category;
	private List<VideoDto> videos;
	private List<ReviewDto> reviews;
	@JsonProperty("is_enrolled")
	private boolean isEnrolled = false;
}
