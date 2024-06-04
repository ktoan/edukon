package com.java.backend.controller;

import com.java.backend.service.TrackingProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Tracking Progress")
public class TrackingProgressController {
    private final TrackingProgressService trackingProgressService;

    @PostMapping("/create")
    @Operation(summary = "Create new tracking progress")
    public ResponseEntity<Object> createTrackingProgress(@RequestParam Integer videoId) {
        return new ResponseEntity<>(
                Map.of("success", true, "is_watched", trackingProgressService.createTrackingProgress(videoId)),
                HttpStatus.OK);
    }
}
