package com.java.backend.controller;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.request.AssignmentRequest;
import com.java.backend.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Assignment")
public class AssignmentController {
	private final AssignmentService assignmentService;

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Create assignment for a video")
	public ResponseEntity<?> createAssignment(@Valid @ModelAttribute AssignmentRequest assignmentRequest) {
		AssignmentDto newAssignment = assignmentService.createAssignment(assignmentRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_assignment", newAssignment), HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Update assignment by id")
	public ResponseEntity<?> updateAssignment(@RequestParam Integer assignmentId,
	                                          @Valid @ModelAttribute AssignmentRequest assignmentRequest) {
		AssignmentDto updatedAssignment = assignmentService.updateAssignment(assignmentId, assignmentRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_assignment", updatedAssignment),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete assignment by id")
	public ResponseEntity<?> deleteAssignment(@RequestParam Integer assignmentId) {
		assignmentService.deleteAssignment(assignmentId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Delete assignment successfully!"),
				HttpStatus.CREATED);
	}
}
