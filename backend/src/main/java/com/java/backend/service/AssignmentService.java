package com.java.backend.service;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.entity.AssignmentEntity;
import com.java.backend.request.AssignmentRequest;

public interface AssignmentService {
	AssignmentEntity saveAssignment(AssignmentEntity assignment);

	AssignmentDto createAssignment(AssignmentRequest assignmentRequest);
	AssignmentDto updateAssignment(Integer assignmentId, AssignmentRequest assignmentRequest);
	void deleteAssignment(Integer assignmentId);

	AssignmentEntity findAssignmentEntityById(Integer assignmentId);
}
