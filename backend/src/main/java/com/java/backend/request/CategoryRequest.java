package com.java.backend.request;

import com.java.backend.constant.ExceptionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@Data
public class CategoryRequest {
    @NotNull(message = ExceptionMessage.REQUIRED_CATEGORY_NAME)
    @NotBlank(message = ExceptionMessage.REQUIRED_CATEGORY_NAME)
    private String name;
    @NotNull(message = ExceptionMessage.REQUIRED_THUMBNAIL)
    private MultipartFile thumbnail;
}
