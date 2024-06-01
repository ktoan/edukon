package com.java.backend.service.impl;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.entity.SubmissionEntity;
import com.java.backend.repository.SubmissionRepository;
import com.java.backend.request.SubmissionRequest;
import com.java.backend.service.SubmissionService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final FileUtil fileUtil;
    private final ContextUtil contextUtil;
    @Override
    public SubmissionEntity saveSubmission(SubmissionEntity submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public SubmissionDto createSubmission(SubmissionRequest submissionRequest) {
        return null;
    }
}
