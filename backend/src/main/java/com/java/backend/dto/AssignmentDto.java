package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AssignmentDto extends AbstractDto {
	private String title;
	private String requirement;
	private String content;
	private LocalDate deadline;
}
