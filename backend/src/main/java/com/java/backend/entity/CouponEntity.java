package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Entity
@Table(name = "coupons")
@Getter
@Setter
public class CouponEntity extends AbstractEntity{
    private String title;
    private String preDescription;
    private Integer discountPercent;
    private Integer amount;
    private Date expiredAt;
}
