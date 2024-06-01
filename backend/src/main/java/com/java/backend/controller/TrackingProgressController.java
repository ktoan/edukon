package com.java.backend.controller;

import com.java.backend.service.TrackingProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TrackingProgressController {
    private final TrackingProgressService trackingProgressService;

    @PostMapping("/create")
    public ResponseEntity<Object> findAllBlogs(@RequestParam Integer videoId) {
        return new ResponseEntity<>(Map.of("success", true, "is_watched", trackingProgressService.createTrackingProgress(videoId)), HttpStatus.OK);
    }
}
