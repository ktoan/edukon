package com.java.backend.controller;

import com.java.backend.dto.CommentDto;
import com.java.backend.request.CommentRequest;
import com.java.backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comment")
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/create")
	@Operation(summary = "Create new comment")
	public ResponseEntity<Object> createComment(@Valid @RequestBody CommentRequest commentRequest) {
		CommentDto newComment = commentService.createComment(commentRequest);
		return new ResponseEntity<>(Map.of("success", true, "new_comment", newComment), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	@Operation(summary = "Update comment by id")
	public ResponseEntity<Object> updateComment(@RequestParam Integer commentId, @RequestParam String comment) {
		CommentDto updatedComment = commentService.updateComment(commentId, comment);
		return new ResponseEntity<>(Map.of("success", true, "updated_comment", updatedComment), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Delete comment by id")
	public ResponseEntity<Object> deleteComment(@RequestParam Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(Map.of("success", true, "message", "Comment deleted successfully!"), HttpStatus.OK);
	}
}
