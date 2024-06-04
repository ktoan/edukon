package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CertificateDto extends AbstractDto {
	private String source;
	private CourseDto course;
	private Date expiredAt;
}
