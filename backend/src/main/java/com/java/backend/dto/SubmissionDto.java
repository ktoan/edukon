package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionDto extends AbstractDto {
    private String source;
    private UserDto student;
    private Double grade;
}
