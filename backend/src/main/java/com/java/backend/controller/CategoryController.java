package com.java.backend.controller;

import com.java.backend.dto.CategoryDto;
import com.java.backend.request.CategoryRequest;
import com.java.backend.service.CategoryService;
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
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Object> findAllCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(Map.of("success", true, "categories", categories), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createCategory(@Valid @ModelAttribute CategoryRequest categoryRequest) {
        CategoryDto newCategory = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(Map.of("success", true, "newCategory", newCategory), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateCategory(@RequestParam Integer categoryId,
                                                 @ModelAttribute CategoryRequest categoryRequest) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(Map.of("success", true, "updatedCategory", updatedCategory), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCategory(@RequestParam Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(Map.of("success", true, "message", "Category deleted successfully!"),
                HttpStatus.OK);
    }
}
