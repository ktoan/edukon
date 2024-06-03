package com.java.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "certificates")
@Getter
@Setter
public class CertificateEntity extends AbstractEntity {
    private String source;

}
