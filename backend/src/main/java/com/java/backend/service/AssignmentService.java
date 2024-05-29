package com.java.backend.service;

import com.java.backend.dto.AssignmentDto;
import com.java.backend.entity.AssignmentEntity;
import com.java.backend.request.AssignmentRequest;

public interface AssignmentService {
	AssignmentEntity saveAssignment(AssignmentEntity assignment);

	AssignmentDto createAssignment(AssignmentRequest assignmentRequest);

	AssignmentEntity findAssignmentEntityById(Integer assignmentId);
}
