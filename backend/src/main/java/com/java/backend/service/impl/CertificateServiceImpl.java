package com.java.backend.service.impl;

import com.java.backend.dto.CertificateDto;
import com.java.backend.entity.CertificateEntity;
import com.java.backend.entity.CourseEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.enums.CertificateRank;
import com.java.backend.exception.BadRequestException;
import com.java.backend.mapper.CertificateMapper;
import com.java.backend.repository.CertificateRepository;
import com.java.backend.service.CertificateService;
import com.java.backend.service.CourseService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
	private final CertificateRepository certificateRepository;
	private final CertificateMapper certificateMapper;
	private final ContextUtil contextUtil;
	private final CourseService courseService;
	private final FileUtil fileUtil;

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
		UserEntity user = contextUtil.loadUserFromContext();
		CourseEntity course = courseService.findCourseEntityById(courseId);
		String source = fileUtil.generateCertificate(user.getName(), course.getName(),
				CertificateRank.EXCELLENT.name());
		if (source != null) {
			CertificateEntity newCertificate = new CertificateEntity();
			newCertificate.setCourse(course);
			newCertificate.setUser(user);
			newCertificate.setSource(source);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 2);
			Date twoYearsLater = calendar.getTime();
			newCertificate.setExpiredAt(twoYearsLater);

			newCertificate = saveCertificate(newCertificate);
			return certificateMapper.toDto(newCertificate);
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
