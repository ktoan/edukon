package com.java.backend.controller;

import com.java.backend.dto.UserDto;
import com.java.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "User")
public class UserController {
	private final UserService userService;

	@PostMapping("/update")
	@Operation(summary = "Update user information")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userRequest) {
		UserDto updatedUser = userService.updateUser(userRequest);
		return new ResponseEntity<>(Map.of("success", true, "updated_user", updatedUser), HttpStatus.OK);
	}

	@PostMapping("/change-avatar")
	@Operation(summary = "Change user avatar")
	public ResponseEntity<?> changeAvatar(@RequestParam MultipartFile file) {
		String avatar = userService.uploadAvatar(file);
		return new ResponseEntity<>(Map.of("success", true, "new_avatar", avatar), HttpStatus.OK);
	}
}
