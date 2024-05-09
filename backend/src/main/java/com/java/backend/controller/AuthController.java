package com.java.backend.controller;

import com.java.backend.dto.UserDto;
import com.java.backend.request.ConfirmAccountRequest;
import com.java.backend.request.LoginRequest;
import com.java.backend.request.RegisterRequest;
import com.java.backend.service.UserService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return new ResponseEntity<>(Map.of("success", true, "message", "User created successfully!"),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        loginRequest = userService.validateLogin(loginRequest);
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final UserDto user = userService.findUserByEmail(loginRequest.getEmail());

        return new ResponseEntity<>(Map.of("success", true, "access_token", token, "user", user),
                HttpStatus.OK);
    }

    @PostMapping("/request-token")
    public ResponseEntity<Object> requestToken(@RequestParam String email) {
        userService.requestToken(email);
        return new ResponseEntity<>(Map.of("success", true, "message",
                "Confirmation token has been sent. Please check your email to receive it!"), HttpStatus.CREATED);
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<Object> confirmAccount(@Valid @RequestBody ConfirmAccountRequest confirmAccountRequest) {
        userService.confirmToken(confirmAccountRequest);
        return new ResponseEntity<>(Map.of("success", true, "message",
                "Your account is enable. Let's login!"), HttpStatus.ACCEPTED);
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
