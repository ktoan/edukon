package com.java.backend.controller;

import com.java.backend.dto.CommentDto;
import com.java.backend.request.CommentRequest;
import com.java.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/create")
	public ResponseEntity<Object> createComment(@Valid @RequestBody CommentRequest commentRequest) {
		CommentDto newComment = commentService.createComment(commentRequest);
		return new ResponseEntity<>(Map.of("success", true, "newComment", newComment), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateComment(@RequestParam Integer commentId, @RequestParam String comment) {
		CommentDto updatedComment = commentService.updateComment(commentId, comment);
		return new ResponseEntity<>(Map.of("success", true, "updatedComment", updatedComment), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteComment(@RequestParam Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Comment deleted successfully!"), HttpStatus.OK);
	}
}
