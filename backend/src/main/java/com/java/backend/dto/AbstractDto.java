package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractDto {
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
}
