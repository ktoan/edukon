package com.java.backend.request;

import com.java.backend.annotation.Required;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @Required(fieldName = "Comment")
    private String comment;
    @Required(fieldName = "Blog")
    private Integer blogId;
    private Integer parentId;
}
