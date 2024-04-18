package com.java.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Getter
@Setter
public abstract class AbstractDto {
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
}
