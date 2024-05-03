package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@Data
public class CategoryEntity extends AbstractEntity {
    private String name;
    private String thumbnail;
}
