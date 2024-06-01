package com.java.backend.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SubmissionRequest {
    private MultipartFile file;
    private Integer assignmentId;
}
