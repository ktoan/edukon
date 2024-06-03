package com.java.backend.controller;

import com.java.backend.request.EnrollRequest;
import com.java.backend.request.ExecutePaypalRequest;
import com.java.backend.service.EnrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Enroll")
public class EnrollController {
	private final EnrollService enrollService;

	@GetMapping("/course")
	@Operation(summary = "Get enrolled courses")
	public ResponseEntity<Object> getEnrolledCourse() {
        return new ResponseEntity<>(Map.of("success", true, "enrolled_courses", enrollService.getEnrolledCourse()),
                HttpStatus.OK);
    }

	@PostMapping("/create")
	@Operation(summary = "Create enroll")
	public ResponseEntity<Object> createEnroll(@Valid @RequestBody EnrollRequest enrollRequest) {
		return new ResponseEntity<>(Map.of("success", true, "return_url", enrollService.createPayment(enrollRequest)),
				HttpStatus.CREATED);
	}

	@PostMapping("/execute")
	@Operation(summary = "Execute enroll")
	public ResponseEntity<Object> executeEnroll(@Valid @RequestBody ExecutePaypalRequest executePaypalRequest) {
		return new ResponseEntity<>(
				Map.of("success", true, "is_enrolled", enrollService.isEnrollCourse("PAYPAL", executePaypalRequest)),
				HttpStatus.CREATED);
	}
}
