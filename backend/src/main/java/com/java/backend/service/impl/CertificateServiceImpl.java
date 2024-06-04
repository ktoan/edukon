package com.java.backend.service.impl;

import com.java.backend.dto.CertificateDto;
import com.java.backend.entity.CertificateEntity;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.exception.BadRequestException;
import com.java.backend.mapper.CertificateMapper;
import com.java.backend.repository.CertificateRepository;
import com.java.backend.repository.CourseRepository;
import com.java.backend.service.CertificateService;
import com.java.backend.service.CourseService;
import com.java.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final ContextUtil contextUtil;
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @Override
    public CertificateEntity saveCertificate(CertificateEntity certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public List<CertificateDto> getStudentCertificates() {
        return contextUtil.loadUserFromContext().getCertificates().stream().map(certificateMapper::toDto).toList();
    }

    @Override
    public CertificateDto getCompletedCertificate(Integer courseId) {
        if (isExistsCertificate(courseId)) {
            throw new BadRequestException("User already had a certificate for this course!");
        }

        return null;
    }

    @Override
    public boolean isExistsCertificate(Integer courseId) {
        UserEntity user = contextUtil.loadUserFromContext();
        CourseEntity course = courseService.findCourseEntityById(courseId);
        return certificateRepository.existsByCourseAndUser(course, user);
    }

    @Override
    public CertificateEntity findCertificateEntityById(Integer certificateId) {
        return null;
    }
}
