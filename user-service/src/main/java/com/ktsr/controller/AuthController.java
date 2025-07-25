package com.ktsr.controller;

import com.ktsr.dto.JwtResponse;
import com.ktsr.dto.SignInRequest;
import com.ktsr.dto.SignUpRequest;
import com.ktsr.entity.User;
import com.ktsr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody SignUpRequest signUpRequest) {
        return new ResponseEntity<>(authService.registerUser(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(authService.longinUser(signInRequest), HttpStatus.OK);
    }


}
