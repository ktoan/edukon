package com.java.backend.mapper;

import com.java.backend.dto.CertificateDto;
import com.java.backend.entity.CertificateEntity;

public interface CertificateMapper {
    CertificateDto toDto(CertificateEntity certificate);
}
