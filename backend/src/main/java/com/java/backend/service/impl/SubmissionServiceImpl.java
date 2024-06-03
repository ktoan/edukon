package com.java.backend.service.impl;

import com.java.backend.dto.SubmissionDto;
import com.java.backend.entity.AssignmentEntity;
import com.java.backend.entity.SubmissionEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.enums.Role;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.SubmissionMapper;
import com.java.backend.repository.SubmissionRepository;
import com.java.backend.request.SubmissionRequest;
import com.java.backend.service.AssignmentService;
import com.java.backend.service.SubmissionService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final SubmissionMapper submissionMapper;
    private final FileUtil fileUtil;
    private final ContextUtil contextUtil;
    private final AssignmentService assignmentService;
    private final MessageSource messageSource;

    @Override
    public SubmissionEntity saveSubmission(SubmissionEntity submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public SubmissionDto createSubmission(SubmissionRequest submissionRequest) {
        SubmissionEntity newSubmission = new SubmissionEntity();
        UserEntity user = contextUtil.loadUserFromContext();
        if (user.getAuthorities().stream()
                .noneMatch(authority -> Role.STUDENT.name().equals(authority.getAuthority()))) {
            throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
        }
        AssignmentEntity assignment = assignmentService.findAssignmentEntityById(submissionRequest.getAssignmentId());
        if (assignment.getSubmissions().stream().anyMatch(s -> Objects.equals(user.getId(), s.getStudent().getId()))) {
            throw new NotAccessException("User have submitted a submission!");
        }
        newSubmission.setSource(fileUtil.uploadFile(submissionRequest.getFile(), "submissions"));
        newSubmission.setStudent(user);
        newSubmission.setAssignment(assignment);
        newSubmission.setGrade(0.0d);
        return submissionMapper.toDto(saveSubmission(newSubmission));
    }

    @Override
    public SubmissionEntity findSubmissionEntityById(Integer submissionId) {
        Optional<SubmissionEntity> submission = submissionRepository.findById(submissionId);
        if (submission.isEmpty()) {
            throw new NotFoundException(
                    messageSource.getMessage("msg.not-found", new Object[]{"Submission id", submissionId},
                            Locale.ENGLISH));
        }
        return submission.get();
    }
}
