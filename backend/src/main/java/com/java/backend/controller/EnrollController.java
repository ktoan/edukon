package com.java.backend.controller;

import com.java.backend.request.EnrollRequest;
import com.java.backend.service.EnrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/enroll")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class EnrollController {
	private final EnrollService enrollService;

	@PostMapping("/create")
	public ResponseEntity<Object> createEnroll(@Valid @RequestBody EnrollRequest enrollRequest) {
		return new ResponseEntity<>(Map.of("success", true, "return_url", enrollService.createPayment(enrollRequest)),
				HttpStatus.CREATED);
	}
}
