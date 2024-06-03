package com.java.backend.request;

import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SubmissionRequest {
    @Required(fieldName = "File")
    private MultipartFile file;
    @Required(fieldName = "Assignment")
    private Integer assignmentId;
}
