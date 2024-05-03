package com.java.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDto extends AbstractDto {
    private String name;
    private String thumbnail;
}
