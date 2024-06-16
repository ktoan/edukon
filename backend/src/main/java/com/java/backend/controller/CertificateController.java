package com.java.backend.controller;

import com.java.backend.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/certificate")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Certificate")
public class CertificateController {
	private final CertificateService certificateService;

	@GetMapping("/get")
	@Operation(summary = "Get certificate for course")
	public ResponseEntity<?> getCourseCertificate(@RequestParam Integer courseId) {
		return new ResponseEntity<>(
				Map.of("success", true, "certificate", certificateService.getCompletedCertificate(courseId)),
				HttpStatus.CREATED);
	}

	@GetMapping("")
	@Operation(summary = "Get certificates of users")
	public ResponseEntity<?> getUserCertificates() {
		return new ResponseEntity<>(
				Map.of("success", true, "certificates", certificateService.getStudentCertificates()),
				HttpStatus.OK);
	}
}
