package com.java.backend.controller;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.request.AssignmentRequest;
import com.java.backend.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/assignment")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AssignmentController {
	private final AssignmentService assignmentService;

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> createAssignment(@Valid @ModelAttribute AssignmentRequest assignmentRequest) {
		AssignmentDto newAssignment = assignmentService.createAssignment(assignmentRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_assignment", newAssignment), HttpStatus.CREATED);
	}
}
