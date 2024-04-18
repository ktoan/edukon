package com.java.backend.controller;

import com.java.backend.constant.CommonMessage;
import com.java.backend.dto.UserDto;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;
import com.java.backend.response.LoginResponse;
import com.java.backend.response.MessageResponse;
import com.java.backend.service.IUserService;
import com.java.backend.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);

        MessageResponse response = new MessageResponse(true, CommonMessage.CREATED_USER);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        loginRequest = userService.validateLogin(loginRequest);
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final UserDto user = userService.findUserByEmail(loginRequest.getEmail());

        LoginResponse response = new LoginResponse(true, token, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
