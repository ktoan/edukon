package com.java.backend.controller;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.request.SubmissionRequest;
import com.java.backend.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/submission")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Submission")
public class SubmissionController {
	private final SubmissionService submissionService;

	@PostMapping("/create")
	@Operation(summary = "Create new submission")
	public ResponseEntity<?> createSubmission(@Valid @ModelAttribute SubmissionRequest submissionRequest) {
		SubmissionDto newSubmission = submissionService.createSubmission(submissionRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_submission", newSubmission), HttpStatus.CREATED);
	}
}
