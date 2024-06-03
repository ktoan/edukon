package com.java.backend.controller;

import com.java.backend.dto.CategoryDto;
import com.java.backend.request.CategoryRequest;
import com.java.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    @Operation(summary = "Get all categories")
    public ResponseEntity<?> findAllCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(Map.of("success", true, "categories", categories), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new category")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryRequest categoryRequest) {
        CategoryDto newCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(Map.of("success", true, "new_category", newCategory), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update category by id")
    public ResponseEntity<?> updateCategory(@RequestParam Integer categoryId,
                                                 @ModelAttribute CategoryRequest categoryRequest) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(Map.of("success", true, "updated_category", updatedCategory), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<?> deleteCategory(@RequestParam Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(Map.of("success", true, "message", "Category deleted successfully!"),
                HttpStatus.OK);
    }
}
