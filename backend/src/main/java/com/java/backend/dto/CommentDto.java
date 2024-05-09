package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CommentDto extends AbstractDto {
    private String comment;
    private UserDto user;
    private Set<CommentDto> childList = new HashSet<>();
}
