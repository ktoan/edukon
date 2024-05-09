package com.java.backend.controller;

import com.java.backend.dto.VideoDto;
import com.java.backend.request.VideoRequest;
import com.java.backend.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class VideoController {
	private final VideoService videoService;

	@PostMapping("/create")
	public ResponseEntity<Object> createVideo(@Valid @ModelAttribute VideoRequest videoRequest) {
		VideoDto newVideo = videoService.createVideo(videoRequest);
		return new ResponseEntity<>(Map.of("success", true, "newVideo", newVideo), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateVideo(@RequestParam Integer videoId, @ModelAttribute VideoRequest videoRequest) {
		VideoDto updatedVideo = videoService.updateVideo(videoId, videoRequest);
		return new ResponseEntity<>(Map.of("success", true, "updatedVideo", updatedVideo), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteBlog(@RequestParam Integer videoId) {
		videoService.deleteVideo(videoId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Video deleted successfully!"), HttpStatus.OK);
	}
}
