package com.java.backend.service;

import com.java.backend.dto.CertificateDto;
import com.java.backend.entity.CertificateEntity;

import java.util.List;

public interface CertificateService {
    CertificateEntity saveCertificate(CertificateEntity certificate);
    List<CertificateDto> getStudentCertificates();
    CertificateDto getCompletedCertificate(Integer courseId);
    boolean isExistsCertificate(Integer courseId);
    CertificateEntity findCertificateEntityById(Integer certificateId);
}
