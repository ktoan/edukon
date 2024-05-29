package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDto extends AbstractDto {
	private String title;
	private String preDescription;
	private String source;
	private VideoDto video;
	private boolean isTracked;
	private AssignmentDto assignmentDto;
}
