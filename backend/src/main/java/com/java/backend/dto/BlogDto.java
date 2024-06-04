package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BlogDto extends AbstractDto {
	private String title;
	private String preDescription;
	private String content;
	private String thumbnail;
	private boolean isApproved;
	private UserDto author;
	private CategoryDto category;
	private Set<CommentDto> comments;
}
