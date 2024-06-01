package com.java.backend.service;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.entity.SubmissionEntity;
import com.java.backend.request.SubmissionRequest;

public interface SubmissionService {
    SubmissionEntity saveSubmission(SubmissionEntity submission);
    SubmissionDto createSubmission(SubmissionRequest submissionRequest);
}
